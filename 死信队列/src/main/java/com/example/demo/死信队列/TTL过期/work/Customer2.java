package com.example.demo.死信队列.TTL过期.work;

import com.example.demo.死信队列.TTL过期.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/2/24 7:46
 */
//接收消息
public class Customer2 {

    //死信队列
    public static final String dead_queue="dead_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtils.connectionRabbitMQ();

        System.out.println("C2等待接收消息");
        channel.basicConsume(dead_queue,true,(e1,e2)->{
            System.out.println("C2接收消息为:"+new String(e2.getBody(),"UTF-8"));
        }, e ->{});
    }
}

