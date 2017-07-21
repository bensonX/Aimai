package com.ins.aimai.net;

import android.text.TextUtils;

import com.ins.aimai.common.AppData;
import com.ins.common.utils.L;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by liaointan on 2017/7/17.
 * 网络请求参数封装，便于统一管理，拓展后期可能增加的统一参数，加密设置等处理过程
 */

public class NetParam {
    private Map<String, Object> paramMap = new LinkedHashMap<>();

    public NetParam() {
        paramMap.put("isWechat", 0);
    }

    public NetParam put(String key, Object value) {
        paramMap.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        //pritParam();
        return paramMap;
    }

    public MultipartBody.Part buildFileBodyPart(String path) {
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("files", file.getName(), requestFile);
        return body;
    }

    private void pritParam() {
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            L.e(entry.getKey() + ":" + entry.getValue().toString());
        }
    }
}
