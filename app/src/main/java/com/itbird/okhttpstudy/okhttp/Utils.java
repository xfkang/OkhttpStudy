package com.itbird.okhttpstudy.okhttp;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Output;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.itbird.okhttpstudy.download.DownloadTask;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by itbird on 2022/11/25
 */
public class Utils {
    /**
     * 写入文件
     */
    public static String writeToFile(DownloadTask downloadTask, InputStream inputStream) throws IOException {
        Log.d(Constants.TAG, " writeToFile 1");
        Log.d(Constants.TAG, " writeToFile 2");
        // 打开一个随机访问文件流，按读写方式
        RandomAccessFile randomFile = new RandomAccessFile(downloadTask.getFilePath(), "rwd");
//        // 文件长度，字节数
        Log.d(Constants.TAG, " writeToFile 2 getCurrentLen =" + downloadTask.getCurrentLen());
//        // 将写文件指针移到文件尾。
        randomFile.seek(downloadTask.getCurrentLen());
        byte[] bytes = new byte[2048];
        int len = -1;
        Log.d(Constants.TAG, " writeToFile 3");
        while ((len = inputStream.read(bytes)) != -1) {
//            Log.d(Constants.TAG, Arrays.toString(bytes));
            randomFile.write(bytes, 0, len);
        }
        randomFile.close();
        Log.d(Constants.TAG, " writeToFile 5");
        return downloadTask.getFilePath();
    }

    /**
     * 根据str，放回md5
     *
     * @param str
     * @return
     */
    public static String stringToMd5(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }

            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 安装方法
     *
     * @param context
     * @param path
     */
    public static void install(Context context, String path) {
        File apk = new File(path);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //注意第二个参数，要保持和manifest中android:authorities的值相同
            Uri uri = FileProvider.getUriForFile(context,
                    context.getPackageName() + ".fileProvider", apk);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apk), "application/vnd.android.package-archive");
        }
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
