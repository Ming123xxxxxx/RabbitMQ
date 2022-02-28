package com.example.demo.死信队列.TTL过期.product;

import com.example.demo.死信队列.TTL过期.utils.RabbitMQUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/2/24 7:39
 */

//接收消息
public class Task2 {
    //交换机的名称
    public static final String normal_name="normal_name";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtils.connectionRabbitMQ();
        //声明交换机
        //参数1:交换机名字   参数2:交换机类型
        channel.exchangeDeclare(normal_name, BuiltinExchangeType.DIRECT);
        int i =0;
        //死信消息 设置TTL时间 过期时间为10000毫秒,即为10秒
        AMQP.BasicProperties properties=new AMQP.BasicProperties().builder().expiration("10000").build();

        while(i<10){
                String message = "info"+i;
                channel.basicPublish(normal_name,"zhangsan",properties,message.getBytes("UTF-8"));
                System.out.println("生产者发送消息为:"+message);
            i++;
        }
    }
}
