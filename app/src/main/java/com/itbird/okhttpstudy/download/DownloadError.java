package com.itbird.okhttpstudy.download;

/**
 * Created by itbird on 2022/11/25
 */
public enum DownloadError {
    NOT_NETWORK(-2, "无网络"),
    RESPONSE_IS_NULL(-3, "返回数据为空");

    private int code;
    private String string;

    DownloadError(int code, String string) {
        this.code = code;
        this.string = string;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return string;
    }
}
