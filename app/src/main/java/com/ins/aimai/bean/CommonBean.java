package com.ins.aimai.bean;

import com.google.gson.annotations.SerializedName;
import com.ins.common.entity.BaseSelectBean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/25.
 * 通用实体对象，提供通用的字段，部分接口可能用到
 */

public class CommonBean extends BaseSelectBean implements Serializable {

    private int id;

    @SerializedName("valiCode")
    private String valiCode;

    @SerializedName("url")
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValiCode() {
        return valiCode;
    }

    public void setValiCode(String valiCode) {
        this.valiCode = valiCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
