package com.itbird.okhttpstudy.okhttp;

/**
 * Created by itbird on 2022/11/21
 */
public class OkhttpClient {
    private Builder builder;
    private Dispatcher dispatcher;

    public OkhttpClient(Builder builder) {
        this.builder = builder;
        dispatcher = builder.dispatcher;
    }

    public Dispatcher dispatcher() {
        return dispatcher;
    }

    public Call newCall(Request request) {
        return new RealCall(request, this);
    }

    public static class Builder {
        private Dispatcher dispatcher;

        public Builder() {
            dispatcher = new Dispatcher();
        }

        public OkhttpClient build() {
            return new OkhttpClient(this);
        }
    }
}
