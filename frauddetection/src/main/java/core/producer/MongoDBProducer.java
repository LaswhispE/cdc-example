package core.producer;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MongoDBProducer {
    private static final Logger LOG = LoggerFactory.getLogger(MongoDBProducer.class);
    private static MongoClient mongoClient;
//    private static int currentId = 1; // 初始化自增ID

    public static void main(String[] args) throws Exception {
        connectToMongoDB();
        startScheduledInsertion();
    }

    // 连接到MongoDB
    public static void connectToMongoDB() {
        LOG.info("Connecting to MongoDB...");

        String connectionString = "mongodb://localhost:27017"; // 替换为你的MongoDB连接字符串
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .build();
        mongoClient = MongoClients.create(settings);

        LOG.info("Connected to MongoDB.");
    }

    // 启动定时插入任务
    public static void startScheduledInsertion() {
        // 获取数据库引用，例如使用test数据库和test2集合
        MongoDatabase database = mongoClient.getDatabase("test");
        String collectionName = "test2";

        // 创建ScheduledExecutorService实例
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        // 定义一个任务，每隔1秒执行一次insertData方法
        Runnable insertTask = () -> insertData(database, collectionName);
        executorService.scheduleAtFixedRate(insertTask, 0, 1, TimeUnit.SECONDS);
    }

    // 插入数据
    protected static void insertData(MongoDatabase database, String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);

        List<Document> documents = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            documents.add(new Document()
                    .append("name", generateRandomString(8))); // 随机生成的8位字符串
        }

        collection.insertMany(documents); // 批量插入文档
        LOG.info("Inserted " + documents.size() + " documents.");
    }

    // 生成随机字符串
    private static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return stringBuilder.toString();
    }
}
