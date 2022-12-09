package com.itbird.okhttpstudy.interceptor;

import com.itbird.okhttpstudy.okhttp.Call;
import com.itbird.okhttpstudy.okhttp.Request;
import com.itbird.okhttpstudy.okhttp.Response;

import java.io.IOException;

/**
 * Created by itbird on 2022/11/21
 */
public interface Interceptor {
    Response intercept(Chain chain) throws IOException;

    interface Chain {
        Request request();

        Call call();

        Response proceed(Request request) throws IOException;
    }
}
