package com.example.easynotes.model;

public class RandomUtil {

    public static void main (String []args){

        String Str = new String("changepin1234");

        System.out.print("Return Value :" );
        System.out.println(Str.startsWith("changepin") );

        System.out.print("Return Value :" );
        System.out.println(Str.startsWith("changepin") );

        String finalst = "changepin1234".substring(9);
        System.out.print(finalst+"\n");
//
//        System.out.print("0"+finalst);

//        String val = ""+((int)(Math.random()*9000)+1000);
//        System.out.printf(val);
    }
}
