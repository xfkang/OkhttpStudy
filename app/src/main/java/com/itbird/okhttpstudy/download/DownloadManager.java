package com.itbird.okhttpstudy.download;

import android.util.Log;

import com.itbird.okhttpstudy.okhttp.Constants;

/**
 * Created by itbird on 2022/11/25
 */
public class DownloadManager {
    private volatile static DownloadManager instance;
    private DownloadCacheInterface downloadCacheInterface;
    private DownloadInterface downloadInterface;

    public static DownloadManager getInstance() {
        if (instance == null) {
            synchronized (DownloadManager.class) {
                if (instance == null) {
                    instance = new DownloadManager();
                }
            }
        }
        return instance;
    }

    private DownloadManager() {
        downloadInterface = new OkhttpDownloadImpl();
        downloadCacheInterface = DownloadTaskManager.getInstance();
    }

    public void startDownload(String url, int threadNum, DownloadCallback downloadCallback) {
        DownloadTask downloadTask = (DownloadTask) downloadCacheInterface.getCache(url);
        downloadTask.setDownloadStatus(DownloadStatus.DOWNLOAD_ING);
        downloadInterface.startDownload(downloadTask, downloadCallback);
    }

    public void stopDownload(String url) {
        Log.d(Constants.TAG, "stopDownload cancel  ");
        DownloadTask downloadTask = (DownloadTask) downloadCacheInterface.getCache(url);
        downloadInterface.stopDownload(downloadTask);
    }
}
