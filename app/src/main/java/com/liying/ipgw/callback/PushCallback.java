package com.liying.ipgw.callback;

import com.liying.ipgw.model.RecentPost;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/20 21:12
 * 版本：1.0
 * 描述：推送信息回调
 * 备注：
 * =======================================================
 */
public interface PushCallback {
    /** 新消息 */
    void newPush(RecentPost post);
}
