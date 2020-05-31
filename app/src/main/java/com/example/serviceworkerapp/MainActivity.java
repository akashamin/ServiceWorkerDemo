package com.example.serviceworkerapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serviceworkerapp.databinding.ActivityMainBinding;

import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String IMAGE_1 = "https://homepages.cae.wisc.edu/~ece533/images/airplane.png";
    public static final String IMAGE_2 = "https://homepages.cae.wisc.edu/~ece533/images/arctichare.png";

    private ActivityMainBinding binding;

    ServiceWorker serviceWorker1 = ServiceWorker.getInstance("service_worker_1");
    ServiceWorker serviceWorker2 = ServiceWorker.getInstance("service_worker_2");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchImage1AndSet();
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchImage2AndSet();
            }
        });
    }

    private void fetchImage1AndSet() {
        serviceWorker1.addTask(new Task<Bitmap>() {
            @Override
            public Bitmap onExecuteTask() {
                try {
                    //Fetching image1 through okhttp
                    Request request = new Request.Builder().url(IMAGE_1).build();
                    Response response = OkHttpProvider.get().newCall(request).execute();
                    final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    return bitmap;
                } catch (Exception e) {
                    Log.e(TAG, "onExecuteTask: ", e);
                }
                return null;
            }

            @Override
            public void onTaskComplete(Bitmap result) {
                //Set bitmap to imageview
                binding.imageView1.setImageBitmap(result);
            }
        });
    }

    private void fetchImage2AndSet() {
        serviceWorker2.addTask(new Task<Bitmap>() {
            @Override
            public Bitmap onExecuteTask() {
                try {
                    //Fetching image1 through okhttp
                    Request request = new Request.Builder().url(IMAGE_2).build();
                    Response response = OkHttpProvider.get().newCall(request).execute();
                    final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    return bitmap;
                } catch (Exception e) {
                    Log.e(TAG, "onExecuteTask: ", e);
                }
                return null;
            }

            @Override
            public void onTaskComplete(Bitmap result) {
                //Set bitmap to image 1
                binding.imageView2.setImageBitmap(result);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        serviceWorker1.shutDown();
        serviceWorker2.shutDown();

    }
}
