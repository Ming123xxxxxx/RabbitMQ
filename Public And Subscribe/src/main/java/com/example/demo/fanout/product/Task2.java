package com.example.demo.fanout.product;

import com.example.demo.不公平分发.utils.RabbitMQUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/2/24 7:39
 */

//接收消息
public class Task2 {
    //交换机的名称
    public static final String exchange_name="logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtils.connectionRabbitMQ();
        //声明交换机
        //参数1:交换机名字   参数2:交换机类型
        channel.exchangeDeclare(exchange_name,"fanout");
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish(exchange_name,"",null,message.getBytes("UTF-8"));
            System.out.println("生产者发送消息为:"+message);
        }
    }
}
