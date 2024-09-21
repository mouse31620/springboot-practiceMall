package com.example.springbootpracticemall.util;

public class Common {

    public static String get (Object object){
        if (object != null){
            return String.valueOf(object);
        } else {
            return "";
        }
    }
}
