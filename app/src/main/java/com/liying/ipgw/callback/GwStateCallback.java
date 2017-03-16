package com.liying.ipgw.callback;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/20 9:10
 * 版本：1.0
 * 描述：网关状态回调
 * 备注：
 * =======================================================
 */
public interface GwStateCallback {
    /** 得到POST请求操作状态 */
    void postGwState(int state);
}
