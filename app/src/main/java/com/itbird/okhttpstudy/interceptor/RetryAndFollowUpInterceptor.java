package com.itbird.okhttpstudy.interceptor;

import android.util.Log;

import com.itbird.okhttpstudy.okhttp.Constants;
import com.itbird.okhttpstudy.okhttp.Request;
import com.itbird.okhttpstudy.okhttp.Response;

import java.io.IOException;

/**
 * Created by itbird on 2022/11/24
 */
public class RetryAndFollowUpInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.d(Constants.TAG, "RetryAndFollowUpInterceptor");
        Request request = chain.request();
        //okhttp表现为，此处，去根据底层抛出的异常，决定是否为关键错误异常，如果不是，则while true循环，去执行重试请求
        return chain.proceed(request);
    }
}
