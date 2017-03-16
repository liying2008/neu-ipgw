package com.liying.ipgw.callback;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/12/8 21:14
 * 版本：1.0
 * 描述：权限请求拒绝回调
 * 备注：
 * =======================================================
 */
public interface PermissionDenyCallback {
    /**
     * 权限请求被拒绝
     */
    void permissionDeny(String permission);
}
