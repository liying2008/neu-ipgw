package com.liying.ipgw.model;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/9/28 0:24
 * 版本：1.0
 * 描述：更新信息实体类
 * 备注：
 * =======================================================
 */

public class UpdateMsg {
    /** 状态码 */
    private int code;
    /** 应用名称 */
    private String name;
    /** 应用版本号 */
    private int version;
    /** 版本名称 */
    private String versionName;
    /** 安装包大小 */
    private String size;
    /** 更新日期 */
    private String updateDate;
    /** 平台（Android、IOS） */
    private String platform;
    /** 更新日志 */
    private String updateLog;
    /** 安装包渠道 */
    private String channel;
    /** 安装包下载链接 */
    private String downloadUrl;

    public UpdateMsg() {
    }

    public UpdateMsg(int code, String name, int version, String versionName, String size, String updateDate, String platform, String updateLog, String channel, String downloadUrl) {
        this.code = code;
        this.name = name;
        this.version = version;
        this.versionName = versionName;
        this.size = size;
        this.updateDate = updateDate;
        this.platform = platform;
        this.updateLog = updateLog;
        this.channel = channel;
        this.downloadUrl = downloadUrl;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getUpdateLog() {
        return updateLog;
    }

    public void setUpdateLog(String updateLog) {
        this.updateLog = updateLog;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
