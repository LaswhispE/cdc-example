package core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starrocks.connector.flink.StarRocksSink;
import com.starrocks.connector.flink.table.sink.StarRocksSinkOptions;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.utils.MultipleParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

public class LoadJsonRecords {

    public static void main(String[] args) throws Exception {

        MultipleParameterTool params = MultipleParameterTool.fromArgs(args);
        String jdbcUrl = params.get("jdbcUrl", "jdbc:mysql://localhost:9030");
        String loadUrl = params.get("loadUrl", "localhost:8030");

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Example messages
        String[] records = new String[]{
                "{\"before\":null,\"after\":{\"id\":1223,\"name\":\"love\"},\"source\":{\"version\":\"1.6.4.Final\",\"connector\":\"mysql\",\"name\":\"mysql_binlog_source\",\"ts_ms\":1734687336000,\"snapshot\":\"false\",\"db\":\"test\",\"sequence\":null,\"table\":\"test\",\"server_id\":243817,\"gtid\":\"a0c05ccd-0a59-11ec-a8b7-b02628432340:6357366190\",\"file\":\"mysql-bin.047385\",\"pos\":34983661,\"row\":0,\"thread\":null,\"query\":null},\"op\":\"c\",\"ts_ms\":1734687336557,\"transaction\":null}",
                "{\"before\":{\"id\":100,\"name\":\"nb\"},\"after\":null,\"source\":{\"version\":\"1.6.4.Final\",\"connector\":\"mysql\",\"name\":\"mysql_binlog_source\",\"ts_ms\":1734695927000,\"snapshot\":\"false\",\"db\":\"test\",\"sequence\":null,\"table\":\"test\",\"server_id\":243817,\"gtid\":\"a0c05ccd-0a59-11ec-a8b7-b02628432340:6357442247\",\"file\":\"mysql-bin.047385\",\"pos\":167575762,\"row\":0,\"thread\":null,\"query\":null},\"op\":\"d\",\"ts_ms\":1734695927333,\"transaction\":null}"
        };

        DataStream<String> source = env.fromElements(records);

        // Use ObjectMapper to parse JSON and extract 'before' and 'after' fields
        DataStream<String> parsedSource = source.map(new MapFunction<String, String>() {
            private final ObjectMapper objectMapper = new ObjectMapper();

            @Override
            public String map(String value) throws Exception {
                JsonNode rootNode = objectMapper.readTree(value);
                JsonNode afterNode = rootNode.get("after");
                JsonNode beforeNode = rootNode.get("before");

                if (afterNode != null && !afterNode.isNull()) {
                    return afterNode.toString();
                } else if (beforeNode != null && !beforeNode.isNull()) {
                    return beforeNode.toString();
                } else {
                    return null; // or handle the case where both are null
                }
            }
        }).filter(record -> record != null); // Filter out null records

        StarRocksSinkOptions options = StarRocksSinkOptions.builder()
                .withProperty("jdbc-url", jdbcUrl)
                .withProperty("load-url", loadUrl)
                .withProperty("database-name", "test")
                .withProperty("table-name", "test")
                .withProperty("username", "root")
                .withProperty("password", "")
                .withProperty("sink.properties.format", "json")
                .withProperty("sink.properties.strip_outer_array", "true")
                .build();

        // Create the sink with the options
        SinkFunction<String> starRockSink = StarRocksSink.sink(options);
        parsedSource.addSink(starRockSink);

        env.execute("LoadJsonRecords");
    }
}
