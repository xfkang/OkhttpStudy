package com.itbird.okhttpstudy.download;

/**
 * Created by itbird on 2022/11/25
 */
public interface DownloadCallback {
    void downloadSuccess(String path);

    void downloadFail(int errorCode, String msg);

    void downloadProcess(int current, int total);
}
