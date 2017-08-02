package com.ins.aimai.bean;

import java.io.Serializable;

/**
 * Created by 学习模块考题页面统计内容 on 2017/8/2.
 */

public class StatisLearn implements Serializable {

    private int correctPercent;
    private int countNum;
    private int errorNum;
    private int nonPassNum;
    private int passNum;
    private int simulationCorrectPercent;
    private int simulationCountNum;

    public int getCorrectPercent() {
        return correctPercent;
    }

    public void setCorrectPercent(int correctPercent) {
        this.correctPercent = correctPercent;
    }

    public int getCountNum() {
        return countNum;
    }

    public void setCountNum(int countNum) {
        this.countNum = countNum;
    }

    public int getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(int errorNum) {
        this.errorNum = errorNum;
    }

    public int getNonPassNum() {
        return nonPassNum;
    }

    public void setNonPassNum(int nonPassNum) {
        this.nonPassNum = nonPassNum;
    }

    public int getPassNum() {
        return passNum;
    }

    public void setPassNum(int passNum) {
        this.passNum = passNum;
    }

    public int getSimulationCorrectPercent() {
        return simulationCorrectPercent;
    }

    public void setSimulationCorrectPercent(int simulationCorrectPercent) {
        this.simulationCorrectPercent = simulationCorrectPercent;
    }

    public int getSimulationCountNum() {
        return simulationCountNum;
    }

    public void setSimulationCountNum(int simulationCountNum) {
        this.simulationCountNum = simulationCountNum;
    }
}
