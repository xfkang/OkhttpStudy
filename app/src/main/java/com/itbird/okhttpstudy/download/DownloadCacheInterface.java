package com.itbird.okhttpstudy.download;

/**
 * Created by itbird on 2022/11/25
 */
public interface DownloadCacheInterface<T> {
    void addCache(String url, T object);

    T getCache(String url);
}
