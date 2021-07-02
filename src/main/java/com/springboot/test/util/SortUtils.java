 package com.springboot.test.util;

 public class SortUtils {
     public static int[] shellSort(int data[]) {
         int j = 0, temp = 0;
         for (int increment = data.length / 2; increment > 0; increment = increment / 2) {
             for (int i = increment; i < data.length; i++) {
                 temp = data[i];
                 for (j = i - increment; j >= 0; j = j - increment) {
                     if (temp < data[j]) {
                         data[j + increment] = data[j];
                     } else {
                         break;
                     }
                 }
                 data[j + increment] = temp;
             }
             for (int i = 0; i < data.length; i++) {
                 System.out.print(data[i] + " ");
             }
         }
         return data;
     }
}
