package com.ins.aimai.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/4.
 */

public class StatisGov implements Serializable {

    private int companyNum;
    private int safeNum;
    private int allSafeNum;
    private int threeSafeNum;

    public int getCompanyNum() {
        return companyNum;
    }

    public void setCompanyNum(int companyNum) {
        this.companyNum = companyNum;
    }

    public int getSafeNum() {
        return safeNum;
    }

    public void setSafeNum(int safeNum) {
        this.safeNum = safeNum;
    }

    public int getAllSafeNum() {
        return allSafeNum;
    }

    public void setAllSafeNum(int allSafeNum) {
        this.allSafeNum = allSafeNum;
    }

    public int getThreeSafeNum() {
        return threeSafeNum;
    }

    public void setThreeSafeNum(int threeSafeNum) {
        this.threeSafeNum = threeSafeNum;
    }
}
