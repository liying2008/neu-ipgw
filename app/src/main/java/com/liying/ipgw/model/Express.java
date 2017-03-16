package com.liying.ipgw.model;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/23 11:06
 * 版本：1.0
 * 描述：快递单实体
 * 备注：
 * =======================================================
 */
public class Express {
    /** 时间戳 */
    private long time;
    /** 快递单号 */
    private String expressCode;
    /** 快递公司公司 */
    private String shipperName;
    /** 快递公司编码 */
    private String shipperCode;
    /** 备注 */
    private String mark;

    public static final String TIME = "time";
    public static final String EXPRESS_CODE = "expressCode";
    public static final String SHIPPER_NAME = "shipperName";
    public static final String SHIPPER_CODE = "shipperCode";
    public static final String MARK = "mark";

    public Express() {
    }

    public Express(String expressCode, String shipperName, String shipperCode, String mark) {
        this.expressCode = expressCode;
        this.shipperName = shipperName;
        this.shipperCode = shipperCode;
        this.mark = mark;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getShipperCode() {
        return shipperCode;
    }

    public void setShipperCode(String shipperCode) {
        this.shipperCode = shipperCode;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
