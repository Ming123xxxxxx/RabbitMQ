package com.example.springbootrabbitmq.TTL队列.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/2/28 9:02
 */
//TTL队列  配置文件类代码
@Configuration
public class TTLQueueConfig {
    //普通交换机名称
    public static final String X_NORMAL_EXCHANGE="X";
    //死信交换机名称
    public static final String Y_DEAD_EXCHANGE="Y";
    //普通队列名称
    public static final String A_NORMAL_QUEUE="QA";
    public static final String B_NORMAL_QUEUE="QB";
    //死信队列名称
    public static final String Y_DEAD_QUEUE="QD";

    //声明X_NORMAL_EXCHANGE
    @Bean("XExchange")
    public DirectExchange Xexchange(){
        return new DirectExchange(X_NORMAL_EXCHANGE);
    }

    //声明Y_DEAD_EXCHANGE
    @Bean("YExchange")
    public DirectExchange Yexchange(){
        return new DirectExchange(Y_DEAD_EXCHANGE);
    }

    //声明普通队列QA的TTL为10s
    @Bean("AQueue")
    public Queue aqueue(){
        Map<String, Object> map = new HashMap<>(3);
        //设置死信交换机
        map.put("x-dead-letter-exchange",Y_DEAD_EXCHANGE);
        //设置死信RoutingKey
        map.put("x-dead-letter-routing-key","YD");
        //设置TTL为10s
        map.put("x-message-ttl",10000);
        return (Queue) QueueBuilder.durable(A_NORMAL_QUEUE).withArguments(map).build();
    }

    //声明普通队列QB的TTL为40s
    @Bean("BQueue")
    public Queue bqueue(){
        Map<String, Object> map = new HashMap<>(3);
        //设置死信交换机
        map.put("x-dead-letter-exchange",Y_DEAD_EXCHANGE);
        //设置死信RoutingKey
        map.put("x-dead-letter-routing-key","YD");
        //设置TTL为10s
        map.put("x-message-ttl",40000);
        return (Queue) QueueBuilder.durable(B_NORMAL_QUEUE).withArguments(map).build();
    }

    //声明死信队列QD
    @Bean("DQueue")
    public Queue DQueue(){
        return (Queue) QueueBuilder.durable(Y_DEAD_QUEUE).build();
    }

    //绑定 将队列QA绑定X交换机  routingkey为:XA
    @Bean
    public Binding AQueueToBingdingXExchange(@Qualifier("AQueue") Queue Aqueue,
                                             @Qualifier("XExchange") DirectExchange XExchange){
        return BindingBuilder.bind(Aqueue).to(XExchange).with("XA");
    }

    //绑定 将队列QB绑定X交换机  routingkey为:XB
    @Bean
    public Binding BQueueToBingdingXExchange(@Qualifier("BQueue") Queue Bqueue,
                                             @Qualifier("XExchange") DirectExchange XExchange){
        return BindingBuilder.bind(Bqueue).to(XExchange).with("XB");
    }

    //绑定 将队列QD绑定Y交换机  routingkey为:YD
    @Bean
    public Binding DQueueToBingdingYExchange(@Qualifier("DQueue") Queue Dqueue,
                                             @Qualifier("YExchange") DirectExchange YExchange){
        return BindingBuilder.bind(Dqueue).to(YExchange).with("YD");
    }
}
