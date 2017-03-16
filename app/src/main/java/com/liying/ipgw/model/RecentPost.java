package com.liying.ipgw.model;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/20 14:22
 * 版本：1.0
 * 描述：网络中心黑板报/FAQ/消息推送 实体
 * 备注：
 * =======================================================
 */
public class RecentPost {
    /** 黑板报标题 */
    private String title;
    /** 发布日期 */
    private String date;
    /** 黑板报内容 */
    private String content;
    /** 详情网址 */
    private String url;
    /** PUSH的序号 */
    private int id;

    public RecentPost(String title, String date, String content, String url) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.url = url;
    }

    public RecentPost(String title, String date, String content, String url, int id) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.url = url;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
