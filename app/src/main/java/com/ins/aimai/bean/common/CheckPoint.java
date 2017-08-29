package com.ins.aimai.bean.common;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.ins.common.entity.BaseSelectBean;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Administrator on 2017/4/25.
 */

public class CheckPoint implements Serializable, Comparable<CheckPoint> {

    private int index;

    private float checkValue;

    public CheckPoint(float checkValue) {
        this.checkValue = checkValue;
    }

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

    @Override
    public int compareTo(@NonNull CheckPoint o) {
        if (checkValue > o.getCheckValue()) {
            return 1;
        } else if (checkValue < o.getCheckValue()) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "CheckPoint{" +
                "index=" + index +
                ", checkValue=" + checkValue +
                '}';
    }
}
