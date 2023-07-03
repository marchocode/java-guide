package com.github.marchocode.router;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitRecv {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("args must have length.");
            System.exit(1);
        }

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            // 申明一个队列
            final String QUEUE_NAME = channel.queueDeclare().getQueue();

            for (String arg : args) {
                System.out.println("start bind log key: " + arg);
                channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, arg);
            }

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
