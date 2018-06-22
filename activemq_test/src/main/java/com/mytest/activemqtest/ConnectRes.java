package com.mytest.activemqtest;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.ConnectionFactory;

/**
 * Created by Administrator on 2018/6/22.
 */
public class ConnectRes {
    //连接账号
    public static final String USERNAME = "admin";
    //连接密码
    public static final String PASSWORD = "admin";
    //连接地址
    public static final String BROKERURL = "tcp://192.168.217.128:61616";

    //connection的工厂
    public static ConnectionFactory factory;

    static {
        //根据用户名，密码，url创建一个连接工厂
        factory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);
    }
}
