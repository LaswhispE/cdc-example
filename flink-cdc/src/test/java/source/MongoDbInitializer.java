package source;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MongoDbInitializer {

    private static final Pattern COMMENT_PATTERN = Pattern.compile("^(.*)//.*$");
    private MongoClient mongoClient;
    private String connectionString;

    public MongoDbInitializer(String connectionString) {
        this.connectionString = connectionString;
        this.mongoClient = MongoClients.create(connectionString);
    }

    public String executeCommandFileInSeparateDatabase(String fileNameIgnoreSuffix) {
        String databaseName = fileNameIgnoreSuffix + "_" + Integer.toUnsignedString(new Random().nextInt(), 36);
        return executeCommandFileInDatabase(fileNameIgnoreSuffix, databaseName);
    }

    public String executeCommandFileInDatabase(String fileNameIgnoreSuffix, String databaseName) {
        final String ddlFile = String.format("ddl/%s.js", fileNameIgnoreSuffix);
        final URL ddlTestFile = getClass().getClassLoader().getResource(ddlFile);
        if (ddlTestFile == null) {
            throw new IllegalStateException("Cannot locate " + ddlFile);
        }

        try {
            MongoDatabase database = mongoClient.getDatabase(databaseName);

            String commands = Files.readAllLines(Paths.get(ddlTestFile.toURI())).stream()
                    .filter(x -> StringUtils.isNotBlank(x) && !x.trim().startsWith("//"))
                    .map(x -> {
                        final Matcher m = COMMENT_PATTERN.matcher(x);
                        return m.matches() ? m.group(1) : x;
                    })
                    .collect(Collectors.joining("\n"));

            // 在这里执行命令，可能需要进一步处理以适应MongoDB Java Driver

            return databaseName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute command file in database", e);
        }
    }

    // 关闭客户端连接
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}



