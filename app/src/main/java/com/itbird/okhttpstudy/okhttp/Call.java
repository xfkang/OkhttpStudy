package com.itbird.okhttpstudy.okhttp;

/**
 * Created by itbird on 2022/11/21
 */
public interface Call {
    void enqueue(Callback callback);

    Response execute();

    Request request();

    void cancel();
}
