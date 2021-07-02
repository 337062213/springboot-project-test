 package com.springboot.test.util.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
public class DownloadFileWithThreadPool {
 
    private static Logger logger = LoggerFactory.getLogger(DownloadFileWithThreadPool.class);
 
    public void getFileWithThreadPoolByHttp(String urlLocation, String filePath, int poolLength) throws IOException {
        //有顺序的线程
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
 
        long len = getContentLength(urlLocation);
        for (int i = 0; i < poolLength; i++) {
            long start = i * len / poolLength;
            long end = (i + 1) * len / poolLength - 1;
            if (i == poolLength - 1) {
                end = len;
            }
            DownloadWithRangeByHttpUrl download = new DownloadWithRangeByHttpUrl(urlLocation, filePath, start, end);
            threadPool.execute(download);
        }
        threadPool.shutdown();
    }
    public void getFileWithThreadPoolByLocal(String localAddress, String filePath, int poolLength) throws IOException {
        //有顺序的线程
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        File file = new File(localAddress);
        if(!file.exists() || !file.isFile()){
            logger.info("多线程下载本地文件：文件不存在");
            return;
        }
        long len = new File(localAddress).length();
        for (int i = 0; i < poolLength; i++) {
            long start = i * len / poolLength;
            DownloadWithRangeByLocalAddress download = new DownloadWithRangeByLocalAddress(localAddress, filePath, len,i,start);
            threadPool.execute(download);
        }
        threadPool.shutdown();
    }
    public static long getContentLength(String urlLocation) throws IOException {
        URL url = null;
        if (urlLocation != null) {
            url = new URL(urlLocation);
        }
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(5000);
        conn.setRequestMethod("GET");
        long len = conn.getContentLength();
        return len;
    }
    
    public static void main1(String[] args) {
        String urlLocation = "C:\\Users\\EDZ\\Downloads\\jdk-8u202-windows-x64.exe";
        String filePath ="C:\\Users\\EDZ\\Desktop\\test file\\aa.exe";
        long time = System.currentTimeMillis();
        DownloadFileWithThreadPool pool = new DownloadFileWithThreadPool();
        try {
            pool.getFileWithThreadPoolByLocal(urlLocation, filePath, 10);
            System.out.println("下载成功："+( System.currentTimeMillis() - time )+"毫秒");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器出错");
        }
     }
    
    public static void main(String[] args) {
        String urlLocation = "https://get.enterprisedb.com/postgresql/postgresql-13.3-2-windows-x64.exe";
        String filePath ="C:\\Users\\EDZ\\Desktop\\test file\\bb.exe";
        long time = System.currentTimeMillis();
        DownloadFileWithThreadPool pool = new DownloadFileWithThreadPool();
        try {
            pool.getFileWithThreadPoolByHttp(urlLocation, filePath, 10);
            System.out.println("下载成功："+( System.currentTimeMillis() - time )+"毫秒");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器出错");
        }
     }
}
