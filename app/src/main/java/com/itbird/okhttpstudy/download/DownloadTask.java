package com.itbird.okhttpstudy.download;

import com.itbird.okhttpstudy.okhttp.Call;
import com.itbird.okhttpstudy.okhttp.Constants;
import com.itbird.okhttpstudy.okhttp.Utils;

import java.io.File;

/**
 * Created by itbird on 2022/12/8
 */
public class DownloadTask {
    private String url;
    private DownloadStatus downloadStatus;
    private long currentLen;
    private String path;
    private Call call;

    public DownloadTask(String url, String absolutePath, long length) {
        this.url = url;
        this.path = absolutePath;
        this.currentLen = length;
    }

    public DownloadTask(String url, String absolutePath) {
        this.url = url;
        this.path = absolutePath;
    }

    public long getCurrentLen() {
        return currentLen;
    }

    public DownloadStatus getDownloadStatus() {
        return downloadStatus;
    }

    public String getFilePath() {
        return path;
    }

    public String getUrl() {
        return url;
    }

    public void setCall(Call call) {
        this.call = call;
    }

    public Call getCall() {
        return this.call;
    }

    public void setDownloadStatus(DownloadStatus downloadIng) {
        this.downloadStatus = downloadIng;
    }

    public void setCurrentLen(long length) {
        this.currentLen = length;
    }
}
