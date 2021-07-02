 package com.springboot.test.util.thread;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.springboot.test.util.DateUtils;

public class TreadTest {
    
    private static ArrayBlockingQueue<Object>  queue;
    private static CountDownLatch producerLatch;
    private static CountDownLatch consumerLatch;
    
    public static void main(String[] args) {
        test1();
        test2();
        Integer ss = 20000;
        int sss = 20000;
        boolean ise = sss==ss;
        System.out.println(ise);
        
        Integer aa = -128;
        Integer aaa = -128;
        boolean isa = aa==aaa;
        System.out.println(isa);
        
        Integer bb = new Integer(1);
        Integer bbb = new Integer(1);
        boolean isb = bb==bbb;
        System.out.println(isb);
        
        Integer cc = new Integer(1);
        Integer ccc = 1;
        boolean isc = cc==ccc;
        System.out.println(isc);
        
        
    }
    
    public static void test1() {
        Date start = new Date();
        queue = new ArrayBlockingQueue<>(1);
        producerLatch = new CountDownLatch(1);
        consumerLatch = new CountDownLatch(1);
        Thread t1 = new Thread(new ProducerTask(queue,producerLatch));
        Thread t2 = new Thread(new ConsumerTask(queue,consumerLatch));
        t1.start();
        t2.start();
        try {
            producerLatch.await();
        } catch (InterruptedException e) {
             e.printStackTrace();
        }
        try {
            consumerLatch.await();
        } catch (InterruptedException e) {
             e.printStackTrace();
        }
        t1.interrupt();
        t2.interrupt();
        Date end = new Date();
        System.out.println(DateUtils.getDatePoorMode2(start, end));
    }
    
    public static void test2() {
        Date start = new Date();
        queue = new ArrayBlockingQueue<>(1);
        producerLatch = new CountDownLatch(1);
        consumerLatch = new CountDownLatch(1);
        int coreNumber = Runtime.getRuntime().availableProcessors();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = df.format(new Date());
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("thread-pool-%d-"+date).build();
        ExecutorService pool = Executors.newFixedThreadPool(coreNumber,threadFactory);
        pool.execute(new ProducerTask(queue,producerLatch));
        try {
            producerLatch.await();
        } catch (InterruptedException e) {
             e.printStackTrace();
        }
        pool.execute(new ConsumerTask(queue,consumerLatch));
        try {
            consumerLatch.await();
        } catch (InterruptedException e) {
             e.printStackTrace();
        }
        pool.shutdown();
        Date end = new Date();
        System.out.println(DateUtils.getDatePoorMode2(start, end));
    }

}
