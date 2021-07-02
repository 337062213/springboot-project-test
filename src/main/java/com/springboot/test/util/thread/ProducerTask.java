 package com.springboot.test.util.thread;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerTask implements Runnable {
     private Logger logger = LoggerFactory.getLogger(ProducerTask.class);
     private ArrayBlockingQueue<Object> queue;
     private CountDownLatch producerLatch;
     private Random random = new Random();
     
     public ProducerTask() {
         super();
     }
     
     public ProducerTask(ArrayBlockingQueue<Object> queue,CountDownLatch producerLatch) {
         super();
         this.queue = queue;
         this.producerLatch = producerLatch;
     }

    @Override
    public void run() {
        try {
            logger.info(Thread.currentThread().getName() + "-start");
            String value = String.valueOf(random.nextInt(100));
            queue.put(value);
            producerLatch.countDown();
            logger.info(Thread.currentThread().getName() + "-end");
        } catch (InterruptedException e) {
             e.printStackTrace();
        }
        
    }
}
