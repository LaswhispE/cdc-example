package source;

import Mysql.table.StartupOptions;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import Mysql.source.MySqlSource;
import org.apache.flink.cdc.debezium.JsonDebeziumDeserializationSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStream;

import org.apache.flink.connector.pulsar.sink.PulsarSink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.flink.streaming.api.environment.ExecutionCheckpointingOptions.ENABLE_CHECKPOINTS_AFTER_TASKS_FINISH;

public class MySqlConnector {

    public static void main(String[] args) throws Exception {
        // 创建 MySQL 源
        MySqlSource<String> mySqlSource = MySqlSource.<String>builder()
                .hostname("10.49.2.7")
                .port(3306)
                .databaseList("test")
                .tableList("test.test")
                .username("bigdata_user")
                .password("4Lme3Bn0wdkRY@5qM3a2j0ISE")
                .deserializer(new JsonDebeziumDeserializationSchema())
//                .startupOptions(StartupOptions.latest())
                .build();

        /*// 测试
        MySqlSource<String> mySqlSource = MySqlSource.<String>builder()
                .hostname("10.49.26.33")
                .port(9030)
                .databaseList("test")
                .tableList("test.product")
                .username("bigdata_user")
                .password("stars@rNi0oG8xHe6yeeOIplm")
                .deserializer(new JsonDebeziumDeserializationSchema())
//                .startupOptions(StartupOptions.latest())
                .build();*/

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