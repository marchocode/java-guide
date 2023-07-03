package com.github.marchocode.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitSend {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel();
        ) {

            // DIRECT 按照路由键进行
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            String[] errors = new String[]{"info", "warn", "error"};
            String[] origin = new String[]{"sys", "net", "nginx"};

            // 产生不同种类的日志
            for (int i = 0; i < 100; i++) {

                int index = (int) (Math.random() * errors.length);
                int origin_index = (int) (Math.random() * errors.length);

                String message = "[" + origin[origin_index] + "]" + "[" + errors[index] + "] log message number:" + i;
                String router = String.format("%s.%s", origin[origin_index], errors[index]);

                // 交换器，使用默认的
                // 消息持久化
                channel.basicPublish(EXCHANGE_NAME, router, null, message.getBytes());
                Thread.sleep(1000L);
            }
            System.out.println("finish send");

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
