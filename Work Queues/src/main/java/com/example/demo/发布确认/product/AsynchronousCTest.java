package com.example.demo.发布确认.product;

import com.example.demo.发布确认.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/2/24 10:30
 */
public class AsynchronousCTest {

    //异步确认发布
    public static void acTest() throws Exception{
        //队列名称
        String task_queue_name = UUID.randomUUID().toString();
        Channel channel = RabbitMQUtils.connectionRabbitMQ();
        //声明队列
        //声明队列持久化
        boolean durable = true;
        channel.queueDeclare(task_queue_name, durable, false, false, null);
        //开启发布确认
        channel.confirmSelect();
        //线程安全有序的哈希表,使用于高并发的情况下
        //ConcurrentSkipListMap作用:
        //1.能轻松的将序号于消息进行关联
        //2.轻松批量删除条目,只要给到序号
        //支持高并发(多线程)
        ConcurrentSkipListMap<Long,String> outstanding = new ConcurrentSkipListMap<Long, String>();
        //开始时间
        long start = System.currentTimeMillis();
        //批量发布消息
        int i=1;
        /*
          消息确认成功时回调的函数
          参数一:消息的标记
          参数二:是否为批量确认
         */
        ConfirmCallback callback =(deliveryTag,multiple) ->{
            //删除已经确认的消息,剩下的就是未确认的消息
            if(multiple){
                ConcurrentNavigableMap<Long, String> longStringConcurrentNavigableMap = outstanding.headMap(deliveryTag);
                longStringConcurrentNavigableMap.clear();
            }else{
                outstanding.remove(deliveryTag);
            }
            System.out.println("确认的消息:"+deliveryTag);
        };
        //消息确认失败时回调的函数
        ConfirmCallback nackCallBack =(deliveryTag,multiple) ->{
            //打印未确认的消息都有哪些
            String s = outstanding.get(deliveryTag);
            System.out.println("未确认的消息:"+s+"---未确认的消息的标记:"+deliveryTag);
        };
        /*
          准备消息的监听器,监听哪些消息发布成功或失败
          参数一:监听哪些消息成功了
          参数二:监听哪些消息失败了
         */
        channel.addConfirmListener(callback,nackCallBack);//异步通知
        while(i<=1000){
            String message ="消息"+i;
            channel.basicPublish("",task_queue_name,null,message.getBytes());
            //此处记录下所有要发送的消息
            outstanding.put(channel.getNextPublishSeqNo(),message);
            i++;
        }
        long end = System.currentTimeMillis();
        System.out.println("发布1000个单独确认消息,耗时:"+(end-start)+"毫秒");
    }

}
