 package com.springboot.test.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {
     
     public static void main2 (String[] arg) throws IOException {
         int serverPort = 8088;
         ServerSocket serverSocket = new ServerSocket(serverPort);
         System.out.println("server将一直等待连接的到来");
         Socket socket = serverSocket.accept();
         InputStream inputStream = socket.getInputStream();
         byte[] bytes;
         while (true) {
             // 首先读取两个字节表示的长度
             int first = inputStream.read();
             System.out.println(first);
             //如果读取的值为-1 说明到了流的末尾，Socket已经被关闭了，此时将不能再去读取
             if(first==-1){
               break;
             }
             int second = inputStream.read();
             System.out.println(second);
             int length = 2 + second;
             // 然后构造一个指定长的byte数组
             bytes = new byte[length];
             // 然后读取指定长度的消息即可
             inputStream.read(bytes);
             System.out.println("get message from client: " + new String(bytes, "UTF-8"));
           }
         OutputStream outputStream = socket.getOutputStream();
         outputStream.write("你好客户端 ，很高兴收到你的消息".getBytes("UTF-8"));
         inputStream.close();
         outputStream.close();
         socket.close();
         serverSocket.close();
     }
     public static void main0(String[] args) throws Exception {
         // 监听指定的端口
         int port = 8088;
         ServerSocket server = new ServerSocket(port);
         
         // server将一直等待连接的到来
         System.out.println("server将一直等待连接的到来");
         Socket socket = server.accept();
         // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
         InputStream inputStream = socket.getInputStream();
         byte[] bytes = new byte[1024];
         int len;
         StringBuilder sb = new StringBuilder();
         //只有当客户端关闭它的输出流的时候，服务端才能取得结尾的-1
         while ((len = inputStream.read(bytes)) != -1) {
           // 注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
           sb.append(new String(bytes, 0, len, "UTF-8"));
         }
         System.out.println("get message from client: " + sb);
      
         OutputStream outputStream = socket.getOutputStream();
         outputStream.write("Hello Client,I get the message.".getBytes("UTF-8"));
      
         inputStream.close();
         outputStream.close();
         socket.close();
         server.close();
       }
     
     public static void main1(String args[]) throws IOException {
         // 监听指定的端口
         int port = 8088;
         @SuppressWarnings("resource")
         ServerSocket server = new ServerSocket(port);
         // server将一直等待连接的到来
         System.out.println("server将一直等待连接的到来");
         
         while(true){
           Socket socket = server.accept();
           // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
           InputStream inputStream = socket.getInputStream();
           byte[] bytes = new byte[1024];
           int len;
           StringBuilder sb = new StringBuilder();
           while ((len = inputStream.read(bytes)) != -1) {
             // 注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
             sb.append(new String(bytes, 0, len, "UTF-8"));
           }
           System.out.println("get message from client: " + sb);
           inputStream.close();
           socket.close();
         }
       }

     public static void main(String args[]) throws Exception {
         // 监听指定的端口
         int port = 8088;
         @SuppressWarnings("resource")
         ServerSocket server = new ServerSocket(port);
         // server将一直等待连接的到来
         System.out.println("server将一直等待连接的到来");
      
         //如果使用多线程，那就需要线程池，防止并发过高时创建过多线程耗尽资源
         ExecutorService threadPool = Executors.newFixedThreadPool(100);
         
         while (true) {
           Socket socket = server.accept();
        // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
           InputStream inputStream = socket.getInputStream();
           Runnable runnable=()->{
             try {
               // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
               byte[] bytes = new byte[1024];
               int len;
               while ((len = inputStream.read(bytes)) != -1) {
                 StringBuilder sb = new StringBuilder();
                 // 注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                 sb.append(new String(bytes, 0, len, "GBK"));
                 System.out.println("get message from client: " + sb);
               }
               inputStream.close();
               socket.close();
             } catch (Exception e) {
               e.printStackTrace();
             }finally{
                 try {
                    inputStream.close();
                } catch (IOException e) {
                     e.printStackTrace();
                }
                 try {
                    socket.close();
                } catch (IOException e) {
                     e.printStackTrace();
                }
             }
           };
           threadPool.submit(runnable);
         }
       }

}
