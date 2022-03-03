package com.example.demo;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/2/23 9:55
 */
//消费者 目的:接受消息
public class Consumer {
    //队列名称
    public static final String QUEUE_NAME="Hello";

    public static void main(String[] args) throws IOException, TimeoutException {

        Channel channel = RabbitMQConfig.channel();
        //消费者接受消息
        /*
          参数一:消费哪个队列
          参数二:消费成功后是否要自动答 true代表自动答
          参数三:消费者未成功消费的回调
          参数四:消费者取消消费者的回调
         */
        DeliverCallback deliverCallback = (c, m) -> System.out.println(new String(m.getBody()));
        CancelCallback callback1 = e -> System.out.println("消息消费被中断");

        channel.basicConsume(QUEUE_NAME,true,deliverCallback,callback1);
    }
}
