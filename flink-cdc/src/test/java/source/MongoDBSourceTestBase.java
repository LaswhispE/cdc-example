package source;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.apache.flink.runtime.minicluster.RpcServiceSharing;
import org.apache.flink.runtime.testutils.MiniClusterResourceConfiguration;
import org.apache.flink.test.util.MiniClusterWithClientResource;
import org.junit.BeforeClass;
import org.junit.Rule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoDBSourceTestBase {
    protected static MongoClient mongodbClient;
    protected static final int DEFAULT_PARALLELISM = 4;
//    protected static final MongoDBContainer CONTAINER = MongoDbInitializer.getContainer();

    @Rule
    public final MiniClusterWithClientResource miniClusterResource =
            new MiniClusterWithClientResource(
                    new MiniClusterResourceConfiguration.Builder()
                            .setNumberTaskManagers(1)
                            .setNumberSlotsPerTaskManager(DEFAULT_PARALLELISM)
                            .setRpcServiceSharing(RpcServiceSharing.DEDICATED)
                            .withHaLeadershipControl()
                            .build());

//    @BeforeClass
//    public static void startContainers() {
//        LOG.info("Starting containers...");
//
//        MongoClientSettings settings =
//                MongoClientSettings.builder()
//                        .applyConnectionString(
//                                new ConnectionString(CONTAINER.getConnectionString()))
//                        .build();
//        mongodbClient = MongoClients.create(settings);
//
//        LOG.info("Containers are started.");
//    }

    private static final Logger LOG = LoggerFactory.getLogger(MongoDBSourceTestBase.class);

//    @ClassRule
//    public static final MongoDBContainer CONTAINER =
//            new MongoDBContainer("mongo:6.0.9")
//                    .withSharding()
//                    .withLogConsumer(new Slf4jLogConsumer(LOG));
}
