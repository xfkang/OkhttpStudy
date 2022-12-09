package com.itbird.okhttpstudy.okhttp;

import android.util.Log;

import com.itbird.okhttpstudy.interceptor.BridgeInterceptor;
import com.itbird.okhttpstudy.interceptor.CacheInterceptor;
import com.itbird.okhttpstudy.interceptor.CallServerInterceptor;
import com.itbird.okhttpstudy.interceptor.ConnectInterceptor;
import com.itbird.okhttpstudy.interceptor.Interceptor;
import com.itbird.okhttpstudy.interceptor.RetryAndFollowUpInterceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itbird on 2022/11/21
 */
public class RealCall implements Call {
    private Request request;
    private OkhttpClient okhttpClient;

    public RealCall(Request request, OkhttpClient okhttpClient) {
        this.request = request;
        this.okhttpClient = okhttpClient;
    }

    @Override
    public void enqueue(Callback callback) {
        okhttpClient.dispatcher().enqueue(new AsyncCall(callback));
    }

    @Override
    public Response execute() {
        return getResponseWithInterceptorChain();
    }

    @Override
    public Request request() {
        return request;
    }

    @Override
    public void cancel() {
        Log.d(Constants.TAG, "RealCall cancel  ");
        okhttpClient.dispatcher().cancel();
    }

    private Response getResponseWithInterceptorChain() {
        List<Interceptor> interceptors = new ArrayList<Interceptor>();
        interceptors.add(new RetryAndFollowUpInterceptor());// 重试
        interceptors.add(new BridgeInterceptor());// 基础
        interceptors.add(new CacheInterceptor());// 缓存
        interceptors.add(new ConnectInterceptor());// 建立连接
        interceptors.add(new CallServerInterceptor());// 写数据

        Interceptor.Chain chain = new RealInterceptorChain(this, interceptors, request);
        try {
            return chain.proceed(request);
        } catch (IOException e) {
            //处理过程被中断时，通过错误码返回
            e.printStackTrace();
            return null;
        }
    }

    class AsyncCall extends NamedRunnable {
        private Callback callback;

        public AsyncCall(Callback callback) {
            this.callback = callback;
        }

        @Override
        public void execute() {
            Log.d(Constants.TAG, "AsyncCall execute");
            //这里有问题的
            Response response = getResponseWithInterceptorChain();
            if (callback != null) {
                try {
                    callback.onResponse(RealCall.this, response);
                } catch (IOException e) {
                }
            }
        }
    }
}
