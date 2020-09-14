package com.geek.rabbit.console.consumer;

import com.rabbitmq.client.*;

import java.util.Scanner;

public class BlogConsumer {
    private static final String EXCHANGE_NAME = "ex-1";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        String queueName = channel.queueDeclare().getQueue();
        System.out.println("My queue name: " + queueName);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter topic ");
        String routingKey = scanner.nextLine();

        channel.queueBind(queueName, EXCHANGE_NAME, routingKey);

        System.out.println(" [*] Waiting for messages  with routing key: " + routingKey);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}
