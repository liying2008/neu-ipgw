package com.liying.ipgw.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.callback.OnCheckUpdateCallback;
import com.liying.ipgw.callback.PushCallback;
import com.liying.ipgw.dialog.NewVersionDialog;
import com.liying.ipgw.fragment.ExtFragment;
import com.liying.ipgw.fragment.GWFragment;
import com.liying.ipgw.model.RecentPost;
import com.liying.ipgw.model.UpdateMsg;
import com.liying.ipgw.task.CheckUpdateTask;
import com.liying.ipgw.task.ReceivePushTask;
import com.liying.ipgw.utils.Constants;
import com.liying.ipgw.utils.DensityUtil;
import com.liying.ipgw.utils.NetWorkUtils;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import cc.duduhuo.applicationtoast.AppToast;
import lxy.liying.ipgw.post.IPGWOperation;

public class MainActivity extends BaseActivity implements PushCallback {
    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;
    private ImageButton ibInfo;
    private ViewPager viewPager;
    private Indicator indicator;
    private String[] tabName = {"网关", "扩展"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AccountApp.activityList.add(this);
        initView();
        setListener();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.fragment_tabmain_viewPager);
        indicator = (Indicator) findViewById(R.id.fragment_tabmain_indicator);
        ibInfo = (ImageButton) findViewById(R.id.ibInfo);
        if (!AccountApp.isWifiChecked) {
            // 检查设备是否连接到WiFi
            try {
                if (!NetWorkUtils.isWifiByType(this)) {
                    AppToast.showToast("设备未接入WiFi。");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            AccountApp.isWifiChecked = true;
        }
        // 检查推送
        checkPush();
    }

    private void setListener() {
        ibInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AppDeclareActivity.class);
                if (indicator.getCurrentItem() == 0) {
                    intent.putExtra("back", tabName[0]);
                } else if (indicator.getCurrentItem() == 1) {
                    intent.putExtra("back", tabName[1]);
                }
                startActivity(intent);
            }
        });

        TypedArray ta = obtainStyledAttributes(new int[]{R.attr.main_color, R.attr.sub_color});
        int selectColor = ta.getColor(0, Color.BLACK);
        int unSelectColor = ta.getColor(1, Color.GRAY);
        ta.recycle();

        indicator.setScrollBar(new ColorBar(getApplicationContext(),
                selectColor, DensityUtil.dip2px(this, 2)));
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor));
        viewPager.setOffscreenPageLimit(2);

        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        inflate = LayoutInflater.from(getApplicationContext());
        indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return tabName.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.tab_top, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(tabName[position]);
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            if (position == 0) {
                // 网关
                return new GWFragment();
            } else if (position == 1) {
                // 扩展
                return new ExtFragment();
            }
            return null;
        }
    }

    @Override
    public void writeExternalStorage() {
        AppToast.showToast("正在检查更新……");
        if (!NetWorkUtils.isConnectedByState(this)) {
            AppToast.showToast(R.string.network_unavailable);
            return;
        }
        CheckUpdateTask task = new CheckUpdateTask(new OnCheckUpdateCallback() {
            @Override
            public void onUpdate(UpdateMsg msg) {
                NewVersionDialog dialog = new NewVersionDialog(MainActivity.this, msg);
                dialog.show();
            }

            @Override
            public void onNoUpdate() {
                AppToast.showToast("当前版本已是最新。");
            }

            @Override
            public void onNull() {
                AppToast.showToast("检查更新失败。");
            }
        });
        task.execute();
    }

    /**
     * 权限拒绝回调
     *
     * @param permission 请求的权限
     */
    @Override
    public void permissionDeny(String permission) {
        super.permissionDeny(permission);
        if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            AppToast.showToast("权限请求被拒绝，无法检查更新。");
        }
    }

    /**
     * 检查是否有新消息推送
     * 仅当本次未推送过并且有网络时才去检查消息推送
     */
    public void checkPush() {
        if (!AccountApp.isPushed) {
            if (NetWorkUtils.isConnectedByState(this)) {
                ReceivePushTask task = new ReceivePushTask(this);
                task.execute();
            }
        }
    }

    /**
     * 有新消息推送
     *
     * @param post
     */
    @Override
    public void newPush(final RecentPost post) {
        AccountApp.isPushed = true;
        // 显示重要消息的对话框
        AlertDialog.Builder builder = AccountApp.getAlertDialogBuilder(this);
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_push, null);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        Button btnOK = (Button) dialogView.findViewById(R.id.btnOK);
        TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvTitle);
        TextView tvDate = (TextView) dialogView.findViewById(R.id.tvDate);
        HtmlTextView tvContent = (HtmlTextView) dialogView.findViewById(R.id.tvContent);

        tvTitle.setText(post.getTitle());
        tvDate.setText("发布日期：" + post.getDate());
        tvContent.setHtml(post.getContent());
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountApp.pushID = post.getId();
                AccountApp.getInstance().editor.putInt(Constants.PUSH_ID, AccountApp.pushID).apply();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AccountApp.activityList.remove(this);
        // 退出时取消请求
        IPGWOperation.cancel();
    }
}
