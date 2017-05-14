package com.liying.ipgw.model;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/5/16 21:48
 * 版本：1.0
 * 描述：回看节目列表实体类
 * 备注：
 * =======================================================
 */
public class ReviewProgram {
    /** 回看节目名称 */
    private String name;
    /** 节目开始时间 */
    private String timeStart;
    /** 节目结束时间 */
    private String timeEnd;

    /** 空参构造器 */
    public ReviewProgram() {
    }

    /** 构造器 */
    public ReviewProgram(String name, String timeStart, String timeEnd) {
        this.name = name;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

}
