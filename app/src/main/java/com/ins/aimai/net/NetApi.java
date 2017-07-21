package com.ins.aimai.net;

import android.util.Log;

import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.L;

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
    private static boolean debug;

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
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                L.e(message);
            }
        });
        httpLoggingInterceptor.setLevel(debug ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                .addInterceptor(new NetInterceptor())
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
        GlideUtil.setImgBaseUrl(baseUrl + "/images/");
        initApi();
    }

    public static void setDebug(boolean debug) {
        NetApi.debug = debug;
    }
}
