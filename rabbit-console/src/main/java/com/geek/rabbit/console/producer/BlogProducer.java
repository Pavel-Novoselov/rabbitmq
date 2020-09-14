package com.geek.rabbit.console.producer;

import com.rabbitmq.client.*;
import com.rabbitmq.tools.json.JSONUtil;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class BlogProducer {
//    private static final String TASK_QUEUE_NAME = "blog-queue";
    private static final String EXCHANGE_NAME = "ex-1";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        List<Article> articleList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("Enter theme and title of article, to stop type q");
            String line = scanner.nextLine();
            if (line.equals("q"))
                break;
            articleList.add(new Article
                    (line.substring(0, line.indexOf(" ")),
                            line.substring(line.indexOf(" "), line.length())));

        }
       articleList.stream().forEach(System.out::println);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            for (int i = 0; i < articleList.size(); i++) {
                String message = articleList.get(i).toString();
                channel.basicPublish(EXCHANGE_NAME, articleList.get(i).getTheme(), null, message.getBytes("UTF-8"));
 //               channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + message + "'");
                Thread.sleep(1000);
            }
        }


    }



}
