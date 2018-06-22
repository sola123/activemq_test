package com.mytest.activemqtest.p2p;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import com.mytest.activemqtest.ConnectRes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 点对点
 * 消息接受端
 */
public class PTPReceive {
    //连接对象
    private Connection connection;
    //一个操作会话
    private Session session;
    //目的地，其实就是连接到哪个队列，如果是点对点，那么它的实现是Queue，如果是订阅模式，那它的实现是Topic
    private Destination destination;
    //消费者，就是接收数据的对象
    private MessageConsumer consumer;

    public void start(){
        try {

            //从工厂中获取一个连接
            connection = ConnectRes.factory.createConnection();
            //测试过这个步骤不写也是可以的，但是网上的各个文档都写了
            connection.start();
            //创建一个session
            //第一个参数:是否支持事务，如果为true，则会忽略第二个参数，被jms服务器设置为SESSION_TRANSACTED
            //第二个参数为false时，paramB的值可为Session.AUTO_ACKNOWLEDGE，Session.CLIENT_ACKNOWLEDGE，DUPS_OK_ACKNOWLEDGE其中一个。
            //Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。哪怕是接收端发生异常，也会被当作正常发送成功。
            //Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会当作发送成功，并删除消息。
            //DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建一个到达的目的地，其实想一下就知道了，activemq不可能同时只能跑一个队列吧，这里就是连接了一个名为"text-msg"的队列，这个会话将会到这个队列，当然，如果这个队列不存在，将会被创建
            destination = session.createQueue("text-msg");
            //根据session，创建一个接收者对象
            consumer = session.createConsumer(destination);

            //实现一个消息的监听器
            //实现这个监听器后，以后只要有消息，就会通过这个监听器接收到
            consumer.setMessageListener(new MyMsgListener(Thread.currentThread().getName()));
            //关闭接收端，也不会终止程序哦
//            consumer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService threadPool= Executors.newFixedThreadPool(5);
        PTPReceive receive = new PTPReceive();
        //每个线程要传递一个新的消费者进去执行监听,成员变量connection相同对象共享，导致多线程无法并行，无意义
        for(int i=5;i>0;i--){
            threadPool.submit(new ReceiverThread(new PTPReceive()));
        }
//       for(int i=5;i>0;i--){
//           Thread t=new Thread(new ReceiverThread(new PTPReceive()));
//           t.start();
//       }

    }
}
