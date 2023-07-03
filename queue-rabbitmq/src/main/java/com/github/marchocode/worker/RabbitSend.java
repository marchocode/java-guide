package com.github.marchocode.worker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitSend {

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        final String QUEUE_NAME = "durable_queue";

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel();
        ) {

            // 持久化
            boolean durable = true;
            // 申明一个队列
            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);


            for (int i = 0; i < 100; i++) {

                String message = "message number:" + i;

                // 交换器，使用默认的
                channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
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
