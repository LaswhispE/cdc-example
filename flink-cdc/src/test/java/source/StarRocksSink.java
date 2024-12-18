package source;

import com.starrocks.connector.flink.row.sink.StarRocksGenericRowTransformer;
import com.starrocks.connector.flink.row.sink.StarRocksIRowTransformer;
import com.starrocks.connector.flink.row.sink.StarRocksSinkRowBuilder;
import com.starrocks.connector.flink.table.data.DefaultStarRocksRowData;
import com.starrocks.connector.flink.table.sink.SinkFunctionFactory;
import com.starrocks.connector.flink.table.sink.StarRocksSinkOptions;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.table.api.TableSchema;

public class StarRocksSink {

    /**
     * Create a StarRocks DataStream sink.
     * <p>
     * Note: the objects passed to the return sink can be processed in batch and retried.
     * Therefore, objects can not be {@link org.apache.flink.api.common.ExecutionConfig#enableObjectReuse() reused}.
     * </p>
     *
     * @param flinkTableSchema     TableSchema of the all columns with DataType
     * @param sinkOptions          StarRocksSinkOptions as the document listed, such as jdbc-url, load-url, batch size and maximum retries
     * @param rowDataTransformer   StarRocksSinkRowBuilder which would be used to transform the upstream record.
     * @param <T>                  type of data in {@link org.apache.flink.streaming.runtime.streamrecord.StreamRecord StreamRecord}.
     * @return SinkFunction        SinkFunction that could be add to a stream.
     */
    public static <T> SinkFunction<T> sink(
            TableSchema flinkTableSchema,
            StarRocksSinkOptions sinkOptions,
            StarRocksSinkRowBuilder<T> rowDataTransformer) {
        StarRocksIRowTransformer<T> rowTransformer =
                new StarRocksGenericRowTransformer<>(rowDataTransformer);
        return SinkFunctionFactory.createSinkFunction(sinkOptions, flinkTableSchema, rowTransformer);
    }

    /**
     * Create a StarRocks DataStream sink, stream elements could only be String.
     * <p>
     * Note: the objects passed to the return sink can be processed in batch and retried.
     * Therefore, objects can not be {@link org.apache.flink.api.common.ExecutionConfig#enableObjectReuse() reused}.
     * </p>
     *
     * @param sinkOptions            StarRocksSinkOptions as the document listed, such as jdbc-url, load-url, batch size and maximum retries
     * @return SinkFunction          SinkFunction that could be add to a stream.
     */
    public static SinkFunction<String> sink(StarRocksSinkOptions sinkOptions) {
        return SinkFunctionFactory.createSinkFunction(sinkOptions);
    }

    public static SinkFunction<DefaultStarRocksRowData> sinkWithMeta(StarRocksSinkOptions sinkOptions) {
        return SinkFunctionFactory.createSinkFunction(sinkOptions);
    }

    private StarRocksSink() {}
}
