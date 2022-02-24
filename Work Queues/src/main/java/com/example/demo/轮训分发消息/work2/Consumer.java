package com.example.demo.轮训分发消息.work2;

import com.example.demo.轮训分发消息.util.RabbitMQUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/2/23 11:02
 */
public class Consumer {

    //队列名称
    public static final String QUEUE_NAME="Hello";
    //接收消息
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.connectionRabbitMQ();
        //消息的接收
        DeliverCallback deliverCallback = (c, m) -> System.out.println("接收的消息为:"+new String(m.getBody()));
        CancelCallback callback1 = e -> System.out.println(e+":消费者取消消息接口回调逻辑");
        System.out.println("Consumer等待接收消息");
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,callback1);
    }
}
