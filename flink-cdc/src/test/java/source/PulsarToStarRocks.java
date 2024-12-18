package source;

import com.starrocks.connector.flink.table.data.DefaultStarRocksRowData;
import com.starrocks.connector.flink.table.sink.StarRocksSinkOptions;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.connector.pulsar.source.PulsarSource;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class PulsarToStarRocks {
    public static void main(String[] args) throws Exception {

        Configuration configuration = new Configuration();
        configuration.setString("pipeline.name", "fixedPipelineName");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment(configuration);

        env.enableCheckpointing(300000);
        env.setParallelism(1);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.AT_LEAST_ONCE);
        env.getCheckpointConfig().setCheckpointTimeout(60 * 60 * 1000L);
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        env.getCheckpointConfig().setTolerableCheckpointFailureNumber(100);
        // 创建 Pulsar 数据源
        PulsarSource<String> pulsarSource = PulsarSource.<String>builder()
                .setServiceUrl("pulsar://localhost:6650")
                .setTopics("persistent://public/default/my-topic")
                .setDeserializationSchema(new SimpleStringSchema())
                .setSubscriptionName("fixed_subscription_name")
                .build();

        // 从 Pulsar 读取数据
        DataStream<String> pulsarDS = env.fromSource(pulsarSource, WatermarkStrategy.noWatermarks(), "PulsarSource");

        System.out.println("Pulsar Source: " + pulsarDS);

//        // 初始化 ParaManager
//        ParameterTool params = ParameterTool.fromArgs(args);
//        ParaManager paraManager = new ParaManager(params);
//        paraManager.setSrSinkTableSuffix(""); // 确保 srSinkTableSuffix 为空字符串
//
//        // 使用初始化好的 ParaManager 创建 Pulsar2SRMapFunction 实例
//        Pulsar2SRMapFunction pulsar2SRMapFunction = new Pulsar2SRMapFunction(paraManager);


        //  StarRocks sink
        env.fromSource(pulsarSource, WatermarkStrategy.noWatermarks(), "Pulsar Source")
                .uid("uid_pulsar_source")
                .name("name_pulsar_source")
                .map(new Pulsar2SRMapFunction() {
                    @Override
                    public DefaultStarRocksRowData map(String value) throws Exception {
                        DefaultStarRocksRowData result = super.map(value);
                        // 打印写入 StarRocks 的信息
                        System.out.println("StarRocks Row Data: " + result);
                        return result;
                    }
                })
                .uid("uid_map")
                .name("name_map")
//                .keyBy((KeySelector<DefaultStarRocksRowData, String>) value ->
//                        String.format("%s.%s", value.getDatabase(), value.getTable()))
                .addSink(
                        StarRocksSink.sinkWithMeta(
                                StarRocksSinkOptions.builder()
                                        .withProperty("jdbc-url", "jdbc:mysql://localhost:9030")
                                        .withProperty("load-url", "localhost:8030")
                                        .withProperty("database-name", "test")
                                        .withProperty("table-name", "test")
                                        .withProperty("username", "root")
                                        .withProperty("password", "")
                                        .withProperty("sink.max-retries", "3")
                                        .withProperty("sink.buffer-flush.max-bytes", "67108864")
                                        .withProperty("sink.buffer-flush.interval-ms", "5000")
                                        .withProperty("sink.buffer-flush.max-rows", "5000000")
                                        .withProperty("sink.properties.format", "json")
                                        .withProperty("sink.properties.strip_outer_array", "true")
                                        .withProperty("sink.connect.timeout-ms", "600000")
                                        .withProperty("sink.properties.ignore_json_size", "true")
                                        .withProperty("sink.version", "V1")
                                        .withProperty("sink.properties.max_filter_ratio", "0.05")
                                        .build()))
                .uid("uid_sink_starrocks")
                .name("sink_starrocks");

        env.execute("Pulsar To StarRocks");
    }
}