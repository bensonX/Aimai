package com.ins.aimai.net;

import android.text.TextUtils;

import com.ins.aimai.common.AppData;
import com.ins.common.utils.L;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/4/26.
 */

public class NetInterceptor implements Interceptor {

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder();

        String token = AppData.App.getToken();
        if (!TextUtils.isEmpty(token)) {
            requestBuilder.addHeader("token", token);
        }
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
