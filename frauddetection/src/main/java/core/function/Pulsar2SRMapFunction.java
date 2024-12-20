package core.function;

import com.alibaba.fastjson.JSONObject;
import com.starrocks.connector.flink.table.data.DefaultStarRocksRowData;
import org.apache.flink.api.common.functions.RichMapFunction;

public class Pulsar2SRMapFunction extends RichMapFunction<String, DefaultStarRocksRowData> {

    public Pulsar2SRMapFunction() {}

    @Override
    public DefaultStarRocksRowData map(String value) throws Exception {
        DefaultStarRocksRowData rowDataWithMeta = new DefaultStarRocksRowData();

        System.out.println("value: " + value);

        JSONObject rowData = JSONObject.parseObject(value);

        System.out.println("rowData: " + rowData);

        if (rowData == null) {
            throw new IllegalArgumentException("rowData cannot be null");
        }

        JSONObject source = rowData.getJSONObject("source");
        if (source == null) {
            throw new IllegalArgumentException("source cannot be null");
        }

        String tableName = source.getString("table");
        String databaseName = source.getString("db");

        JSONObject before = rowData.getJSONObject("before");
        JSONObject after = rowData.getJSONObject("after");


        System.out.println("tableName: " + tableName);
        System.out.println("databaseName: " + databaseName);

        if (tableName == null || databaseName == null) {
            throw new IllegalArgumentException("table_name or db cannot be null");
        }

        String op = rowData.getString("op");
        System.out.println("op: " + op);

        rowDataWithMeta.setDatabase(databaseName);
        rowDataWithMeta.setTable(tableName);
        rowDataWithMeta.setUniqueKey(databaseName + tableName);

        if ("c".equals(op) || "u".equals(op) || "r".equals(op)) { // 假设更新操作用 "u" 表示
            rowDataWithMeta.setRow(after.toJSONString());
        } else if ("d".equals(op)) {
            // 设置一个标志位或其他方式来表示这是删除操作
            rowDataWithMeta.setRow(null); // 或者你可以设置一个特殊的字符串来表示删除
        } else {
            throw new IllegalArgumentException("Unsupported operation type: " + op);
        }

        return rowDataWithMeta;
    }
}
