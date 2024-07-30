package source;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class DataUpdate {
    private static final Logger LOG = LoggerFactory.getLogger(DataUpdate.class);
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
        Runnable insertTask = () -> updateData(database, collectionName);
        executorService.scheduleAtFixedRate(insertTask, 0, 1, TimeUnit.SECONDS);
    }

    protected static void updateData(MongoDatabase database, String collectionName) {
        // 创建更新操作的列表，用于存储要执行的多个更新操作
        List<UpdateManyModel<Document>> updates = new ArrayList<>();

        // 定义更新操作，这里使用$set操作符来增加或更新字段
        Bson update = Updates.set("weight", 2);

        // 可以为每个更新定义不同的查询条件
        for (int i = 0; i < 10; i++) {
            // 创建查询条件，例如：更新特定条件的文档
            // 这里使用空查询代表选择所有文档，您可以根据实际情况修改查询条件
            Bson query = Filters.eq("weight",""); // 示例条件

            // 将更新操作添加到列表中，使用UpdateManyModel来更新多条数据
            updates.add(new UpdateManyModel<>(query, update));
        }

        // 获取MongoCollection并执行批量更新
        MongoCollection<Document> collection = database.getCollection(collectionName);
        BulkWriteResult result = collection.bulkWrite(updates, new BulkWriteOptions().ordered(false));

        // 输出日志，确认更新
        LOG.info("Matched {} documents and modified {} documents.",
                result.getMatchedCount(), result.getModifiedCount());
    }

}
