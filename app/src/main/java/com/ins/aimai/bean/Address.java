package com.ins.aimai.bean;

import com.ins.common.entity.BaseSelectBean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/21.
 */

public class Address extends BaseSelectBean implements Serializable {

    private int id;
    private String name;
    private String mergerName;
    private String address;
    private int levelType;

    public Address() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMergerName() {
        return mergerName;
    }

    public void setMergerName(String mergerName) {
        this.mergerName = mergerName;
    }

    public Address(String name) {
        this.name = name;
    }

    public int getLevelType() {
        return levelType;
    }

    public void setLevelType(int levelType) {
        this.levelType = levelType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
