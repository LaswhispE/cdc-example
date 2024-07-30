/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import source.MongoDBContainer;
import org.apache.flink.runtime.minicluster.RpcServiceSharing;
import org.apache.flink.runtime.testutils.MiniClusterResourceConfiguration;
import org.apache.flink.test.util.MiniClusterWithClientResource;
import org.bson.Document;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.lifecycle.Startables;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/** MongoDBSourceTestBase for MongoDB >= 5.0.3. */
public class MongoDBSourceTestBase {

    protected static MongoClient mongodbClient;

    protected static final int DEFAULT_PARALLELISM = 4;

    protected static void insertData(MongoDatabase database, String collectionName) {
        // 创建一个列表，用于存储要插入的多个Document
        List<Document> documents = new ArrayList<>();

        // 添加三个Document到列表中
        for (int i = 0; i < 10000000; i++) {
            documents.add(new Document()
                    .append("name", "spare tire " + i)
                    .append("description", "24 inch spare tire")
                    .append("weight", 22.2));
        }
        // 获取MongoCollection并批量插入数据
        MongoCollection<Document> collection = database.getCollection(collectionName);
        collection.insertMany(documents);

        // 输出日志，确认插入
        LOG.info("Inserted " + documents.size() + " documents.");
    }
    @BeforeClass

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


    @Rule
    public final MiniClusterWithClientResource miniClusterResource =
            new MiniClusterWithClientResource(
                    new MiniClusterResourceConfiguration.Builder()
                            .setNumberTaskManagers(1)
                            .setNumberSlotsPerTaskManager(DEFAULT_PARALLELISM)
                            .setRpcServiceSharing(RpcServiceSharing.DEDICATED)
                            .withHaLeadershipControl()
                            .build());

    @BeforeClass
    public static void startContainers() {
        LOG.info("Starting containers...");
        Startables.deepStart(Stream.of(CONTAINER)).join();

        MongoClientSettings settings =
                MongoClientSettings.builder()
                        .applyConnectionString(
                                new ConnectionString(CONTAINER.getConnectionString()))
                        .build();
        mongodbClient = MongoClients.create(settings);

        LOG.info("Containers are started.");
    }

    private static final Logger LOG = LoggerFactory.getLogger(MongoDBSourceTestBase.class);

    @ClassRule
    public static final MongoDBContainer CONTAINER =
            new MongoDBContainer("mongo:6.0.9")
                    .withSharding()
                    .withLogConsumer(new Slf4jLogConsumer(LOG));
}


