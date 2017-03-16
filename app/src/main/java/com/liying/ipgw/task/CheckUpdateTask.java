package com.liying.ipgw.task;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.callback.OnCheckUpdateCallback;
import com.liying.ipgw.model.UpdateMsg;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONObject;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/4 22:37
 * 版本：1.0
 * 描述：检查更新任务类
 * 备注：
 * =======================================================
 */
public class CheckUpdateTask extends AsyncTask<Void, Void, Void> {
    private static final String PATH = "http://duduhuo.cc/ddh/check_update.php?appname=ipgwlt";
    private static final String ENCODING = "UTF-8";
    private static final int NO_UPDATE = 0x0000;
    private static final int UPDATE = 0x0001;
    private static final int NULL = 0x0002;
    private OnCheckUpdateCallback callback;
    private UpdateMsg updateMsg;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == NO_UPDATE) {
                if (callback != null) {
                    callback.onNoUpdate();
                }
            } else if (msg.what == UPDATE) {
                if (callback != null) {
                    callback.onUpdate(updateMsg);
                }
            } else if (msg.what == NULL) {
                if (callback != null) {
                    callback.onNull();
                }
            }
            return false;
        }
    });
    public CheckUpdateTask(OnCheckUpdateCallback callback) {
        this.callback = callback;
    }
    @Override
    protected Void doInBackground(Void... params) {
        String html = "";
        Request<String> request = NoHttp.createStringRequest(PATH, RequestMethod.GET);
        // 调用同步请求，直接拿到请求结果。
        Response<String> response = NoHttp.startRequestSync(request);
        if (response.isSucceed()) {
            html = response.get();
        }
        try {
            if ("null".equals(html) || "".equals(html)) {
                mHandler.sendEmptyMessage(NULL);
            } else {
                JSONObject json = new JSONObject(html);
                // 获取服务器上的版本号
                int serverVersion = json.getInt("version");
                // 获取本地版本号
                int localVersion = AccountApp.getInstance().getVersionCode();
                // 比较版本号
                if (serverVersion > localVersion) {
                    // 有新版本
                    updateMsg = new UpdateMsg();
                    int code = json.getInt("code");
                    String name = json.getString("name");
                    String versionName = json.getString("versionName");
                    String size = json.getString("size");
                    String updateDate = json.getString("updateDate");
                    String platform = json.getString("platform");
                    String updateLog = json.getString("updateLog");
                    String channel = json.getString("channel");
                    String downloadUrl = json.getString("downloadUrl");
                    updateMsg.setCode(code);
                    updateMsg.setName(name);
                    updateMsg.setVersion(serverVersion);
                    updateMsg.setVersionName(versionName);
                    updateMsg.setSize(size);
                    updateMsg.setUpdateDate(updateDate);
                    updateMsg.setPlatform(platform);
                    updateMsg.setUpdateLog(updateLog);
                    updateMsg.setChannel(channel);
                    updateMsg.setDownloadUrl(downloadUrl);
                    mHandler.sendEmptyMessage(UPDATE);
                } else {
                    // 本地已是最新版本
                    mHandler.sendEmptyMessage(NO_UPDATE);
                }
            }
        } catch (Exception e) {
            mHandler.sendEmptyMessage(NULL);
            e.printStackTrace();
        }
        return null;
    }
}
