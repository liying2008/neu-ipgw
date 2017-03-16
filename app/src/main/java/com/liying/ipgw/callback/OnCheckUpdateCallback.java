package com.liying.ipgw.callback;

import com.liying.ipgw.model.UpdateMsg;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/9/28 0:23
 * 版本：1.0
 * 描述：检查更新回调接口
 * 备注：
 * =======================================================
 */
public interface OnCheckUpdateCallback {
    /**
     * 有更新
     * @param msg 更新信息
     */
    void onUpdate(UpdateMsg msg);

    /**
     * 无更新
     */
    void onNoUpdate();

    /**
     * 检查更新失败
     */
    void onNull();
}
