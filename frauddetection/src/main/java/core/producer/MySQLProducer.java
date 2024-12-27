package core.producer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySQLProducer {

    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String INSERT_SQL = "INSERT INTO user (username,password) VALUES (?,?)";

    // 创建日志记录器实例
    private static final Logger LOG = LoggerFactory.getLogger(MySQLProducer.class);

    public static void main(String[] args) throws Exception {
        startScheduledInsertion();
    }

    // 启动定时插入任务
    public static void startScheduledInsertion() {
        // 创建ScheduledExecutorService实例
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        // 定义一个任务，每隔1秒执行一次insertData方法
        Runnable insertTask = MySQLProducer::insertData;
        executorService.scheduleAtFixedRate(insertTask, 0, 1, TimeUnit.SECONDS);
    }

    // 插入数据
    private static void insertData() {
        List<String> usernames = generateRandomString(100);
        List<String> passwords = generateRandomString(100); // 注意这里应该是 passwords 而不是 password

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            connection.setAutoCommit(false); // 关闭自动提交

            try (PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
                for (int i = 0; i < usernames.size(); i++) {
                    statement.setString(1, usernames.get(i));
                    statement.setString(2, passwords.get(i)); // 同时设置用户名和密码
                    statement.addBatch();
                }

                int[] affectedRows = statement.executeBatch();
                connection.commit(); // 提交事务

                LOG.info("Inserted " + affectedRows.length + " rows.");
            } catch (SQLException e) {
                connection.rollback(); // 如果发生异常，回滚事务
                LOG.error("Error inserting data", e);
            }
        } catch (SQLException e) {
            LOG.error("Error connecting to database", e);
        }
    }
    // 生成随机字符串列表
    private static List<String> generateRandomString(int count) {
        List<String> names = new ArrayList<>(count);
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < 8; j++) {
                stringBuilder.append(characters.charAt(random.nextInt(characters.length())));
            }
            names.add(stringBuilder.toString());
        }
        return names;
    }
}
