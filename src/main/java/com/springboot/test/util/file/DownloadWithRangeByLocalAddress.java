 package com.springboot.test.util.file;

 import sun.misc.Cleaner;
 import sun.nio.ch.DirectBuffer;
 import java.io.File;
 import java.io.RandomAccessFile;
 import java.nio.MappedByteBuffer;
 import java.nio.channels.FileChannel;
  
 /***
  * 获取本地资源路径，指定文件地址保存文件
  */
 public class DownloadWithRangeByLocalAddress implements Runnable {
  
     // 缓冲区大小为3M
     final int BUFFER_SIZE = 0x300000;
  
     /**本地文件地址*/
     private String localAddress;
  
     /**文件保存地址*/
     private String filePath;
  
     /**如index/10,文件拆分为十段，index为第几段*/
     private long index;
  
     /**下载起始位置*/
     private long start;
  
     /**文件大小*/
     private long len;
  
     DownloadWithRangeByLocalAddress(String localAddress, String filePath, long len,long index,long start) {
         this.localAddress = localAddress;
         this.filePath = filePath;
         this.index = index;
         this.start = start;
         this.len = len;
     }
  
     @Override
     public void run() {
         try {
             File sourcefile = new File(localAddress);
             RandomAccessFile randomAccessFile = new RandomAccessFile(sourcefile, "r");
             FileChannel channel = randomAccessFile.getChannel();
             MappedByteBuffer inputBuffer = channel.map(FileChannel.MapMode.READ_ONLY, len*index/10,len/10);
             File file = new File(filePath);
             RandomAccessFile out = null;
             if (file != null) {
                 out = new RandomAccessFile(file, "rw");
             }
             out.seek(start);
             byte[] dst = new byte[BUFFER_SIZE];// 每次读出3M的内容
  
             for (int offset = 0; offset < inputBuffer.capacity(); offset += BUFFER_SIZE) {
                 if (inputBuffer.capacity() - offset >= BUFFER_SIZE) {
  
                     for (int i = 0; i < BUFFER_SIZE; i++) {
                         dst[i] = inputBuffer.get(offset + i);
                     }
                 } else {
                     for (int i = 0; i < inputBuffer.capacity() - offset; i++) {
                         dst[i] = inputBuffer.get(offset + i);
                     }
                 }
                 out.write(dst, 0, BUFFER_SIZE);
             }
             out.close();
             inputBuffer.force();
             channel.force(true);
             channel.close();
             randomAccessFile.close();
             unmap(inputBuffer);
         } catch (Exception e) {
             e.getMessage();
         }
     }
     private void unmap(MappedByteBuffer var0) {
         Cleaner var1 = ((DirectBuffer)var0).cleaner();
         if (var1 != null) {
             var1.clean();
         }
     }
 }
