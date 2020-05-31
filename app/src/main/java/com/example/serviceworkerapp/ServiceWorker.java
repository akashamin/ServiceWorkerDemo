package com.example.serviceworkerapp;

import android.os.Handler;
import android.os.Looper;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;

/**
 * Created by Akash Amin on 31/05/20.
 */
public class ServiceWorker {
    private static HashMap<String, ServiceWorker> map;
    private ExecutorService executorService;

    /**
     * Create single instance based on worker name;
     * @param workerName
     * @return ServiceWorker instance
     */
    public static ServiceWorker getInstance(String workerName) {
        if (map == null) {
            map = new HashMap<>();
        }

        if (!map.containsKey(workerName)) {
            map.put(workerName, new ServiceWorker());
        }
        return map.get(workerName);
    }

    /**
     * initialize the singleThreadExecutor to execute single task at a time.
     */
    private ServiceWorker() {
        executorService = ExecutorHelper.getSingleThreadPoolExecutor();
    }

    public void addTask(final Task task) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                final Object object = task.onExecuteTask();

                //post the result in main thread
                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            @Override
                            public void run() {
                                task.onTaskComplete(object);
                            }
                        });
            }
        });
    }

    public void shutDown() {
        executorService.shutdown();
    }
}
