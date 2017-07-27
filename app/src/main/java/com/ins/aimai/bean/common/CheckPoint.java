package com.ins.aimai.bean.common;

import com.google.gson.annotations.SerializedName;
import com.ins.common.entity.BaseSelectBean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/25.
 */

public class CheckPoint implements Serializable {

    private int index;

    private float checkValue;

    public CheckPoint(int index, float checkValue) {
        this.index = index;
        this.checkValue = checkValue;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public float getCheckValue() {
        return checkValue;
    }

    public void setCheckValue(float checkValue) {
        this.checkValue = checkValue;
    }
}
