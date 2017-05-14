package com.liying.ipgw.application;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.activity.BaseActivity;
import com.liying.ipgw.callback.EggObtainedCallback;
import com.liying.ipgw.callback.OnAccountChange;
import com.liying.ipgw.db.AccountInfoServices;
import com.liying.ipgw.db.ExpressServices;
import com.liying.ipgw.model.Egg;
import com.liying.ipgw.utils.Constants;
import com.liying.ipgw.utils.EggUtils;
import com.umeng.analytics.MobclickAgent;
import com.yanzhenjie.nohttp.NoHttp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cc.duduhuo.applicationtoast.AppToast;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2015/12/6 15:16
 * 版本：1.0
 * 描述：Application
 * 备注：
 * =======================================================
 */
public class AccountApp extends Application {
    /** 程序使用的默认帐号 */
    public static String defaultUserName = "";
    /** 全局prefs */
    public SharedPreferences prefs;
    /** 全局editor */
    public SharedPreferences.Editor editor;
    /** 彩蛋prefs */
    public SharedPreferences eggPrefs;
    /** 彩蛋editor */
    public SharedPreferences.Editor eggEditor;

    public static AccountInfoServices aServices = null;
    public static ExpressServices eServices = null;
    /** 色彩主题的名称 */
    public static String colorThemeName;
    /** 上次连接成功的帐户 */
    public static String lastSuccess = "未知";
    /** 当前Application实例 */
    private static AccountApp mInstance;
    public static List<BaseActivity> activityList = new ArrayList<>();
    /** 应用启动后是否已经检测过WiFi */
    public static boolean isWifiChecked = false;
    /** 应用启动后是否已经检查过PUSH */
    public static boolean isPushed = false;
    /** 用户帐户改变 */
    public static OnAccountChange accountChangeCallback;
    /** 用户已获取的彩蛋数量 */
    public static int eggCount = -1;
    /** 用户已获取的彩蛋列表 */
    public static List<Egg> eggList = null;
    /** 用户已获取的彩蛋号码 */
    public static Set<String> eggNums = null;
    /** 是否显示彩蛋收纳盒 */
    public static boolean isEggsBoxShow = false;
    /** 消息推送的ID */
    public static int pushID = 0;
    /** 找到彩蛋的回调接口 */
    public static EggObtainedCallback eggsBoxShowListener;

    @Override
    public void onCreate() {
        super.onCreate();
        // 实例化SharedPreferences
        prefs = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
        editor = prefs.edit();
        eggPrefs = getSharedPreferences(Constants.EGG_PREFS_NAME, MODE_PRIVATE);
        eggEditor = eggPrefs.edit();
        mInstance = this;
        aServices = new AccountInfoServices(this);
        eServices = new ExpressServices(this);
        // 检查工作目录
        checkWorkDir();
        // 友盟场景类型设置接口
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        // 初始化ApplicationToast
        AppToast.init(this);
        // 初始化NoHttp
        NoHttp.initialize(this);
    }

    /** 得到本Application实例 */
    public static AccountApp getInstance() {
        return mInstance;
    }

    /**
     * 检查工作目录和下载目录是否存在，不存在则创建
     */
    private void checkWorkDir() {
        File dir = new File(Constants.WORK_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 得到对话框构建器实例
     * @param context
     * You need to use your Activity as the Context for the Dialog not the Application.
     * @return
     */
    public static AlertDialog.Builder getAlertDialogBuilder(Activity context) {
        return new AlertDialog.Builder(context);
    }

    /**
     * 获取当前版本号
     *
     * @return
     */
    public int getVersionCode() {
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo;
        int versionCode = 0;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            versionCode = packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本名称
     *
     * @return 版本名称
     */
    public String getVersionName() {
        String packageName = getPackageName();
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(packageName, 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 重新创建所有Activity
     */
    public static void recreateActivity() {
        for (int i = 0; i < activityList.size(); i++) {
            activityList.get(i).recreate();
        }
    }

    /**
     * 触发彩蛋
     * @param activity
     */
    public void triggerEgg(Activity activity, int eggNum) {
        String[] num = {"①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "⑨", "⑩"};
        if (eggNums.contains(String.valueOf(eggNum))) {
            return;
        }
        Egg egg = EggUtils.getEggByNum(eggNum);
        AlertDialog.Builder builder = getAlertDialogBuilder(activity);
        View dialogView = activity.getLayoutInflater().inflate(R.layout.dialog_egg, null);
        TextView tvEggTitle = (TextView) dialogView.findViewById(R.id.tvEggTitle);
        ImageView ivEggImg = (ImageView) dialogView.findViewById(R.id.ivEggImg);
        TextView tvEggMsg = (TextView) dialogView.findViewById(R.id.tvEggMsg);
        Button btnEggOK = (Button) dialogView.findViewById(R.id.btnEggOK);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        tvEggTitle.setText("捡到彩蛋 " + num[eggNum - 1]);
        ivEggImg.setImageResource(egg.getEggDrawable());
        tvEggMsg.setText(egg.getEggMsg());
        btnEggOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

        if (!isEggsBoxShow) {
            if (eggsBoxShowListener != null) {
                eggsBoxShowListener.ShowEggsBox();
            }
            eggEditor.putBoolean(Constants.EGG_BOX, true).commit();
        }
        eggCount++;
        eggList.add(egg);
        eggNums.add(String.valueOf(eggNum));
        eggEditor.remove(Constants.EGG_SET).commit();
        eggEditor.putStringSet(Constants.EGG_SET, eggNums).commit();
    }
}
