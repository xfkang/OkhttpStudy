package com.itbird.okhttpstudy.okhttp;

/**
 * Created by itbird on 2022/11/21
 */
public enum Method {
    GET("GET"), POST("POST"), HEAD("HEAD"), DELETE("DELETE");

    String name;

    Method(String name) {
        this.name = name;
    }
}
