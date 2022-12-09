package com.itbird.okhttpstudy.okhttp;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by itbird on 2022/11/23
 */
public interface RequsetFile {
    long getFileLenght();

    String getFileame();

    String getFileType();

    void write(OutputStream outputStream) throws IOException;
}
