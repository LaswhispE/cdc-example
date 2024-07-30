package source;

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
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import org.apache.flink.connector.pulsar.source.PulsarSource;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.TableSchema;
import org.apache.pulsar.client.api.SubscriptionType;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

public class PulsarToDB {

    public static void main(String[] args) throws Exception {

        MultipleParameterTool params = MultipleParameterTool.fromArgs(args);
        String jdbcUrl = params.get("jdbcUrl", "jdbc:mysql://10.49.26.33:9030");
        String loadUrl = params.get("loadUrl", "10.49.26.33:8030");

        //定义pulsar相关参数
        String serviceUrl = "pulsar://localhost:6650";
        String adminUrl = "http://localhost:8080";
        String topic = "persistent://public/default/my-topic";

        PulsarSource<String> source = PulsarSource.<String>builder()
                .setServiceUrl(serviceUrl)
                .setStartCursor(StartCursor.earliest())
                .setTopics(topic)
                .setDeserializationSchema(new SimpleStringSchema())
                .setSubscriptionName("my-subscription")
                .build();

        // 获取 Flink 执行环境
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 定义StarRocks的连接配置
        StarRocksSinkOptions options = StarRocksSinkOptions.builder()
                .withProperty("jdbc-url", jdbcUrl)
                .withProperty("load-url", loadUrl)
                .withProperty("database-name", "test")
                .withProperty("table-name", "product")
                .withProperty("username", "bigdata_user")
                .withProperty("password", "stars@rNi0oG8xHe6yeeOIplm")
                .build();

        // 定义与StarRocks表结构匹配的schema
        TableSchema schema = TableSchema.builder()
                .field("_id", DataTypes.STRING().notNull())
                .field("name", DataTypes.STRING())
                .field("description", DataTypes.STRING())
                .field("weight", DataTypes.DOUBLE())
                .primaryKey("_id")
                .build();

        // 实现StarRocksSinkRowBuilder以将Product对象转换为StarRocks行
        StarRocksSinkRowBuilder<Product> productRowBuilder = new StarRocksSinkRowBuilder<Product>() {
            @Override
            public void accept(Object[] internalRow, Product product) {
                internalRow[0] = product.getId();
                internalRow[1] = product.getName();
                internalRow[2] = product.getDescription();
                internalRow[3] = product.getWeight();
                internalRow[internalRow.length - 1] = StarRocksSinkOP.UPSERT.ordinal();
            }
        };

        DataStream<String> pulsarStream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Pulsar Source");
        pulsarStream.map(value -> {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(value);

            String operationType = rootNode.path("operationType").asText();
            JsonNode fullDocumentNode = rootNode.path("fullDocument");


            // 解析fullDocument
            JsonNode fullDocument = objectMapper.readTree(fullDocumentNode.asText());
            String id = fullDocument.path("_id").path("$oid").asText();
            String name = fullDocument.path("name").asText();
            String description = fullDocument.path("description").asText();
            double weight = fullDocument.path("weight").asDouble();

            return new Product(id, name, description, weight);
        }).addSink(StarRocksSink.sink(schema, options, productRowBuilder));



        // 将 Pulsar Source 添加到 Flink 流中，并进行转换、打印和存储
        /*env.fromSource(source, WatermarkStrategy.noWatermarks(), "Pulsar Source")
                .map(new MapFunction<String, Product>() {
                    @Override
                    public Product map(String value) throws Exception {
                        // 解析 JSON 字符串中的 fullDocument 部分
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode jsonNode = objectMapper.readTree(value);
                        JsonNode documentNode = jsonNode.get("fullDocument");

                        // 创建 Product 对象
                        Product product = new Product();

                        JsonNode idNode = documentNode.get("_id");
                        product.setId(idNode.get("$oid").asText());
                        product.setName(documentNode.get("name").asText());
                        product.setDescription(documentNode.get("description").asText());
                        product.setWeight(documentNode.get("weight").asDouble());

                        // 打印 Product 对象
                        System.out.println(product);

                        return product;
                    }
                })
                // 添加 StarRocks Sink 将 Product 对象存储到数据库
                .addSink(StarRocksSink.sink(schema, options, productRowBuilder));*/

        // 执行 Flink 作业
        env.execute("PulsarToDBJob");
    }
}
