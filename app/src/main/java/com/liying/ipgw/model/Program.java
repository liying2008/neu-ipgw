package com.liying.ipgw.model;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/5/15 18:46
 * 版本：1.0
 * 描述：节目实体类
 * 备注：
 * =======================================================
 */
public class Program {
    private int id;
    private String name;
    private String p;

    public Program() {

    }
    public Program(int id, String name, String p) {
        this.id = id;
        this.name = name;
        this.p = p;
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

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

}
