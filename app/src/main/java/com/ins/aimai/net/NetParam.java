package com.ins.aimai.net;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by liaointan on 2017/7/17.
 * 网络请求参数封装，便于统一管理，拓展后期可能增加的统一参数，加密设置等处理过程
 */

public class NetParam {
    private Map<String, Object> paramMap = new LinkedHashMap<>();

    public NetParam put(String key, Object value) {
        paramMap.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        return paramMap;
    }
}
