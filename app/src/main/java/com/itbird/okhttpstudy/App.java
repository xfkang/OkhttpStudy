package com.itbird.okhttpstudy;

import android.app.Application;

/**
 * Created by itbird on 2022/11/25
 */
public class App extends Application {
    private static App instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }
}
