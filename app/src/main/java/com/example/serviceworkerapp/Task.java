package com.example.serviceworkerapp;

/**
 * Created by Akash Amin on 31/05/20.
 */
public interface Task<T> {
    T onExecuteTask();
    void onTaskComplete(T result);
}
