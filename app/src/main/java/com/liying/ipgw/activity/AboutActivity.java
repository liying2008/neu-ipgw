package com.liying.ipgw.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.dialog.AboutAuthorDialog;
import com.liying.ipgw.utils.Constants;
import com.umeng.analytics.MobclickAgent;

import cc.duduhuo.applicationtoast.AppToast;

public class AboutActivity extends BaseActivity implements View.OnClickListener {
    private int clickIconCount = 0;
    private TextView tvBack, tvVersion;
    private ImageView ivIcon;
    private FrameLayout flFeedback, flShareIPGW, flDeclare, flRecommend, flAboutAuthor;

    /**
     * 作者邮箱地址
     */
    private static final String EMAIL = "liruoer2008@yeah.net";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        AccountApp.activityList.add(this);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        tvBack = (TextView) findViewById(R.id.tvBack);
        tvVersion = (TextView) findViewById(R.id.tvVersion);
        ivIcon = (ImageView) findViewById(R.id.ivIcon);
        flFeedback = (FrameLayout) findViewById(R.id.flFeedback);
        flShareIPGW = (FrameLayout) findViewById(R.id.flShareIPGW);
        flDeclare = (FrameLayout) findViewById(R.id.flDeclare);
        flRecommend = (FrameLayout) findViewById(R.id.flRecommend);
        flAboutAuthor = (FrameLayout) findViewById(R.id.flAboutAuthor);
    }

    private void setListener() {
        tvBack.setOnClickListener(this);
        ivIcon.setOnClickListener(this);
        flFeedback.setOnClickListener(this);
        flShareIPGW.setOnClickListener(this);
        flDeclare.setOnClickListener(this);
        flRecommend.setOnClickListener(this);
        flAboutAuthor.setOnClickListener(this);
    }

    private void initData() {
        String version = "v" + AccountApp.versionName;
        tvVersion.setText(version);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.tvBack:
                finish();
                break;
            case R.id.flFeedback:
                // 发送邮件
                // 友盟统计：用户反馈
                MobclickAgent.onEvent(this, Constants.UmengStatistics.USER_FEEDBACK);
                // 获取设备分辨率大小
                Display display = getWindowManager().getDefaultDisplay();
                String resolution = "Resolution: " + display.getWidth() + "x" + display.getHeight() + "; ";
                String msgPreset = resolution + "\nAndroid: " + android.os.Build.VERSION.RELEASE
                        + "; \nPhone: " + android.os.Build.MODEL
                        + "; \nVersion: " + AccountApp.versionName
                        + "; \n（以上数据由应用自动收集，发送邮件时请保留）。";
                Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
                mailIntent.setData(Uri.parse("mailto:" + EMAIL));
                mailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + " - 用户反馈");
                mailIntent.putExtra(Intent.EXTRA_TEXT, msgPreset);
                ComponentName componentName = mailIntent.resolveActivity(getPackageManager());
                if (componentName != null) {
                    startActivity(mailIntent);
                } else {
                    // 复制邮箱地址
                    ClipboardManager cmbName = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipDataName = ClipData.newPlainText(null, EMAIL);
                    cmbName.setPrimaryClip(clipDataName);
                    AppToast.showToast("您没有安装邮件类应用，已将作者邮箱地址复制到剪贴板。");
                }
                break;
            case R.id.flShareIPGW:
                // 分享该应用
                // 友盟统计：分享IPGW
                MobclickAgent.onEvent(this, Constants.UmengStatistics.SHARE_IPGW);
                String content = getString(R.string.share_description, getString(R.string.app_name));
                content += "\n\n——发送自 " + getString(R.string.app_name);
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/*");
                shareIntent.putExtra(Intent.EXTRA_TEXT, content);
                ComponentName componentName1 = shareIntent.resolveActivity(getPackageManager());
                if (componentName1 != null) {
                    startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
                } else {
                    AppToast.showToast("无法分享。");
                }

                break;
            case R.id.flDeclare:
                // 应用说明
                intent.setClass(this, AppDeclareActivity.class);
                intent.putExtra("back", "关于");
                startActivity(intent);
                break;
            case R.id.flRecommend:
                // 应用推荐
                intent.setClass(this, RecommendActivity.class);
                startActivity(intent);
                break;
            case R.id.flAboutAuthor:
                // 关于作者
                // 友盟统计：关于作者
                MobclickAgent.onEvent(this, Constants.UmengStatistics.ABOUT_AUTHOR);
                AboutAuthorDialog dialog = new AboutAuthorDialog(this);
                dialog.show();
                break;
            case R.id.ivIcon:
                // 点击图标
                clickIconCount++;
                if (clickIconCount == 10) {
                    // 触发彩蛋1
                    AccountApp.getInstance().triggerEgg(this, 1);
                }
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 点击计数清零
        clickIconCount = 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AccountApp.activityList.remove(this);
    }
}
