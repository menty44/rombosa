package com.example.easynotes.model;

public class RandomUtil {

    public static void main (String []args){
//        String finalst = "+254720106420".substring(4);
//        System.out.print(finalst+"\n");
//
//        System.out.print("0"+finalst);

        String val = ""+((int)(Math.random()*9000)+1000);
        System.out.printf(val);
    }
}
