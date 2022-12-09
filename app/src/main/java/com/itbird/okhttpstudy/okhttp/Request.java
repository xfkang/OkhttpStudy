package com.itbird.okhttpstudy.okhttp;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求
 * Created by itbird on 2022/11/21
 */
public class Request {
    private Builder builder;

    public Request(Builder builder) {
        this.builder = builder;
    }

    public String url() {
        return builder.url;
    }

    public String method() {
        return builder.method.name;
    }

    public RequsetBody requsetBody() {
        return builder.requsetBody;
    }

    public int cache() {
        return builder.cache;
    }

    public void addParam(String connection, String s) {
        builder.addParam(connection, s);
    }

    public Map<String, String> getParams() {
        return builder.params;
    }

    public static class Builder {
        private String url;
        private Map<String, String> params;
        private Method method;
        private RequsetBody requsetBody;
        private int cache;

        public Builder() {
            method = Method.GET;
            params = new HashMap<>(20);
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder addParam(String key, String value) {
            params.put(key, value);
            return this;
        }

        public Request build() {
            return new Request(this);
        }

        public Builder method(Method method) {
            this.method = method;
            return this;
        }

        public Builder requsetBody(RequsetBody requsetBody) {
            this.requsetBody = requsetBody;
            return this;
        }


        public Builder cacheControl(int cache) {
            this.cache = cache;
            return this;
        }
    }
}
