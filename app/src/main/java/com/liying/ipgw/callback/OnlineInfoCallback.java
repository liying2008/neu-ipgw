package com.liying.ipgw.callback;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/20 9:32
 * 版本：1.0
 * 描述：获取在线信息回调
 * 备注：
 * =======================================================
 */
public interface OnlineInfoCallback {
    int FLOW_NULL = 0x000d;
    int FLOW_ERROR = 0x000e;
    /**
     * 获取到在线信息
     * @param info
     */
    void onlineInfo(String[] info);

    /**
     * 发生异常
     * @param type 异常类型
     */
    void occurException(int type);
}
