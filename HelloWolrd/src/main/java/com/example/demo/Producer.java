package com.example.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
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
        //创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //工厂IP连接RabbitMQ队列
        factory.setHost("192.168.118.130");
        //设置用户名和密码
        factory.setUsername("admin");
        factory.setPassword("15172415712");
        //创建连接
        Connection connection = factory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();
        //生成一个队列
        /*
          参数一:队列名称
          参数二:是否持久化, 队列的声明默认是存放到内存中的，如果rabbitmq重启会丢失，
          如果想重启之后还存在就要使队列持久化，保存到Erlang自带的Mnesia数据库中，当rabbitmq重启之后会读取该数据库
          参数三:该队列是否只供一个消费者进行消费,是否进行消息的共享,false表示可以多个消费者进行消费
          参数四:是否自动删除   最后一消费者端开连接以后 该队列是否自动删除  true自动删除
          参数五:其他参数
         */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String message = "hello,RabbitMQ";//初次使用
        //发消息
        /*
          发送一个消息
          参数一:发送到哪个交换机 ,""代表不使用交换机
          参数二:路由的key值是哪个,本次是队列的名称
          参数三:其他参数信息
          参数四:发送消息的消息体,消息体为byte类型
         */
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("消息发送完毕");
    }
}
