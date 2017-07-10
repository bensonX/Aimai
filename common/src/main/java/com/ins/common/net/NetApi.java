package com.ins.common.net;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * 网络请求的Retrofit2创建类，以及通过该类去调用接口
 */
public class NetApi {

    private final static String LOG_TAG = "NetApi";
    private static NetInterface ni;
    private static String baseUrl;

    private NetApi() {
        throw new UnsupportedOperationException();
    }

    public static NetInterface NI() {
        if (ni == null) {
            initApi();
        }
        return ni;
    }

    private static void initApi() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor())
//                .addNetworkInterceptor(new StethoInterceptor())
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        ni = retrofit.create(NetInterface.class);
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setBaseUrl(String baseUrl) {
        NetApi.baseUrl = baseUrl;
        Log.d(LOG_TAG, "Base url is setting:" + baseUrl);
        initApi();
    }
}
