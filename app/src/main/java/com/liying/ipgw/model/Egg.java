package com.liying.ipgw.model;

import android.support.annotation.NonNull;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/7 0:00
 * 版本：1.0
 * 描述：彩蛋实体类
 * 备注：
 * =======================================================
 */
public class Egg {
    private int eggNumber;
    private int eggDrawable;
    private String eggMsg;

    public Egg() {
    }

    public Egg(int eggNumber, int eggDrawable, String eggMsg) {
        this.eggNumber = eggNumber;
        this.eggDrawable = eggDrawable;
        this.eggMsg = eggMsg;
    }

    @NonNull
    public int getEggDrawable() {
        return eggDrawable;
    }

    public int getEggNumber() {
        return eggNumber;
    }

    public void setEggNumber(int eggNumber) {
        this.eggNumber = eggNumber;
    }

    public void setEggDrawable(int eggDrawable) {
        this.eggDrawable = eggDrawable;
    }

    @NonNull
    public String getEggMsg() {
        return eggMsg;
    }

    public void setEggMsg(String eggMsg) {
        this.eggMsg = eggMsg;
    }
}
