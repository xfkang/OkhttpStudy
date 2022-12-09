package com.itbird.okhttpstudy.okhttp;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * Created by itbird on 2022/11/23
 */
public class RequsetBody {
    Builder builder;

    public RequsetBody(Builder builder) {
        this.builder = builder;
    }

    public static RequsetFile createFile(File file) {
        return new RequsetFile() {

            @Override
            public long getFileLenght() {
                return file.length();
            }

            @Override
            public String getFileame() {
                return file.getName();
            }

            @Override
            public String getFileType() {
                FileNameMap fileNameMap = URLConnection.getFileNameMap();
                return fileNameMap.getContentTypeFor(file.getAbsolutePath());
            }

            @Override
            public void write(OutputStream outputStream) throws IOException {
                InputStream inputStream = new FileInputStream(file);
                int len = 0;
                byte[] bytes = new byte[2048];
                while ((len = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes);
                }
                inputStream.close();
            }
        };
    }

    /**
     * 按照一定的规则
     *
     * @return
     */
    public String getContentType() {
        return type() + ";boundary = " + createBoundary();
    }

    private String createBoundary() {
        return UUID.randomUUID().toString();
    }

    private String type() {
        return builder.type;
    }

    /**
     * 规则
     *
     * @return
     */
    public long getContentLength() {
        long length = 0;
        Iterator<Map.Entry<String, Object>> iterator = builder.params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            if (entry.getValue() instanceof String) {
                length += getText(entry.getKey(), (String) entry.getValue()).getBytes().length;
            }

            if (entry.getValue() instanceof RequsetFile) {
                RequsetFile file = (RequsetFile) entry.getValue();
                length += getText(entry.getKey(), file).getBytes().length;
                length += "\r\n".getBytes().length;
            }
        }
        if (!builder.params.isEmpty()) {
            length += endBoundary.getBytes().length;
        }
        return length;
    }

    private String boundary = createBoundary();
    private String startBoundary = "--" + boundary;
    private String endBoundary = startBoundary + "--";

    private String getText(String key, String value) {
        return startBoundary + "\r\n" +
                "Content-Disposition: form-data; name = \"" + key + "\"\r\n" +
                "Context-Type: text/plain\r\n" +
                "\r\n" +
                value +
                "\r\n";
    }

    private String getText(String key, RequsetFile value) {
        return startBoundary + "\r\n" +
                "Content-Disposition: form-data; name = \"" + key + "\" filename = \"" + value.getFileame() + "\"" +
                "Context-Type: " + value.getFileType() + "\r\n" +
                "\r\n";
    }

    public void writeBodyData(OutputStream outputStream) {
        try {
            Iterator<Map.Entry<String, Object>> iterator = builder.params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                if (entry.getValue() instanceof String) {
                    String str = getText(entry.getKey(), (String) entry.getValue());
                    Log.d(Constants.TAG, str);
                    outputStream.write(str.getBytes());
                }

                if (entry.getValue() instanceof RequsetFile) {
                    RequsetFile file = (RequsetFile) entry.getValue();
                    String str = getText(entry.getKey(), file);
                    Log.d(Constants.TAG, str);
                    outputStream.write(str.getBytes());
                    file.write(outputStream);
                    outputStream.write("\r\n".getBytes());
                }

            }
            if (!builder.params.isEmpty()) {
                Log.d(Constants.TAG, endBoundary);
                outputStream.write(endBoundary.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Builder {
        public static final String FORM = "multipart/form-data";
        HashMap<String, Object> params;
        String type;

        public Builder() {
            params = new HashMap<>();
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder addParams(String s, Object s1) {
            params.put(s, s1);
            return this;
        }

        public RequsetBody build() {
            return new RequsetBody(this);
        }
    }
}
