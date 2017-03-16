package com.liying.ipgw.task;

import android.content.pm.PackageInfo;
import android.os.AsyncTask;

import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.utils.Constants;
import com.liying.ipgw.utils.EggUtils;
import com.liying.ipgw.utils.SignatureUtils;

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
    // IPGW的签名的MD5值
    private static final String MD5_SIGNATURE = "e2f32944095606a12389652db76b44e9";
    private PackageInfo packageInfo;
    private LoadDataCallback callback;

    public SplashLoadDataTask(PackageInfo packageInfo, LoadDataCallback callback) {
        this.packageInfo = packageInfo;
        this.callback = callback;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        int status = 0;
        // 检查数字签名，防止二次打包
        if (SignatureUtils.checkSignature(packageInfo, MD5_SIGNATURE)) {
            // 签名正确
            loadSettings();
            loadEggs();
            status = 0;
        } else {
            // 签名错误
            status = 1;
            // TODO: 2016/11/21
            // 开发模式下使用
//            loadSettings();
//            loadEggs();
        }
        return status;
    }

    /**
     * 加载彩蛋
     */
    private void loadEggs() {
        AccountApp.isEggsBoxShow = AccountApp.getInstance().eggPrefs.getBoolean(Constants.EGG_BOX, false);
        // 读取已获取的彩蛋
        AccountApp.eggNums = AccountApp.getInstance().eggPrefs.getStringSet(Constants.EGG_SET, AccountApp.eggNums);
        AccountApp.eggCount = AccountApp.eggNums.size();
        AccountApp.eggList.clear();
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
                // 签名错误
                callback.signatureError();
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
        void signatureError();
    }

}
