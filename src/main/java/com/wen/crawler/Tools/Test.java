package com.wen.crawler.Tools;

public class Test {
    public static void main(String[] args) {
        String str = "1232/212121/3223.html";
//        System.out.println(str);
        String arr[] = str.split("/");
//        Arrays.toString(arr);
        for(String s:arr){
            System.out.print(s+" ");
        }
    }
}
