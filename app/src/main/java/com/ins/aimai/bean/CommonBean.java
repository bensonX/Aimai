package com.ins.aimai.bean;

import com.ins.common.entity.BaseSelectBean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/25.
 * 通用实体对象，提供通用的字段，部分接口可能用到
 */

public class CommonBean extends BaseSelectBean implements Serializable {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
