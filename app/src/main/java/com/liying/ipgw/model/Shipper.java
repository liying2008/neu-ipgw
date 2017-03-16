package com.liying.ipgw.model;

import java.io.Serializable;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/22 0:28
 * 版本：1.0
 * 描述：快递公司实体类
 * 备注：
 * =======================================================
 */
public class Shipper implements Serializable {
    /** 快递公司名称 */
    private String name;
    /** 快递公司编码 */
    private String code;

    public Shipper(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Shipper() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
