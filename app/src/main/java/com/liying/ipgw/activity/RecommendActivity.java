package com.liying.ipgw.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.adapter.RecommendListAdapter;
import com.liying.ipgw.application.AccountApp;

import java.lang.ref.WeakReference;

import cc.duduhuo.applicationtoast.AppToast;

/**
 * 应用推荐
 */
public class RecommendActivity extends BaseActivity {
    private static WeakReference<RecommendActivity> recommendActivity;

    // 图标数组
    private static int[] icons = {R.mipmap.xdp_512x512, R.mipmap.sec_512x512, R.mipmap.circle_512x512};
    // 软件名称数组
    private static int[] name = {R.string.recommend_name_xdp, R.string.recommend_name_sec, R.string.recommend_name_circle};
    // 软件描述数组
    private static int[] desc = {R.string.recommend_desc_xdp, R.string.recommend_desc_sec, R.string.recommend_desc_circle};
    // 软件包名
    public static String[] packageName = {"lxy.liying.hdtvneu", "lxy.liying.secbook", "lxy.liying.circletodo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        AccountApp.activityList.add(this);
        TextView tvBack = (TextView) findViewById(R.id.tvBack);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ListView lvRecommend = (ListView) findViewById(R.id.lvRecommend);
        RecommendListAdapter adapter = new RecommendListAdapter(this, icons, name, desc);
        lvRecommend.setAdapter(adapter);

        recommendActivity = new WeakReference<RecommendActivity>(this);
    }

    /**
     * 获取本Activity的实例
     * @return
     */
    public static RecommendActivity getInstance() {
        return recommendActivity.get();
    }

    /**
     * 跳到应用商店的下载页面
     * @param packageName 应用的包名
     */
    public void goToDownload(String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(getPackageManager());
        if (componentName != null) {
            startActivity(intent);
        } else {
            AppToast.showToast("您没有安装应用市场类软件，无法打开应用详情页面。");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AccountApp.activityList.remove(this);
    }
}
