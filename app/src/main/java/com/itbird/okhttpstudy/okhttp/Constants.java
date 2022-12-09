package com.itbird.okhttpstudy.okhttp;

import android.os.Environment;

import java.io.File;

/**
 * Created by itbird on 2022/11/21
 */
public class Constants {
    public final  static String TAG = "itbird test";

    /**
     * APK后缀
     */
    public static final String APK_NAME_SUFFIX = ".apk";

    /**
     * 下载base路径
     */
    public static String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
}
