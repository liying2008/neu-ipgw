package com.liying.ipgw.task;

import android.os.AsyncTask;

import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.utils.Constants;
import com.liying.ipgw.utils.EggUtils;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/21 13:55
 * 版本：1.0
 * 描述：Splash界面加载应用数据任务类
 * 备注：
 * =======================================================
 */
public class SplashLoadDataTask extends AsyncTask<Void, Void, Integer> {
    private LoadDataCallback callback;

    public SplashLoadDataTask(LoadDataCallback callback) {
        this.callback = callback;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        try {
            loadSettings();
            loadEggs();
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    /**
     * 加载彩蛋
     */
    private void loadEggs() {
        AccountApp.isEggsBoxShow = AccountApp.getInstance().eggPrefs.getBoolean(Constants.EGG_BOX, false);
        // 读取已获取的彩蛋
        AccountApp.eggNums = new HashSet<>(10);
        AccountApp.eggNums.addAll(AccountApp.getInstance().eggPrefs.getStringSet(Constants.EGG_SET, AccountApp.eggNums));
        AccountApp.eggCount = AccountApp.eggNums.size();
        AccountApp.eggList = new ArrayList<>(10);
        for (String eggNum : AccountApp.eggNums) {
            AccountApp.eggList.add(EggUtils.getEggByNum(Integer.parseInt(eggNum)));
        }
    }

    /**
     * 设置默认帐号
     */
    private void loadSettings() {
        AccountApp.defaultUserName = AccountApp.getInstance().prefs.getString(Constants.DEFAULT_ACCOUNT, "null");
        AccountApp.pushID = AccountApp.getInstance().prefs.getInt(Constants.PUSH_ID, 0);
    }

    @Override
    protected void onPostExecute(Integer status) {
        super.onPostExecute(status);
        if (callback != null) {
            if (status == 0) {
                // 数据加载完毕
                callback.loaded();
            } else if (status == 1) {
                // 发生错误
                callback.error();
            }
        }
    }

    /**
     * 加载数据回调
     */
    public interface LoadDataCallback {
        /**
         * 数据加载完毕
         */
        void loaded();

        /**
         * 签名校验失败
         */
        void error();
    }

}
