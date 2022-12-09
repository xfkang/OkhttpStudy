package com.itbird.okhttpstudy.okhttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 响应
 * Created by itbird on 2022/11/21
 */
public class Response {
    private InputStream inputStream;

    public Response(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public String toString() {
        return "Response{" +
                "databody='" + getBodyString(inputStream) + '\'' +
                '}';
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    private String getBodyString(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String str = sb.toString();
        return str;
    }
}
