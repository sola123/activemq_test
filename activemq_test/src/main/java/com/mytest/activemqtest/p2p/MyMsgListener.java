package com.mytest.activemqtest.p2p;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
public class MyMsgListener implements MessageListener {
    private String threadName;

    public MyMsgListener(String threadName) {
        this.threadName=threadName;
    }

    @Override
    public void onMessage(Message message) {
        try {
            //获取到接收的数据
            String text = ((TextMessage)message).getText();
            System.out.println(threadName+":"+text+" ("+Thread.currentThread().getName()+")");
            Thread.sleep(1000*3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
