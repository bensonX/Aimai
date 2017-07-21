package com.ins.aimai.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Trade implements Serializable {
    private int id;
    private String tradeName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }
}
