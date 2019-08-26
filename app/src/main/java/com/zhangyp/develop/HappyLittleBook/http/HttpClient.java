package com.zhangyp.develop.HappyLittleBook.http;

import android.util.Log;

import com.zhangyp.develop.HappyLittleBook.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class HttpClient {
    //    http://123.57.151.98:9980/api/User/ImageValidateCode/15590858773.jpg
//    final static String HOST = "http://123.57.151.98:9980/api/";
    //    http://123.57.151.98:9980/api/Task/GetTaskNoticeSignConfig
//    final static String HOST = "http://123.56.251.83:96/api/values/";

    /**
     * 构建OkHttp
     */
    private static final class OKHttpHolder {

        private static final int TIME_OUT = 60;

        private static final OkHttpClient OK_HTTP_CLIENT = createOkHttpClientBuilder().build();

        private static OkHttpClient.Builder createOkHttpClientBuilder() {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //设置连接超时
            builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
            //设置从主机读信息超时
            builder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
            //设置写信息超时
            builder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
            if (BuildConfig.LOG) {
                builder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.e("======>", message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY));
            }
            return builder;
        }

    }

    // 构建全局Retrofit客户端
    private static final class RetrofitHolder {

        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl("http://apis.juhe.cn/")
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * HttpService
     */
    private static final class HttpServiceHolder {
        private static final HttpService REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(HttpService.class);
    }

    static HttpService getHttpService() {
        return HttpServiceHolder.REST_SERVICE;
    }
}

