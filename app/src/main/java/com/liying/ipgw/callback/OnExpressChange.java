package com.liying.ipgw.callback;

import com.liying.ipgw.model.Express;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/23 14:07
 * 版本：1.0
 * 描述：更改快递单的回调接口
 * 备注：
 * =======================================================
 */
public interface OnExpressChange {
    /**
     * 更改快递单
     * @param express 快递单信息
     */
    void onNewExpress(Express express);
}
