package com.github.marchocode.worker;

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

        final String QUEUE_NAME = "durable_queue";

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();

            // 在没确认消息之前，这个队列只能处理一条数据
            channel.basicQos(1);
            // 持久化
            boolean durable = true;
            // 申明一个队列
            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);


            System.out.println("we are waiting the message from rabbitmq.");

            DeliverCallback callback = (str, deliver) -> {

                String message = new String(deliver.getBody());
                System.out.println("receive message message=" + message);

                try {
                    Thread.sleep(2000L);
                    System.out.println("I have finished works.");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    channel.basicAck(deliver.getEnvelope().getDeliveryTag(), false);
                }

            };

            // 所有的消息都被确认
            // boolean isAck = true;
            // 手动确认消息
            boolean isAck = false;
            channel.basicConsume(QUEUE_NAME, isAck, callback, consumerTag -> {});

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }


    }


}
