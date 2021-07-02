 package com.springboot.test.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
     
     public static String  getCurrentTime1() {
         Date date = new Date();
         SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
         return dateFormat.format(date);
     }
     
     public static String  getCurrentTime2() {
         Calendar calendar= Calendar.getInstance();
         SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
         System.out.println(dateFormat.format(calendar.getTime()));
         return dateFormat.format(calendar.getTime());
     }
     
     public static String  getCurrentTime3() {
         Calendar cal=Calendar.getInstance();      
         int y=cal.get(Calendar.YEAR);      
         int m=cal.get(Calendar.MONTH);      
         int d=cal.get(Calendar.DATE);      
         int h=cal.get(Calendar.HOUR_OF_DAY);      
         int mi=cal.get(Calendar.MINUTE);      
         int s=cal.get(Calendar.SECOND);      
         System.out.println("现在时刻是"+y+"年"+m+"月"+d+"日"+h+"时"+mi+"分"+s+"秒");
         return "现在时刻是"+y+"年"+m+"月"+d+"日"+h+"时"+mi+"分"+s+"秒";
     }
     public static String getDatePoor(Date start, Date end) {
         
         long nd = 1000 * 24 * 60 * 60;
         long nh = 1000 * 60 * 60;
         long nm = 1000 * 60;
         // long ns = 1000;
         // 获得两个时间的毫秒时间差异
         long diff = end.getTime() - start.getTime();
         // 计算差多少天
         long day = diff / nd;
         // 计算差多少小时
         long hour = diff % nd / nh;
         // 计算差多少分钟
         long min = diff % nd % nh / nm;
         // 计算差多少秒//输出结果
         // long sec = diff % nd % nh % nm / ns;
         return day + "天" + hour + "小时" + min + "分钟";
     }
     public static String getDatePoorMode2(Date start, Date end) {
         long nd = 1000 * 24 * 60 * 60;
         long nh = 1000 * 60 * 60;
         long nm = 1000 * 60;
         long ns = 1000;
         // long ns = 1000;
         // 获得两个时间的毫秒时间差异
         long diff = end.getTime() - start.getTime();
         // 计算差多少天
         long day = diff / nd;
         // 计算差多少小时
         long hour = diff % nd / nh;
         // 计算差多少分钟
         long min = diff % nd % nh / nm;
         // 计算差多少秒
         long second = diff % nd % nh % nm /ns;
         // 计算差多少毫秒
         long milsecond = diff % nd % nh % nm % ns;
         // 计算差多少秒//输出结果
         // long sec = diff % nd % nh % nm / ns;
         return day + "天" + hour + "小时" + min + "分钟" + second + "秒" + milsecond + "毫秒";
     }
}
