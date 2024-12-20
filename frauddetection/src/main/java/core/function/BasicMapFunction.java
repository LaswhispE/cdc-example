package core.function;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.starrocks.connector.flink.table.data.DefaultStarRocksRowData;
import io.debezium.data.Envelope;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;

/**
 * @Author ZhuHaiBo
 * @Create 2022/1/11 18:35
 */
public class BasicMapFunction extends RichMapFunction<String, DefaultStarRocksRowData> {

    private Integer taskNum;

    @Override
    public void open(Configuration parameters) throws Exception {
        taskNum = getRuntimeContext().getMaxNumberOfParallelSubtasks();
    }

    @Override
    public DefaultStarRocksRowData map(String value) throws Exception {
        JSONObject data = JSON.parseObject(value);
        JSONObject source = data.getJSONObject("source");
        String op = data.getString("op");
        JSONObject record = null;

        String tableName = source.getString("table");
        String database = source.getString("db");

        if ("c".equals(op) || "u".equals(op) || "r".equals(op)) {
            record = data.getJSONObject("after");

        } else if ("d".equals(op)) {
            record = data.getJSONObject("before");
        }
        if (record == null) {
            throw new RuntimeException("Invalid operation type: " + op);
        }
        record.put("__op", Envelope.Operation.DELETE.code().equals(op) ? 1 : 0);

        System.out.println("taskNum: " + record.toJSONString());

        DefaultStarRocksRowData defaultStarRocksRowData = new DefaultStarRocksRowData();
        defaultStarRocksRowData.setUniqueKey(database + "_" + tableName);
        defaultStarRocksRowData.setDatabase(database);
        defaultStarRocksRowData.setTable(tableName);
        defaultStarRocksRowData.setRow(record.toJSONString());
        return defaultStarRocksRowData;
    }
}