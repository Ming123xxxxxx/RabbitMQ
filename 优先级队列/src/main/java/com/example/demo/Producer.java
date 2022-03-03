package com.example.demo;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/2/23 9:27
 */

//生产者,目的:发送消息
public class Producer {
    //队列名称
    public static final String QUEUE_NAME="Hello";
    //发消息
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQConfig.channel();
        //生成一个队列
        /*
          参数一:队列名称
          参数二:是否持久化, 队列的声明默认是存放到内存中的，如果rabbitmq重启会丢失，
          如果想重启之后还存在就要使队列持久化，保存到Erlang自带的Mnesia数据库中，当rabbitmq重启之后会读取该数据库
          参数三:该队列是否只供一个消费者进行消费,是否进行消息的共享,false表示可以多个消费者进行消费
          参数四:是否自动删除   最后一消费者端开连接以后 该队列是否自动删除  true自动删除
          参数五:其他参数
         */
        //设置最大优先级,官方允许0-255,推荐0-10
        Map<String,Object> map = new HashMap<>();
        map.put("x-max-priority",10);
        channel.queueDeclare(QUEUE_NAME,true,false,false,map);
        //发消息
        /*
          发送一个消息
          参数一:发送到哪个交换机 ,""代表不使用交换机
          参数二:路由的key值是哪个,本次是队列的名称
          参数三:其他参数信息
          参数四:发送消息的消息体,消息体为byte类型
         */
        for(int i =0;i<10;i++){
            String message="info---"+i;
            if(i==5){
                //若i==5,则设置优先级为7
                AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().priority(7).build();
                channel.basicPublish("",QUEUE_NAME,properties,message.getBytes());
            }else{
                channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            }
        }
        System.out.println("消息发送成功");
    }
}
