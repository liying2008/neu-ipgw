package com.liying.ipgw.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.callback.PermissionDenyCallback;
import com.liying.ipgw.utils.ColorThemeUtils;
import com.liying.ipgw.utils.Constants;
import com.liying.ipgw.utils.EggUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/2 18:32
 * 版本：1.0
 * 描述：Activity的基类
 * 备注：
 * =======================================================
 */
public class BaseActivity extends AppCompatActivity implements PermissionDenyCallback {
    /** 请求写外部存储的请求码 */
    public static final int WRITE_EXTERNAL_STORAGE_CODE = 0x0000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        loadTheme();    // 加载主题
        super.onCreate(savedInstanceState);
        // 检查数据是否存在，不存在则重新读取
        if (AccountApp.eggNums == null) {
            AccountApp.eggNums = new HashSet<>(10);
            AccountApp.eggNums.addAll(AccountApp.getInstance().eggPrefs.getStringSet(Constants.EGG_SET, AccountApp.eggNums));
        }
        if (AccountApp.eggCount == -1) {
            AccountApp.eggCount = AccountApp.eggNums.size();
        }
        if (AccountApp.eggList == null) {
            AccountApp.eggList = new ArrayList<>(10);
            AccountApp.eggNums = AccountApp.getInstance().eggPrefs.getStringSet(Constants.EGG_SET, AccountApp.eggNums);
            for (String eggNum : AccountApp.eggNums) {
                AccountApp.eggList.add(EggUtils.getEggByNum(Integer.parseInt(eggNum)));
            }
        }
        if (AccountApp.pushID == 0) {
            AccountApp.pushID = AccountApp.getInstance().prefs.getInt(Constants.PUSH_ID, 0);
        }
        if ("".equals(AccountApp.defaultUserName)) {
            AccountApp.defaultUserName = AccountApp.getInstance().prefs.getString(Constants.DEFAULT_ACCOUNT, "null");
        }
    }

    /**
     * 获取点击事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                HideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判定是否需要隐藏
     *
     * @param v
     * @param ev
     * @return
     */
    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 隐藏软键盘
     *
     * @param token
     */
    private void HideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    /**
     * 加载应用色彩主题
     */
    private void loadTheme() {
        AccountApp.colorThemeName = AccountApp.getInstance().prefs.getString(Constants.COLOR_THEME, "落栗");
        setTheme(ColorThemeUtils.getThemeByName(AccountApp.colorThemeName));
    }

    /**
     * 检查是否具有权限
     *
     * @param permissions 权限
     * @return
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 权限请求方法
     *
     * @param code        请求码
     * @param permissions 请求的权限数组
     */
    public void requestPermissions(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                permissionDeny(permissions[i]);
                return;
            }
        }
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_CODE:
                writeExternalStorage();
                break;
            default:
                break;
        }
    }

    /**
     * 默认的写外部存储权限处理
     */
    protected void writeExternalStorage() {

    }

    @Override
    public void permissionDeny(String permission) {

    }
}
