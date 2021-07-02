 package com.springboot.test.util.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerTask implements Runnable {
    
     private Logger logger = LoggerFactory.getLogger(ConsumerTask.class);
     private ArrayBlockingQueue<Object> queue;
     private CountDownLatch consumerLatch;
     
     public ConsumerTask() {
         super();
     }
     
     public ConsumerTask(ArrayBlockingQueue<Object> queue,CountDownLatch consumerLatch) {
         super();
         this.queue = queue;
         this.consumerLatch = consumerLatch;
     }

    @Override
    public void run() {
        try {
            logger.info(Thread.currentThread().getName() + "-start");
            queue.take();
            consumerLatch.countDown();
            logger.info(Thread.currentThread().getName() + "-end");
        } catch (InterruptedException e) {
             e.printStackTrace();
        }
        
    }
}
