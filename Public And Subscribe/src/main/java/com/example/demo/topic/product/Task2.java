package com.example.demo.topic.product;

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
    public static final String exchange_name="topic_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtils.connectionRabbitMQ();
        //声明交换机
        //参数1:交换机名字   参数2:交换机类型
        channel.exchangeDeclare(exchange_name, BuiltinExchangeType.TOPIC);
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("给哪个消费者发送消息(C1为*.orange.*)(c2为lazy.#和*.*.rabbit)");
            String routingkey = scanner.next();
            char[] chars =routingkey.toCharArray();
            //计算routingkey中出现"."的次数
            int i=0;
            for(char c :chars){
                if(String.valueOf(c).equals(".")){
                    i=i+1;
                }
            }
            if((routingkey.startsWith("orange.",routingkey.indexOf(".")+1)&&i==2)
                    ||routingkey.startsWith("lazy.")
                    ||routingkey.startsWith("lazy")
                    ||routingkey.endsWith(".rabbit")&&i==2){
                String message = scanner.next();
                channel.basicPublish(exchange_name,routingkey,null,message.getBytes("UTF-8"));
                System.out.println("生产者发送消息为:"+message);
            }else{
                System.out.println("请输入指定类型数据");
            }
        }
    }
}
