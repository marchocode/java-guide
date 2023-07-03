package com.github.marchocode.publish;

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

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare("logs", "fanout");
            // 申明一个队列
            final String QUEUE_NAME = channel.queueDeclare().getQueue();
            channel.queueBind(QUEUE_NAME, "logs", "");

            System.out.println("Receive: random queue name is =" + QUEUE_NAME);
            System.out.println("we are waiting the message from rabbitmq.");

            DeliverCallback callback = (str, deliver) -> {

                String message = new String(deliver.getBody());
                System.out.println("receive message message=" + message);
            };

            // 所有的消息都被确认
            // boolean isAck = true;
            boolean isAck = true;
            channel.basicConsume(QUEUE_NAME, isAck, callback, consumerTag -> {
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }


    }


}
