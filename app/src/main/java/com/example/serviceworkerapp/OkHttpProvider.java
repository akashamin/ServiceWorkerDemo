package com.example.serviceworkerapp;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Akash Amin on 31/05/20.
 */
public class OkHttpProvider {

    public static synchronized OkHttpClient get() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS);

        return builder.build();
    }
}
