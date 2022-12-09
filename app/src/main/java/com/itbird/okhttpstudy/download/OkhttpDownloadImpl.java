package com.itbird.okhttpstudy.download;

import android.util.Log;

import com.itbird.okhttpstudy.okhttp.CacheControl;
import com.itbird.okhttpstudy.okhttp.Call;
import com.itbird.okhttpstudy.okhttp.Callback;
import com.itbird.okhttpstudy.okhttp.Constants;
import com.itbird.okhttpstudy.okhttp.Method;
import com.itbird.okhttpstudy.okhttp.OkhttpClient;
import com.itbird.okhttpstudy.okhttp.Request;
import com.itbird.okhttpstudy.okhttp.Response;
import com.itbird.okhttpstudy.okhttp.Utils;

import java.io.IOException;

/**
 * Created by itbird on 2022/11/25
 */
public class OkhttpDownloadImpl implements DownloadInterface {
    @Override
    public void startDownload(DownloadTask downloadTask, DownloadCallback callback) {
        //第一步：new okhttpclient
        OkhttpClient okhttpClient = new OkhttpClient.Builder().build();

        //第二步：new Requset
        Request request = new Request.Builder()
                .url(downloadTask.getUrl())
                .method(Method.GET)
                .addParam("Range", "bytes=" + downloadTask.getCurrentLen() + "-")
                .cacheControl(CacheControl.FORCE_CACHE)
                .build();

        //第三步：new Call
        Call call = okhttpClient.newCall(request);
        downloadTask.setCall(call);

        //第四部：执行
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(Constants.TAG, " onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response == null) {
                    callback.downloadFail(DownloadError.RESPONSE_IS_NULL.getCode(), DownloadError.RESPONSE_IS_NULL.getMsg());
                    return;
                }
//                Log.d(Constants.TAG, " onResponse body = " + response.toString());
                String path = Utils.writeToFile(downloadTask, response.getInputStream());
                Log.d(Constants.TAG, " onResponse path = " + path);
                downloadTask.setDownloadStatus(DownloadStatus.COMPLETE);
                callback.downloadSuccess(path);
            }
        });
    }

    @Override
    public void stopDownload(DownloadTask downloadTask) {
        Log.d(Constants.TAG, "OkhttpDownloadImpl cancel  ");
        downloadTask.getCall().cancel();
        downloadTask.setDownloadStatus(DownloadStatus.STOP);
    }
}
