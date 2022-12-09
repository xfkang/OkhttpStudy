package com.itbird.okhttpstudy.interceptor;

import android.util.Log;

import com.itbird.okhttpstudy.okhttp.Constants;
import com.itbird.okhttpstudy.okhttp.Request;
import com.itbird.okhttpstudy.okhttp.Response;
import com.itbird.okhttpstudy.okhttp.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by itbird on 2022/11/24
 */
public class CallServerInterceptor implements Interceptor {

    public CallServerInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.d(Constants.TAG, "CallServerInterceptor");

        Request request = chain.request();
        try {
            //获取连接请求
            URL url = new URL(request.url());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            //设置连接超时
            httpURLConnection.setConnectTimeout(3000);

            //设置方法
            httpURLConnection.setRequestMethod(request.method());
            if (request.requsetBody() != null) {
                httpURLConnection.setRequestProperty("Content-Type", request.requsetBody().getContentType());
                httpURLConnection.setRequestProperty("Content-Length", String.valueOf(request.requsetBody().getContentLength()));

                Log.d(Constants.TAG, httpURLConnection.getRequestProperty("Content-Length"));
                Log.d(Constants.TAG, httpURLConnection.getRequestProperty("Content-Type"));
            }

            Map<String, String> params = request.getParams();
            if (!params.isEmpty()) {
                Iterator<Map.Entry<String, String>> entryIterator = params.entrySet().iterator();
                while (entryIterator.hasNext()) {
                    Map.Entry<String, String> entry = entryIterator.next();
                    httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
                }
                Log.d(Constants.TAG, "RANGE = " + httpURLConnection.getRequestProperty("RANGE"));
            }
            //开始连接
            httpURLConnection.connect();
            //插入，如果requsetbody不为空，则继续写入内容
            if (request.requsetBody() != null) {
                request.requsetBody().writeBodyData(httpURLConnection.getOutputStream());
            }
            Log.d(Constants.TAG, "httpURLConnection.getResponseCode = " + httpURLConnection.getResponseCode());
            Log.d(Constants.TAG, "httpURLConnection.getResponseMessage = " + httpURLConnection.getResponseMessage());
            //判断返回的状态码
            if (httpURLConnection.getResponseCode() == 200 || httpURLConnection.getResponseCode() == 206) {
                //获取返回的数据
                InputStream inputStream = httpURLConnection.getInputStream();
                Log.d(Constants.TAG, "filename = " + httpURLConnection.getHeaderField("filename"));

                //将返回的数据，封装为response
                Response response = new Response(inputStream);
                return response;
            }
        } catch (MalformedURLException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
        return null;
    }
}
