package com.example.serviceworkerapp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Akash Amin on 31/05/20.
 */
public class ExecutorHelper {
    private static int numCores = Runtime.getRuntime().availableProcessors();

    /**
     * Get multi threaded pool based on device configuration
     * @return
     */
    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return new ThreadPoolExecutor(numCores * 2, numCores * 2,
                60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }

    /**
     * Get single thread pool
     * @return
     */
    public static ExecutorService getSingleThreadPoolExecutor() {
        return Executors.newSingleThreadExecutor();
    }


}
