package com.liying.ipgw.model;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/22 16:58
 * 版本：1.0
 * 描述：物流信息类
 * 备注：
 * =======================================================
 */
public class Trace {
    /** 时间 */
    private String acceptTime;
    /** 描述 */
    private String acceptStation;
    /** 备注 */
    private String remark;

    public Trace() {
    }

    public Trace(String acceptTime, String acceptStation, String remark) {
        this.acceptTime = acceptTime;
        this.acceptStation = acceptStation;
        this.remark = remark;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getAcceptStation() {
        return acceptStation;
    }

    public void setAcceptStation(String acceptStation) {
        this.acceptStation = acceptStation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
