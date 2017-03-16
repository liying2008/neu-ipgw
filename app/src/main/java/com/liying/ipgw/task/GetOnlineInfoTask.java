package com.liying.ipgw.task;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.liying.ipgw.callback.OnlineInfoCallback;

import lxy.liying.ipgw.info.GetConnInfo;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/20 9:25
 * 版本：1.0
 * 描述：获取在线信息任务类
 * 备注：
 * =======================================================
 */
public class GetOnlineInfoTask extends AsyncTask<Void, Void, String[]> {
    private OnlineInfoCallback callback;

    public GetOnlineInfoTask(OnlineInfoCallback callback) {
        this.callback = callback;
    }
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case OnlineInfoCallback.FLOW_ERROR:
                    if (callback != null) {
                        callback.occurException(OnlineInfoCallback.FLOW_ERROR);
                    }
                    break;
                case OnlineInfoCallback.FLOW_NULL:
                    if (callback != null) {
                        callback.occurException(OnlineInfoCallback.FLOW_NULL);
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    });
    @Override
    protected String[] doInBackground(Void... params) {
        String[] rlt;
        try {
            rlt = GetConnInfo.getConnInfo();
        } catch (Exception e) {
            // 一般是java.io.IOException: Server returned HTTP response code: 500
            handler.sendEmptyMessage(OnlineInfoCallback.FLOW_ERROR);
            return null;
        }
        if (rlt == null) {
            handler.sendEmptyMessage(OnlineInfoCallback.FLOW_NULL);
            return null;
        } else if ("".equals(rlt[0]) && "".equals(rlt[1]) && "".equals(rlt[2]) && "".equals(rlt[3])) {
//                    System.out.println("进入FLOW=EXCEPTION");
            handler.sendEmptyMessage(OnlineInfoCallback.FLOW_ERROR);
            return null;
        } else {
            return rlt;
        }
    }

    @Override
    protected void onPostExecute(String[] info) {
        super.onPostExecute(info);
        if (info != null) {
            callback.onlineInfo(info);
        }
    }
}
