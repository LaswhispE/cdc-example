package core.function;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.starrocks.connector.flink.table.data.DefaultStarRocksRowData;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;

/**
 * @Author ZhuHaiBo
 * @Create 2022/1/11 18:35
 */
public class MongoDBMapFunction extends RichMapFunction<String, DefaultStarRocksRowData> {

    private Integer taskNum;

    @Override
    public void open(Configuration parameters) throws Exception {
        taskNum = getRuntimeContext().getMaxNumberOfParallelSubtasks();
    }

    @Override
    public DefaultStarRocksRowData map(String value) throws Exception {

//        System.out.println("value: " + value);
        JSONObject data = JSON.parseObject(value);
        JSONObject source = data.getJSONObject("ns");
        String op = data.getString("operationType");
        JSONObject record = null;

        String tableName = source.getString("coll");
        String database = source.getString("db");

        if ("insert".equals(op) || "update".equals(op)) {
            record = data.getJSONObject("fullDocument");

            // 提取 _id 中的 $oid 值
            JSONObject idObject = record.getJSONObject("_id");
            if (idObject != null) {
                String oid = idObject.getString("$oid");
                record.put("_id", oid);
            }
            record.put ("__op", 0);

        } else if ("delete".equals(op)) {
            record = data.getJSONObject("fullDocument");
            record.put ("__op", 1);
        }
        if (record == null) {
            throw new RuntimeException("Invalid operation type: " + op);
        }
//        record.put("__op", Envelope.Operation.DELETE.code().equals(op) ? 1 : 0);

//        System.out.println("taskNum: " + record.toJSONString());

        DefaultStarRocksRowData defaultStarRocksRowData = new DefaultStarRocksRowData();
        defaultStarRocksRowData.setUniqueKey(database + "_" + tableName);
        defaultStarRocksRowData.setDatabase(database);
        defaultStarRocksRowData.setTable(tableName);
        defaultStarRocksRowData.setRow(record.toJSONString());
        return defaultStarRocksRowData;
    }
}