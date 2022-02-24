package com.example.demo.发布确认.product;

import com.example.demo.发布确认.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.util.UUID;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/2/24 10:29
 */
public class BatchCTest {
    public static void batchs() throws Exception {
        //队列名称
        String task_queue_name = UUID.randomUUID().toString();
        Channel channel = RabbitMQUtils.connectionRabbitMQ();
        //声明队列
        //声明队列持久化
        boolean durable = true;
        channel.queueDeclare(task_queue_name, durable, false, false, null);
        //开启发布确认
        channel.confirmSelect();
        //开始时间
        long start = System.currentTimeMillis();
        int i = 1;
        //批量发送消息
        while (i <= 1000) {
            //MessageProperties.PERSISTENT_TEXT_PLAINs:声明消息持久化
            channel.basicPublish("", task_queue_name, MessageProperties.PERSISTENT_TEXT_PLAIN, String.valueOf(i).getBytes("UTF-8"));
            if(i%100==0){
                //批量消息确认发布
             channel.waitForConfirms();
            }
            i++;
        }
        long end = System.currentTimeMillis();
        System.out.println("发布1000个单独确认消息,耗时:"+(end-start)+"毫秒");
    }
}
