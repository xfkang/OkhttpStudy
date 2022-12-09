package com.itbird.okhttpstudy.okhttp;

/**
 * Created by itbird on 2022/11/21
 */
public abstract class NamedRunnable implements Runnable {
    @Override
    public void run() {
        execute();
    }

    abstract void execute();
}
