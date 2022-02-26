package com.example.demo.direct.work;

import com.example.demo.不公平分发.utils.RabbitMQUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/2/24 7:46
 */
//接收消息
public class Customer2 {
    //交换机的名称
    public static final String exchange_name="direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtils.connectionRabbitMQ();
        //声明交换机
        //参数1:交换机名字   参数2:交换机类型
        channel.exchangeDeclare(exchange_name, BuiltinExchangeType.DIRECT);
        //声明队列
        //声明临时队列,队列的名称是随机的,当消费者断开与队列的连接时,队列就会自动删除
        channel.queueDeclare("disk",false,false,false,null);
        //绑定交换机与队列
        //参数1:临时队列名字   参数二:交换机名字   参数三:routingkey
        channel.queueBind("disk",exchange_name,"info");
        channel.queueBind("disk",exchange_name,"error");
        System.out.println("C2等待接收消息");
        channel.basicConsume("disk",true,(e1,e2)->{
            System.out.println("C2接收消息为:"+new String(e2.getBody(),"UTF-8"));
        }, e ->{});
    }
}

