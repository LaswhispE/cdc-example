package source;

import org.apache.pulsar.client.api.*;

public class PulsarConsumerExample {

    private static final String SERVICE_URL = "pulsar://localhost:6650"; // Pulsar集群URL
    private static final String TOPIC_NAME = "persistent://public/default/my-topic"; // 你的topic名
    private static final String SUBSCRIPTION_NAME = "my-subscription"; // 订阅名

    public static void main(String[] args) throws Exception {

        // 创建Pulsar客户端
        PulsarClient client = PulsarClient.builder()
                .serviceUrl(SERVICE_URL)
                .build();

        // 创建消费者
        Consumer<byte[]> consumer = client.newConsumer()
                .topic(TOPIC_NAME)
                .subscriptionName(SUBSCRIPTION_NAME)
                .subscriptionType(SubscriptionType.Shared) // Shared订阅模式
//                .batchReceivePolicy(BatchReceivePolicy.builder()
//                        .maxNumMessages(100)
//                        .maxNumBytes(1024 * 1024)
//                        .timeout(200, TimeUnit.MILLISECONDS)
//                        .build())
                .subscribe();

        // 循环接收消息
        while (true) {
            // 等待接收消息
            Message<byte[]> msg = consumer.receive();

            try {
                // 打印消息内容
                String content = new String(msg.getData());
                System.out.printf("Message received: %s%n", content);

                // 确认消息，以便它不会再次被发送
                consumer.acknowledge(msg);
            } catch (Exception e) {
                // 无法处理消息，重试
                consumer.negativeAcknowledge(msg);
            }
        }

        // 实际应用中，你应该在适当的时候关闭客户端
        // client.close();
    }
}