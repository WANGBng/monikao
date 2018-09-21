package com.bwie.mvp.ui.utils;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * OKHttpUtils是网络工具类
 * Created by wangbingjun on 2018/9/21.
 */

public class OKHttpUtils {
     public interface NetCallback {
             void success(String result);

             void error(String errorMsg);
         }

         private Handler mHandler;

         private OkHttpClient mOkHttpClient;

         {
             /* 日志拦截器 */
             HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
             interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

             /* OKHttpClient */
             mOkHttpClient = new OkHttpClient
                     .Builder()
                     .addInterceptor(interceptor)
                     .build();

             mHandler = new Handler(Looper.getMainLooper());
         }

         private static final OKHttpUtils ourInstance = new OKHttpUtils();

         public static OKHttpUtils getInstance() {
             return ourInstance;
         }

         private OKHttpUtils() {
         }

         public void get(String url, final NetCallback netCallback) {
             Request request = new Request
                     .Builder()
                     .url(url)
                     .build();
             mOkHttpClient.newCall(request).enqueue(new Callback() {
                 @Override
                 public void onFailure(Call call, final IOException e) {

                     mHandler.post(new Runnable() {
                         @Override
                         public void run() {
                             if (netCallback != null){
                                 netCallback.error(e.getMessage());
                             }
                         }
                     });
                 }
                 @Override
                 public void onResponse(Call call, Response response){

                     if (netCallback != null){
                         try {
                             netCallback.success(response.body().string());

                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                     }
                 }
             });
         }
         public void post() {

         }
}