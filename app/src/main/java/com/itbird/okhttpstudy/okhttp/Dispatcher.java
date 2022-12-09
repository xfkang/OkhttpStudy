package com.itbird.okhttpstudy.okhttp;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 执行器
 * Created by itbird on 2022/11/21
 */
public class Dispatcher {
    private ExecutorService executorService;

    public void enqueue(RealCall.AsyncCall call) {
        executorService().execute(call);
    }

    public synchronized ExecutorService executorService() {
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(), new ThreadFactory() {
                @Override
                public Thread newThread(Runnable runnable) {
                    Thread result = new Thread(runnable, "okhttp");
                    result.setDaemon(false);
                    return result;
                }
            });
        }
        return executorService;
    }

    public void cancel() {
        Log.d(Constants.TAG, "executorService cancel  ");
        executorService().shutdownNow();
    }
}
