package com.itbird.okhttpstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.itbird.okhttpstudy.download.DownloadCallback;
import com.itbird.okhttpstudy.download.DownloadManager;
import com.itbird.okhttpstudy.okhttp.CacheControl;
import com.itbird.okhttpstudy.okhttp.Call;
import com.itbird.okhttpstudy.okhttp.Callback;
import com.itbird.okhttpstudy.okhttp.Constants;
import com.itbird.okhttpstudy.okhttp.Method;
import com.itbird.okhttpstudy.okhttp.OkhttpClient;
import com.itbird.okhttpstudy.okhttp.Request;
import com.itbird.okhttpstudy.okhttp.RequsetBody;
import com.itbird.okhttpstudy.okhttp.Response;
import com.itbird.okhttpstudy.okhttp.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements DownloadCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * okhttp实现单独的body、文件上传
         */
//        testOkhttpUploadAndRequst();
    }

    @Override
    public void downloadSuccess(String path) {
        Log.d(Constants.TAG, "downloadSuccess path = " + path);
        Utils.install(this, path);
    }

    @Override
    public void downloadFail(int errorCode, String msg) {
        Log.d(Constants.TAG, "downloadFail errorCode = " + errorCode + " msg =" + msg);
    }

    @Override
    public void downloadProcess(int current, int total) {
        Log.d(Constants.TAG, "downloadProcess current = " + current + " total =" + total);
    }

    /**
     * okhttp实现单独的body、文件上传
     */
    private void testOkhttpUploadAndRequst() {

        //第一步：new okhttpclient
        OkhttpClient okhttpClient = new OkhttpClient.Builder().build();

        File file = new File("");
        //插入，提交requsetbody，也就是通用首部字段
        RequsetBody requsetBody = new RequsetBody.Builder()
                .setType(RequsetBody.Builder.FORM)
                .addParams("pageSize", "1")
                .addParams("pageNum", "1")
//                .addParams("file1", RequsetBody.createFile(file))
                .build();

        //第二步：new Requset
        Request request = new Request.Builder()
                .url("http://192.168.230.7:8080/testt/RequstData")
                .method(Method.POST)
                .cacheControl(CacheControl.FORCE_CACHE)
                .requsetBody(requsetBody)
                .build();


        //第三步：new Call
        Call call = okhttpClient.newCall(request);

        //第四部：执行
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(Constants.TAG, "MainActivity onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(Constants.TAG, "MainActivity onResponse" + response.toString());
            }
        });
    }

    String url = "https://831panapp3.pk855.com/lhy9/dianludahsi.apk";
    int threadNum = 1;

    public void startDownload(View view) {
        /**
         * 实现通过okhttp实现download封装
         */
        DownloadManager.getInstance().startDownload(url, threadNum, this);
    }

    public void stopDownload(View view) {
        DownloadManager.getInstance().stopDownload(url);
    }
}