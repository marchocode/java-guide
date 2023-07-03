package com.github.marchocode.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitSend {

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        final String QUEUE_NAME = "hello";

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel();
        ) {
            // 申明一个队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            for (int i = 0; i < 100; i++) {

                String message = "message number:" + i;
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
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
