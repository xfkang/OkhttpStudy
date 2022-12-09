package com.itbird.okhttpstudy.interceptor;

import android.util.Log;

import com.itbird.okhttpstudy.okhttp.CacheControl;
import com.itbird.okhttpstudy.okhttp.Constants;
import com.itbird.okhttpstudy.okhttp.Request;
import com.itbird.okhttpstudy.okhttp.Response;

import java.io.IOException;

/**
 * Created by itbird on 2022/11/24
 */
public class CacheInterceptor implements Interceptor {
    public CacheInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.d(Constants.TAG, "CacheInterceptor");

        Request request = chain.request();
        if (request.cache() == CacheControl.FORCE_CACHE) {
            //本地缓存有没有，缓存过期了没有，缓存对比服务器返回307
        }
        return chain.proceed(request);
    }
}
