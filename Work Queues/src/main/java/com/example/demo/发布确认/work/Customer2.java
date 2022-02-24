package com.example.demo.发布确认.work;

import com.example.demo.发布确认.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/2/24 7:46
 */
public class Customer2 {
    //队列名称
    public static final String task_queue_name = "ack_queue";
    //接收消息
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtils.connectionRabbitMQ();
        System.out.println("C2等待接收消息时间较长");
        DeliverCallback callback = (e1, e2) -> {
            //沉睡1秒后进行应答
            try {
                Thread.sleep(30000);
                System.out.println("接收到的消息:"+new String(e2.getBody(),"UTF-8"));
                //手动应答
                /*
                参数一:消息的标记 tag
                参数二:是否批量应答,false表示不批量应答信道中的消息
                 */
                channel.basicAck(e2.getEnvelope().getDeliveryTag(),false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        //设置预取值
        int prefetchCount=5;
        channel.basicQos(prefetchCount);
        //手动应答接收消息
        boolean autoAck=false;
        channel.basicConsume(task_queue_name,autoAck,callback,(e) -> {
            System.out.println("消费者取消消费接口回调逻辑");
        });
    }
}
