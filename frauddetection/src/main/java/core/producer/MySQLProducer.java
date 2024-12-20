package core.producer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class MySQLProducer {

    private static final String URL = "jdbc:mysql://10.49.2.7:3306/test";
    private static final String USER = "bigdata_user";
    private static final String PASSWORD = "4Lme3Bn0wdkRY@5qM3a2j0ISE";
    private static final String INSERT_SQL = "INSERT INTO test (name) VALUES (?)";

    // 创建日志记录器实例
    private static final Logger logger = Logger.getLogger(MySQLProducer.class.getName());

    public static void main(String[] args) {
        // 创建 Timer 实例
        Timer timer = new Timer();

        // 创建 TimerTask，每秒执行一次
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // 每秒插入 10000 条数据
                for (int i = 0; i < 10000; i++) {
                    insertData();
                }
            }
        };

        // 安排 TimerTask，每秒执行一次
        timer.schedule(task, 0, 1000);
    }

    private static void insertData() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {

            // 设置随机名称
            String randomName = generateRandomName();
            statement.setString(1, randomName);

            // 执行插入操作
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                // 插入成功，记录日志
                logger.info("Data inserted successfully: name = " + randomName);
            }
        } catch (SQLException e) {
            // 记录异常日志
            logger.severe("Failed to insert data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String generateRandomName() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            stringBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return stringBuilder.toString();
    }
}
