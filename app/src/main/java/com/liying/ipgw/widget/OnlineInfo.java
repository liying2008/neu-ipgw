package com.liying.ipgw.widget;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/6 16:05
 * 版本：1.0
 * 描述：所有在线信息实体类
 * 备注：
 * =======================================================
 */
public class OnlineInfo {
    /** 连接状态 */
    private String state = "";
    /** 失败原因 */
    private String reason = "";
    /** 帐户余额 */
    private String balance = "";
    /** 已有流量 */
    private String usedFlow = "";
    /** 已用时长  */
    private String usedTime = "";
    /** 当前地址 */
    private String ip = "";

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getUsedFlow() {
        return usedFlow;
    }

    public void setUsedFlow(String usedFlow) {
        this.usedFlow = usedFlow;
    }

    public String getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void clear() {
        this.state = "";
        this.reason = "";
        this.balance = "";
        this.usedFlow = "";
        this.usedTime = "";
        this.ip = "";
    }
}
