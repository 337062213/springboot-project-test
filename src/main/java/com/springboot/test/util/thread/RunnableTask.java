 package com.springboot.test.util.thread;

 public class RunnableTask implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
             e.printStackTrace();
        }        
    }
}
