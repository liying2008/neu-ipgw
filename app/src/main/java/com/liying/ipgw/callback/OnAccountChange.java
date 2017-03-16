package com.liying.ipgw.callback;

import com.liying.ipgw.model.AccountInfo;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/4 13:18
 * 版本：1.0
 * 描述：更改用户帐户的回调接口
 * 备注：
 * =======================================================
 */
public interface OnAccountChange {
    /**
     * 更改帐户
     * @param accountInfo 帐户信息
     */
    void onNewAccount(AccountInfo accountInfo);
}
