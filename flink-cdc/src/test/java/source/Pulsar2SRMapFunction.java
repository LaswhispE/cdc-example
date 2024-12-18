package source;

import com.alibaba.fastjson.JSONObject;
import source.ParaManager;
import com.starrocks.connector.flink.table.data.DefaultStarRocksRowData;
import org.apache.flink.api.common.functions.RichMapFunction;

public class Pulsar2SRMapFunction  extends RichMapFunction<String, DefaultStarRocksRowData> {

//    private ParaManager paraManager;

    public Pulsar2SRMapFunction() {}

//    public Pulsar2SRMapFunction(ParaManager paraManager) {
//        this.paraManager= paraManager;
//    }

    @Override
    public DefaultStarRocksRowData map(String value) throws Exception {
        DefaultStarRocksRowData rowDataWithMeta = new DefaultStarRocksRowData();

        System.out.println("value: " + value);

        JSONObject rowData = JSONObject.parseObject(value);

        System.out.println("rowData: " + rowData);
        JSONObject record = rowData.getJSONObject("record");

        System.out.println("record: " + record);

        String tableName = "";

        if (rowData == null) {
            throw new IllegalArgumentException("rowData cannot be null");
        }

        tableName = rowData.getString("table");
        String databaseName = rowData.getString("db");

        System.out.println("tableName: " + tableName);
        System.out.println("databaseName: " + databaseName);

        if (tableName == null || databaseName == null) {
            throw new IllegalArgumentException("table_name or schema cannot be null");
        }

        rowDataWithMeta.setDatabase(databaseName);
        rowDataWithMeta.setTable(tableName);
        rowDataWithMeta.setUniqueKey(databaseName + tableName);

//        if (record == null) {
//            throw new IllegalArgumentException("record cannot be null");
//        }

        rowDataWithMeta.setRow(record.toJSONString());

        return rowDataWithMeta;
    }

}