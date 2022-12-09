package com.itbird.okhttpstudy.download;

import com.itbird.okhttpstudy.okhttp.Constants;
import com.itbird.okhttpstudy.okhttp.Utils;

import java.io.File;
import java.util.HashMap;

/**
 * Created by itbird on 2022/12/8
 */
public class DownloadTaskManager implements DownloadCacheInterface<DownloadTask> {
    public static volatile DownloadTaskManager mInstance;
    private HashMap<String, DownloadTask> map = new HashMap<>();

    public static DownloadTaskManager getInstance() {
        if (mInstance == null) {
            synchronized (DownloadTaskManager.class) {
                if (mInstance == null) {
                    mInstance = new DownloadTaskManager();
                }
            }
        }
        return mInstance;
    }

    private DownloadTaskManager() {
    }

    @Override
    public void addCache(String url, DownloadTask downloadTask) {
        map.put(url, downloadTask);
    }

    @Override
    public DownloadTask getCache(String url) {
        DownloadTask downloadTask = null;
        if (map.containsKey(url)) {
            downloadTask = map.get(url);
            downloadTask.setCurrentLen(new File(downloadTask.getFilePath()).length());
            return map.get(url);
        } else {
            File file = new File(Constants.BASE_PATH + Utils.stringToMd5(url) + Constants.APK_NAME_SUFFIX);
            if (file.exists()) {
                downloadTask = new DownloadTask(url, file.getAbsolutePath(), file.length());
            } else {
                downloadTask = new DownloadTask(url, file.getAbsolutePath());
            }
            addCache(url, downloadTask);
            return downloadTask;
        }
    }
}
