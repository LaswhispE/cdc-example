package source;

import Mongodb.source.MongoDBSource;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.cdc.connectors.base.options.StartupOptions;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.pulsar.sink.PulsarSink;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.configuration.RestOptions;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.cdc.debezium.JsonDebeziumDeserializationSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.flink.streaming.api.environment.ExecutionCheckpointingOptions.ENABLE_CHECKPOINTS_AFTER_TASKS_FINISH;

public class MongoConnector {

    private static final Logger LOG = LoggerFactory.getLogger(MongoConnector.class);

    public static void main(String[] args) throws Exception {

//        String database = CONTAINER.executeCommandFileInSeparateDatabase("inventory");
        MongoDBSource<String> mongoSource =
                MongoDBSource.<String>builder()
                        .hosts("localhost:27017")
                        .databaseList("inventory")
                        .collectionList("inventory.products")
                        .deserializer(new JsonDebeziumDeserializationSchema())
//                        .startupOptions(StartupOptions.latest())
                        .closeIdleReaders(true)
                        .build();

        Configuration config = new Configuration();

        config.set(ENABLE_CHECKPOINTS_AFTER_TASKS_FINISH, true);
        config.setInteger(RestOptions.PORT, 8888);

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment(config);

        // enable checkpoint
        env.enableCheckpointing(3000);
        // set the source parallelism to 2
        DataStream<String> mongoDS  = env.fromSource(mongoSource, WatermarkStrategy.noWatermarks(), "MongoDBParallelSource")
                    .setParallelism(2);

        // 配置Pulsar
        PulsarSink<String> sink = PulsarSink.builder()
                .setServiceUrl("pulsar://localhost:6650")
                .setTopics("persistent://public/default/my-topic")
                .setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
                .setSerializationSchema(new SimpleStringSchema())
                .build();

        mongoDS.sinkTo(sink);

        env.execute("Print MongoDB Snapshot + Change Stream");
    }
}
