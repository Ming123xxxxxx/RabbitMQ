package com.example.demo.轮训分发消息.work1;

import com.example.demo.轮训分发消息.util.RabbitMQUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/2/23 11:08
 */

//生产者,发送大量消息
public class Task {

    public static final String QUEUE_NAME="Hello";

    //发消息
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.connectionRabbitMQ();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = null;
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            message = scanner.next();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("消息发送完毕:"+message);
        }

    }
}
