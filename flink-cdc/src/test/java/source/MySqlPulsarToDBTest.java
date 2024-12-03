package source;

import com.ibm.icu.text.SimpleDateFormat;
import com.starrocks.connector.flink.StarRocksSink;
import com.starrocks.connector.flink.row.sink.StarRocksSinkOP;
import com.starrocks.connector.flink.row.sink.StarRocksSinkRowBuilder;
import com.starrocks.connector.flink.table.sink.StarRocksSinkOptions;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.MultipleParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.connector.pulsar.source.enumerator.cursor.StartCursor;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import org.apache.flink.connector.pulsar.source.PulsarSource;

import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.TableSchema;

import java.math.BigInteger;
import java.util.Date;

import static org.apache.flink.streaming.api.environment.ExecutionCheckpointingOptions.ENABLE_CHECKPOINTS_AFTER_TASKS_FINISH;

public class MySqlPulsarToDBTest {
    public static void main(String[] args) throws Exception {
        MultipleParameterTool params = MultipleParameterTool.fromArgs(args);
        String serviceUrl = params.get("serviceUrl", "pulsar://localhost:6650");
        String topic = params.get("topic", "persistent://public/default/mysql-topic");

        // MySQL 连接参数
        String jdbcUrl = params.get("jdbcUrl", "jdbc:mysql://10.49.26.33:9030");
        String loadUrl = params.get("loadUrl", "10.49.26.33:8030");
        String jdbcUser = params.get("jdbcUser", "bigdata_user");
        String jdbcPassword = params.get("jdbcPassword", "stars@rNi0oG8xHe6yeeOIplm");

        // 创建 Pulsar Source
        PulsarSource<String> source = PulsarSource.<String>builder()
                .setServiceUrl(serviceUrl)
                .setStartCursor(StartCursor.latest())
                .setTopics(topic)
                .setDeserializationSchema(new SimpleStringSchema())
                .setSubscriptionName("mysql-subscription")
                .build();

        // 获取 Flink 执行环境
        Configuration config = new Configuration();

        config.set(ENABLE_CHECKPOINTS_AFTER_TASKS_FINISH, true);
        config.setInteger(RestOptions.PORT, 8087);

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment(config);
        // enable checkpoint
        env.enableCheckpointing(3000);

        StarRocksSinkOptions options = StarRocksSinkOptions.builder()
                .withProperty("jdbc-url", jdbcUrl)
                .withProperty("load-url", loadUrl) // 需要替换为实际的load URL
                .withProperty("database-name", "test_sr_sink")
                .withProperty("table-name", "product_mws")
                .withProperty("username", jdbcUser)
                .withProperty("password", jdbcPassword)
                .withProperty("sink.properties.format", "json")
                .withProperty("sink.properties.strip_outer_array", "true")
                .build();

        TableSchema schema = TableSchema.builder()
                .field("pdr_id", DataTypes.INT().notNull()) // Assuming id is a unique identifier
                .field("zid", DataTypes.INT().notNull())
                .field("product_id", DataTypes.INT())
                .field("developer_uid", DataTypes.INT())
                .field("developer_name", DataTypes.STRING())
                .field("status", DataTypes.TINYINT())
                .field("operator", DataTypes.INT())
                .field("operated_at", DataTypes.DATE())
                .field("company_id", DataTypes.BIGINT())
                .field("v_uuid", DataTypes.STRING())

                .primaryKey("id") // Assuming 'id' is the primary key
                .build();

        StarRocksSinkRowBuilder<RowData> RowDataBuilder = new StarRocksSinkRowBuilder<RowData>() {
            @Override
            public void accept(Object[] internalRow, RowData rowData) {
                internalRow[0] = rowData.pdr_id;
                internalRow[1] = rowData.zid;
                internalRow[2] = rowData.product_id;
                internalRow[3] = rowData.developer_uid;
                internalRow[4] = rowData.developer_name;
                internalRow[5] = rowData.status;
                internalRow[6] = rowData.operator;
                internalRow[7] = rowData.operated_at;
                internalRow[8] = rowData.company_id;
                internalRow[9] = rowData.v_uuid;

                internalRow[internalRow.length - 1] = StarRocksSinkOP.UPSERT.ordinal();
            }

        };

        // Create a stream from the source
        DataStream<String> pulsarStream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Pulsar Source");
        pulsarStream.map(value -> {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(value);
            JsonNode afterNode = rootNode.path("after");

            // 解析fullDocument
            JsonNode after = objectMapper.readTree(afterNode.asText());
            int pdr_id = after.path("pdr_id").asInt();
            int zid = after.path("zid").asInt();
            int product_id = after.path("product_id").asInt();
            int product_uid = after.path("product_uid").asInt();
            String developer_name = after.path("developer_name").asText();
            int status = after.path("local_sku").asInt();
            int operator = after.path("operator").asInt();
            String operated_at_raw = after.path("operated_at").asText();
            SimpleDateFormat operated_at = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String company_id = after.path("company_id").asText();
            String v_uuid = after.path("v_uuid").asText();

            return new RowData(pdr_id,zid,product_id,product_uid,developer_name,status,operator, operated_at.get2DigitYearStart(),company_id,v_uuid);
        }).addSink(StarRocksSink.sink(schema, options, RowDataBuilder));
    }
    public static class RowData {
        public int pdr_id;
        public int zid;
        public int product_id;
        public int developer_uid;
        public String developer_name;
        public int status;
        public int operator;
        public Date operated_at;
        public String company_id;
        public String v_uuid;

        public RowData() {
        }

        public RowData(int pdr_id, int zid, int product_id, int developer_uid, String developer_name, int status, int operator, Date operated_at, String company_id, String v_uuid) {
            this.pdr_id = pdr_id;
            this.zid = zid;
            this.product_id = product_id;
            this.developer_uid = developer_uid;
            this.developer_name = developer_name;
            this.status = status;
            this.operator = operator;
            this.operated_at = operated_at;
            this.company_id = company_id;
            this.v_uuid = v_uuid;
        }
    }
}
