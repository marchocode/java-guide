package com.github.marchocode.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitRecv {

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        final String QUEUE_NAME = "hello";

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println("we are waiting the message from rabbitmq.");

            DeliverCallback callback = (str, deliver) -> {
                String message = new String(deliver.getBody());
                System.out.println("receive message str=" + str);
                System.out.println("receive message message=" + message);
            };

            channel.basicConsume(QUEUE_NAME, true, callback, consumerTag -> {});

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }


    }


}
