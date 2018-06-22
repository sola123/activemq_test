package com.mytest.activemqtest.p2p;

public class ReceiverThread implements Runnable {
    private PTPReceive receive;

    public ReceiverThread(PTPReceive receive) {
        this.receive = receive;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" is running");
        receive.start();
    }
}
