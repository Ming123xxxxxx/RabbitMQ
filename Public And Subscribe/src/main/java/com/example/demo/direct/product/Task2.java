package com.example.demo.direct.product;

import com.example.demo.不公平分发.utils.RabbitMQUtils;
import com.rabbitmq.client.BuiltinExchangeType;
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
    public static final String exchange_name="direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtils.connectionRabbitMQ();
        //声明交换机
        //参数1:交换机名字   参数2:交换机类型
        channel.exchangeDeclare(exchange_name, BuiltinExchangeType.DIRECT);
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("给哪个消费者发送消息(C1为info和warning)(c2为info和error)");
            String routingkey = scanner.next();
            if(routingkey.equals("info")||routingkey.equals("warning")||routingkey.equals("error")){
                String message = scanner.next();
                channel.basicPublish(exchange_name,routingkey,null,message.getBytes("UTF-8"));
                System.out.println("生产者发送消息为:"+message);
            }else{
                System.out.println("请输入指定类型数据");
            }

        }
    }
}
