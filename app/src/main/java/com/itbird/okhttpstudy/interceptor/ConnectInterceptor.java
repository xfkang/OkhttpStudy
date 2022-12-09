package com.itbird.okhttpstudy.interceptor;

import android.util.Log;

import com.itbird.okhttpstudy.okhttp.Constants;
import com.itbird.okhttpstudy.okhttp.Request;
import com.itbird.okhttpstudy.okhttp.Response;

import java.io.IOException;

/**
 * Created by itbird on 2022/11/24
 */
public class ConnectInterceptor implements Interceptor {
    public ConnectInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.d(Constants.TAG, "ConnectInterceptor");
        Request request = chain.request();
        //表现为okhttp的话，这里就是socket简历连接，并且将socket输入输出流与okio绑定在一起
        return chain.proceed(request);
    }
}
