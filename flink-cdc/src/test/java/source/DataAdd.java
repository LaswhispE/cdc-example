package source;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class DataAdd{
    private static final Logger LOG = LoggerFactory.getLogger(DataAdd.class);
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
        Runnable insertTask = () -> insertData(database, collectionName);
        executorService.scheduleAtFixedRate(insertTask, 0, 1, TimeUnit.SECONDS);
    }

    protected static void insertData(MongoDatabase database, String collectionName) {
        // 创建一个列表，用于存储要插入的多个Document
        List<Document> documents = new ArrayList<>();

        // 添加三个Document到列表中
        for (int i = 0; i < 100; i++) {
            documents.add(new Document()
                    .append("name", "spare tire " + i)
                    .append("description", "")
                    .append("weight", ""));
        }
        // 获取MongoCollection并批量插入数据
        MongoCollection<Document> collection = database.getCollection(collectionName);
        collection.insertMany(documents);

        // 输出日志，确认插入
        LOG.info("Inserted " + documents.size() + " documents.");
    }

}
