 package com.springboot.test.util;

 import java.io.File;
 import java.io.FileNotFoundException;
 import java.io.IOException;
 import java.io.RandomAccessFile;

 class CopyFileThread extends Thread{
     private RandomAccessFile in;
     private RandomAccessFile out;
     private long start;
     private long end;

     /**
      * 
      * @param in 源文件地址
      * @param out 目标文件地址
      * @param start 分段复制的开始位置
      * @param end  分段复制的结束位置
      */
     public CopyFileThread(String in, String out,
             long start, long end){
         this.start = start;
         this.end = end;
         try {
             this.in = new RandomAccessFile(in, "rw");
             this.out = new RandomAccessFile(out, "rw");
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }
     }

     public void run(){
         Thread current = Thread.currentThread();
         System.out.println("Name:" + current.getName() + ",Priority:" + current.getPriority() + ",Id:" + current.getId()  + ",activeCount:" + Thread.activeCount() + ",hashCode:" + current.hashCode());
         try {
             in.seek(start);
             out.seek(start);
             int hasRead = 0;
             byte[] buff = new byte[1024*1024];
             while(start<end && (hasRead = in.read(buff)) != -1){
                 start += hasRead;
                 out.write(buff, 0, hasRead);
             }
         } catch (IOException e) {
             e.printStackTrace();
         }finally{
             try {
                 in.close();
                 out.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
     }
 }

 public class ThreadsCopyFile {

     /**
      * 
      * @param sourcePath 源文件路径
      * @param targetPath 目标文件路径
      * @param ThreadNums 设定的线程数
      * @throws IOException
      */
     public void startCopy(String sourcePath, String targetPath, int ThreadNums)
             throws IOException{
         long fileLength = new File(sourcePath).length();
         //很有可能文件长度线程不能均分下载，预留一个线程复制最后剩余的部分
         int num  = ThreadNums-1;
         if(ThreadNums==1) {
             num=1;
         }
         long segmentLength = fileLength/num;
         for (int i = 0; i < ThreadNums-1; i++) {
             new CopyFileThread(sourcePath, targetPath, i*segmentLength, (i+1)*segmentLength).start();
         }
         new CopyFileThread(sourcePath, targetPath, (ThreadNums-1)*segmentLength, fileLength).start();
     }


     public static void main(String[] args) throws IOException {
         ThreadsCopyFile tcf = new ThreadsCopyFile();
         System.out.println(DateUtils.getCurrentTime1());
         String sourcePath = "C:\\Users\\EDZ\\Desktop\\test file\\抖音.mp4";
         String targetPath = "C:\\Users\\EDZ\\Desktop\\tttt32452.mp4";
         tcf.startCopy(sourcePath, targetPath,10);
         System.out.println(DateUtils.getCurrentTime1());
     }
 }