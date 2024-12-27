package core;


import com.ververica.cdc.connectors.mysql.source.MySqlSource;
import com.ververica.cdc.connectors.mysql.table.StartupOptions;
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStream;

import org.apache.flink.connector.pulsar.sink.PulsarSink;

import static org.apache.flink.streaming.api.environment.ExecutionCheckpointingOptions.ENABLE_CHECKPOINTS_AFTER_TASKS_FINISH;

public class MySqlToPulsarCDC {

    public static void main(String[] args) throws Exception {
        // 创建 MySQL 源
        MySqlSource<String> mySqlSource = MySqlSource.<String>builder()
                .hostname("localhost")
                .port(3306)
                .databaseList("test")
                .tableList("test.user")
                .username("root")
                .password("root")
                .deserializer(new JsonDebeziumDeserializationSchema())
                .startupOptions(StartupOptions.latest())
                .serverTimeZone("UTC")  // 设置为与 MySQL 服务器一致的时区
                .build();


        // 配置
        Configuration config = new Configuration();
        config.set(ENABLE_CHECKPOINTS_AFTER_TASKS_FINISH, true);
        config.setInteger(RestOptions.PORT, 8086);

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment(config);
        env.setParallelism(2);
        env.enableCheckpointing(300000);

        // 从 MySQL 源读取数据，并设置并行度
        DataStream<String> mySqlDS = env.fromSource(mySqlSource, WatermarkStrategy.noWatermarks(), "MySQLParallelSource")
                .setParallelism(1);


        // 配置 Pulsar 接收端
        // 配置Pulsar
        PulsarSink<String> sink = PulsarSink.builder()
                .setServiceUrl("pulsar://localhost:6650")
                .setTopics("persistent://public/default/my-topic")
                .setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
                .setSerializationSchema(new SimpleStringSchema())
                .build();

        mySqlDS.print();

        // 将数据发送到 Pulsar
        mySqlDS.sinkTo(sink);

        // 执行 Flink 作业
        env.execute("MySQL CDC to Pulsar");
    }
}