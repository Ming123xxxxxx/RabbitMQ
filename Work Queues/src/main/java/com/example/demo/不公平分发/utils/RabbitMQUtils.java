package com.example.demo.不公平分发.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/2/23 10:48
 */

//此类为连接工厂,创建信道的工具类
public class RabbitMQUtils {

    public static Channel connectionRabbitMQ() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        //工厂IP连接RabbitMQ队列
        factory.setHost("192.168.118.130");
        //设置用户名和密码
        factory.setUsername("admin");
        factory.setPassword("15172415712");
        //创建连接
        Connection connection = factory.newConnection();
        //获取信道
        return connection.createChannel();
    }
}
