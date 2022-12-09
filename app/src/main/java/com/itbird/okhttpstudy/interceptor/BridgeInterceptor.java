package com.itbird.okhttpstudy.interceptor;

import android.util.Log;

import com.itbird.okhttpstudy.okhttp.Constants;
import com.itbird.okhttpstudy.okhttp.Request;
import com.itbird.okhttpstudy.okhttp.RequsetBody;
import com.itbird.okhttpstudy.okhttp.Response;

import java.io.IOException;
import java.util.Map;

/**
 * Created by itbird on 2022/11/24
 */
public class BridgeInterceptor implements Interceptor {
    public BridgeInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.d(Constants.TAG, "BridgeInterceptor");
        Request request = chain.request();
//        request.addParam("Connection", "keep-alive");
        // 做一些其他处理
        if (request.requsetBody() != null) {
            RequsetBody requestBody = request.requsetBody();
            request.addParam("Content-Type", requestBody.getContentType());
            request.addParam("Content-Length", Long.toString(requestBody.getContentLength()));
        }

        //GZIP数据流转换

        return chain.proceed(request);
    }
}
