package com.itbird.okhttpstudy.okhttp;

import java.io.IOException;

/**
 * Created by itbird on 2022/11/21
 */
public interface Callback {
    void onFailure(Call call, IOException e);

    void onResponse(Call call, Response response) throws IOException;
}
