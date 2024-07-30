package source;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.DeleteManyModel;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class DataDelete {
    private static final Logger LOG = LoggerFactory.getLogger(DataDelete.class);
    private static MongoClient mongodbClient;
    public static void main(String[] args) throws Exception {
        connectToMongoDB();
        startScheduledInsertion();

    }
    public static void connectToMongoDB() {
        LOG.info("Connecting to MongoDB...");

        // 这里替换为您的MongoDB连接字符串
        String connectionString = "mongodb://localhost:27017";
//        String connectionString = "mongodb://localhost:27017/?replicaSet=rs0";

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .build();
        mongodbClient = MongoClients.create(settings);

        LOG.info("Connected to MongoDB.");
    }

    public static void startScheduledInsertion() {
        // 获取数据库引用，例如使用inventory数据库和products集合
        MongoDatabase database = mongodbClient.getDatabase("inventory");
        String collectionName = "products";

        // 创建ScheduledExecutorService实例
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        // 定义一个任务，每隔2秒执行一次insertData方法
        Runnable insertTask = () -> deleteData(database, collectionName);
        executorService.scheduleAtFixedRate(insertTask, 0, 1, TimeUnit.SECONDS);
    }

    protected static void deleteData(MongoDatabase database, String collectionName) {
        // 创建删除操作的列表，用于存储要执行的多个删除操作
        List<DeleteManyModel<Document>> deletes = new ArrayList<>();

        // 定义删除条件，例如：删除特定条件的文档
        // 这里可以根据实际情况添加特定的删除条件
        Bson deleteQuery = Filters.eq("weight", "2"); // 示例条件

        // 将删除操作添加到列表中，使用DeleteManyModel来删除多条数据
        deletes.add(new DeleteManyModel<>(deleteQuery));

        // 获取MongoCollection并执行批量删除
        MongoCollection<Document> collection = database.getCollection(collectionName);
        BulkWriteResult result = collection.bulkWrite(deletes, new BulkWriteOptions().ordered(false));

        // 输出日志，确认删除
        LOG.info("Deleted {} documents.", result.getDeletedCount());
    }

}
