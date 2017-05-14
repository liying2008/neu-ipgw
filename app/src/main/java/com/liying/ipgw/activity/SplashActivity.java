package com.liying.ipgw.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.liying.ipgw.R;
import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.task.SplashLoadDataTask;
import com.liying.ipgw.utils.Constants;
import com.umeng.analytics.MobclickAgent;

import cc.duduhuo.applicationtoast.AppToast;

/**
 * 启动界面
 */
public class SplashActivity extends BaseActivity implements SplashLoadDataTask.LoadDataCallback {
    private static PackageInfo packageInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /** 设置是否对日志信息进行加密, 默认false(不加密). */
        MobclickAgent.enableEncrypt(true);
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        // 启动加载应用数据任务类
        startTask();
    }

    /**
     * 启动加载应用数据任务类
     */
    private void startTask() {
        SplashLoadDataTask task = new SplashLoadDataTask(this);
        task.execute();
    }

    /**
     * 跳转界面
     */
    private void jump() {
        int currentVersion = packageInfo.versionCode;
        int version = AccountApp.getInstance().prefs.getInt(Constants.VERSION, 0);
        if (version == 0) {
            // 首次使用
            startActivity(new Intent(this, InitAccountActivity.class));
            finish();
            //将当前版本写入preference中，则下次启动的时候，据此判断，不再为首次启动
            AccountApp.getInstance().editor.putInt(Constants.VERSION, currentVersion).apply();
        } else {
            // 该版本不是首次启动，跳转到MainActivity
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void loaded() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jump();// 界面跳转
            }
        }, 500);   // 停留时间500ms
    }

    @Override
    public void error() {
        AppToast.showToast("应用启动过程中出现错误，请重新启动。", Toast.LENGTH_LONG);
        finish();
    }

    @Override
    public void onBackPressed() {
        // Splash界面不允许使用back键
    }
}
