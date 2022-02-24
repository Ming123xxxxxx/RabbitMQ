package com.example.demo.消息应答.product;

import com.example.demo.消息应答.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/2/24 7:39
 */
public class Task2 {

    //队列名称
    public static final String task_queue_name = "ack_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtils.connectionRabbitMQ();
        //声明队列
        channel.queueDeclare(task_queue_name,false,false,false,null);
        //从控制台中输入信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish("",task_queue_name,null,message.getBytes("UTF-8"));
            System.out.println("生产者发送消息:"+message);
        }


    }
}
