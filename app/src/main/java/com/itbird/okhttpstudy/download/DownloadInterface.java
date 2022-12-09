package com.itbird.okhttpstudy.download;

/**
 * Created by itbird on 2022/11/25
 */
public interface DownloadInterface {
    void startDownload(DownloadTask downloadTask, DownloadCallback callback);

    void stopDownload(DownloadTask downloadTask);
}
