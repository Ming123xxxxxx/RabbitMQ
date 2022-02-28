package com.example.demo.死信队列.TTL过期.work;

import com.example.demo.死信队列.TTL过期.utils.RabbitMQUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/2/24 7:46
 */
//接收消息
public class Customer1 {
    //普通交换机
    public static final String normal_name="normal_name";
    //死信交换机
    public static final String dead_exchange="dead_logs";
    //普通队列名称
    public static final String normal_queue="normal_queue";
    //死信队列名称
    public static final String dead_queue="dead_queue";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtils.connectionRabbitMQ();
        //声明交换机
        //参数1:交换机名字   参数2:交换机类型
        channel.exchangeDeclare(normal_name, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(dead_exchange, BuiltinExchangeType.DIRECT);
        //声明队列
        //声明临时队列,队列的名称是随机的,当消费者断开与队列的连接时,队列就会自动删除
        //声明普通队列
        //正常队列设置死信交换机
        Map<String,Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange",dead_exchange);
        //设置死信routingkey
        arguments.put("x-dead-letter-routing-key","lisi");
        //过期时间设置为10s,可以在消费者方指定过期时间,但是建议在生产者中指定
        // arguments.put("x-message-ttl",10000);
        channel.queueDeclare(normal_queue,false,false,false,arguments);
        //声明死信队列
        channel.queueDeclare(dead_queue,false,false,false,null);
        //绑定交换机与队列
        //参数1:临时队列名字   参数二:交换机名字   参数三:routingkey
        //绑定普通交换机与队列
        channel.queueBind(normal_queue,normal_name,"zhangsan");
        //绑定死信交换机与队列
        channel.queueBind(dead_queue,dead_exchange,"lisi");
        System.out.println("C1等待接收消息.....");
        channel.basicConsume(normal_queue,true,(e1,e2)->{
            System.out.println("C1接收消息为:"+new String(e2.getBody(),"UTF-8"));
        }, e ->{});
    }
}
