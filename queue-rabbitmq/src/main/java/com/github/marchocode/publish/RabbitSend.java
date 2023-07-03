package com.github.marchocode.publish;

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

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel();
        ) {

            // fanout 将进行广播，广播到所有的已知队列。
            channel.exchangeDeclare("logs", "fanout");
            // 申明一个非持久化队列，用完会被删除
            final String QUEUE_NAME = channel.queueDeclare().getQueue();
            // 队列和交换器进行绑定
            channel.queueBind(QUEUE_NAME, "logs", "");

            System.out.println("Send: random queue name is =" + QUEUE_NAME);

            for (int i = 0; i < 100; i++) {

                String message = "log message number:" + i;

                // 交换器，使用默认的
                // 消息持久化
                channel.basicPublish("logs", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
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
