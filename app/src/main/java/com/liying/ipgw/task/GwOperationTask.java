package com.liying.ipgw.task;

import android.os.AsyncTask;

import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.callback.GwStateCallback;
import com.liying.ipgw.utils.Constants;

import lxy.liying.ipgw.post.IPGWOperation;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/20 0:51
 * 版本：1.0
 * 描述：网关操作任务类
 * 备注：
 * =======================================================
 */
public class GwOperationTask extends AsyncTask<String, Void, Integer> {
    private int operation;
    private GwStateCallback callback;
    public GwOperationTask(int operation, GwStateCallback callback) {
        this.operation = operation;
        this.callback = callback;
    }
    @Override
    protected Integer doInBackground(String... params) {
        int state = 0;
        String userName = params[0];
        String password = params[1];
        if (operation == IPGWOperation.CONNECT) {
            state = IPGWOperation.getConnectState(userName, password);
        } else if (operation == IPGWOperation.CONNECT_PC) {
            state = IPGWOperation.getConnectPCState(userName, password);
        } else if (operation == IPGWOperation.DISCONNECT) {
            String ip = AccountApp.getInstance().prefs.getString(Constants.IP_ADDRESS, "0");
            state = IPGWOperation.getDisconnectState(ip);
        } else if (operation == IPGWOperation.DISCONNECT_ALL) {
            state = IPGWOperation.getDisconnectAllState(userName, password);
        }
        return state;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (callback != null) {
            callback.postGwState(integer);
        }
    }
}
