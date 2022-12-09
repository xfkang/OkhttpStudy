package com.itbird.okhttpstudy.okhttp;

import com.itbird.okhttpstudy.interceptor.Interceptor;

import java.io.IOException;
import java.util.List;

/**
 * Created by itbird on 2022/11/24
 */
public class RealInterceptorChain implements Interceptor.Chain {
    private List<Interceptor> interceptors;
    private Request request;
    private RealCall realCall;
    private int index = 0;

    public RealInterceptorChain(RealCall realCall, List<Interceptor> interceptors, Request request) {
        this.interceptors = interceptors;
        this.request = request;
        this.realCall = realCall;
    }

    @Override
    public Request request() {
        return request;
    }

    @Override
    public Call call() {
        return realCall;
    }

    @Override
    public Response proceed(Request request) throws IOException {
        if (index >= interceptors.size()) {
            throw new AssertionError();
        }
        return interceptors.get(index++).intercept(this);
    }
}
