package com.liying.ipgw.task;

import android.os.AsyncTask;

import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.callback.PushCallback;
import com.liying.ipgw.model.RecentPost;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONObject;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/20 20:52
 * 版本：1.0
 * 描述：检查推送的任务类
 * 备注：
 * =======================================================
 */
public class ReceivePushTask extends AsyncTask<Void, Void, RecentPost> {
    private PushCallback callback;
    private static final String PATH = "http://duduhuo.cc/ddh/ipgw/push.json";

    public ReceivePushTask(PushCallback callback) {
        this.callback = callback;
    }

    @Override
    protected RecentPost doInBackground(Void... params) {
        String jsonStr = "";
        Request<String> request = NoHttp.createStringRequest(PATH, RequestMethod.GET);
        // 调用同步请求，直接拿到请求结果。
        Response<String> response = NoHttp.startRequestSync(request);
        if (response.isSucceed()) {
            jsonStr = response.get();
        }
        try {
            if (!"".equals(jsonStr)) {
                JSONObject json = new JSONObject(jsonStr);
                int id = json.getInt("id");
                // 每条信息只推送一遍
                if (id > AccountApp.pushID) {
                    String title = json.getString("title");
                    String date = json.getString("date");
                    String content = json.getString("content");
                    String url = json.getString("url");
                    return new RecentPost(title, date, content, url, id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(RecentPost post) {
        super.onPostExecute(post);
        if (callback != null && post != null) {
            callback.newPush(post);
        }
    }
}