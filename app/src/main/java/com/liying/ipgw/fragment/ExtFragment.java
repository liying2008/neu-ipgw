package com.liying.ipgw.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.activity.AboutActivity;
import com.liying.ipgw.activity.AccountActivity;
import com.liying.ipgw.activity.AppFAQActivity;
import com.liying.ipgw.activity.BaseActivity;
import com.liying.ipgw.activity.CalendarActivity;
import com.liying.ipgw.activity.ColorThemeActivity;
import com.liying.ipgw.activity.EggActivity;
import com.liying.ipgw.activity.ExpressActivity;
import com.liying.ipgw.activity.MainActivity;
import com.liying.ipgw.activity.NeuHdtvListActivity;
import com.liying.ipgw.activity.RecentPostsActivity;
import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.callback.EggObtainedCallback;
import com.liying.ipgw.callback.OnlineInfoCallback;
import com.liying.ipgw.dialog.ProgressDialog;
import com.liying.ipgw.task.GetOnlineInfoTask;
import com.liying.ipgw.utils.Constants;
import com.liying.ipgw.utils.NetWorkUtils;
import com.umeng.analytics.MobclickAgent;

import cc.duduhuo.applicationtoast.AppToast;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/2 17:51
 * 版本：1.0
 * 描述：其他扩展功能
 * 备注：
 * =======================================================
 */
public class ExtFragment extends Fragment implements View.OnClickListener, EggObtainedCallback {
    private View view;
    private FrameLayout flAddAccount, flDelAccounts, flQueryInfo, flBrowserLogin;
    private FrameLayout flIPTV, flNavigator, flRecentPosts, flExpress, flSchoolCalendar;
    private FrameLayout flColorTheme, flAppFAQ, flCheckUpdate, flAbout;
    private FrameLayout flEggBox;
    private TextView tvThemeName, tvVersion;
    private ProgressDialog progressDialog;
    private static final String IPGW_URL = "https://ipgw.neu.edu.cn/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ext, container, false);
        initView();
        setListener();
        return view;
    }

    private void initView() {
        flAddAccount = (FrameLayout) view.findViewById(R.id.flAddAccount);
        flDelAccounts = (FrameLayout) view.findViewById(R.id.flDelAccounts);
        flQueryInfo = (FrameLayout) view.findViewById(R.id.flQueryInfo);
        flBrowserLogin = (FrameLayout) view.findViewById(R.id.flBrowserLogin);
        flIPTV = (FrameLayout) view.findViewById(R.id.flIPTV);
        flNavigator = (FrameLayout) view.findViewById(R.id.flNavigator);
        flRecentPosts = (FrameLayout) view.findViewById(R.id.flRecentPosts);
        flExpress = (FrameLayout) view.findViewById(R.id.flExpress);
        flSchoolCalendar = (FrameLayout) view.findViewById(R.id.flSchoolCalendar);
        flColorTheme = (FrameLayout) view.findViewById(R.id.flColorTheme);
        flAppFAQ = (FrameLayout) view.findViewById(R.id.flAppFAQ);
        flCheckUpdate = (FrameLayout) view.findViewById(R.id.flCheckUpdate);
        flAbout = (FrameLayout) view.findViewById(R.id.flAbout);
        flEggBox = (FrameLayout) view.findViewById(R.id.flEggBox);
        tvThemeName = (TextView) view.findViewById(R.id.tvThemeName);
        tvVersion = (TextView) view.findViewById(R.id.tvVersion);

        progressDialog = new ProgressDialog(getActivity(), "信息查询中...");
    }

    private void setListener() {
        flAddAccount.setOnClickListener(this);
        flDelAccounts.setOnClickListener(this);
        flQueryInfo.setOnClickListener(this);
        flBrowserLogin.setOnClickListener(this);
        flIPTV.setOnClickListener(this);
        flNavigator.setOnClickListener(this);
        flRecentPosts.setOnClickListener(this);
        flExpress.setOnClickListener(this);
        flSchoolCalendar.setOnClickListener(this);
        flColorTheme.setOnClickListener(this);
        flAppFAQ.setOnClickListener(this);
        flCheckUpdate.setOnClickListener(this);
        flAbout.setOnClickListener(this);
        flEggBox.setOnClickListener(this);
        AccountApp.eggsBoxShowListener = this;
    }

    @Override
    public void onResume() {
        super.onResume();
        tvThemeName.setText(AccountApp.colorThemeName);
        // 确定是否显示彩蛋收纳盒
        if (AccountApp.isEggsBoxShow) {
            flEggBox.setVisibility(View.VISIBLE);
        } else {
            flEggBox.setVisibility(View.GONE);
        }
        // 设置版本名称
        tvVersion.setText(AccountApp.getInstance().getVersionName());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.flAddAccount:
                // 添加帐号
                intent.setClass(getActivity(), AccountActivity.class);
                startActivity(intent);
                break;
            case R.id.flDelAccounts:
                // 删除已保存的帐号
                AlertDialog.Builder builder = AccountApp.getAlertDialogBuilder(getActivity());
                View dialogView = LayoutInflater.from(getActivity())
                    .inflate(R.layout.dialog_del_accounts, null);
                builder.setView(dialogView);
                final AlertDialog dialog = builder.create();
                Button btnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
                Button btnOK = (Button) dialogView.findViewById(R.id.btnOK);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AccountApp.aServices.deleteAccountInfo();
                        AppToast.showToast(R.string.delete_account_successfully);
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case R.id.flQueryInfo:
                // 查询在线信息
                // 友盟统计：查询流量
                MobclickAgent.onEvent(getActivity(), Constants.UmengStatistics.QUERY_FLOW);
                queryOnlineInfo();
                break;
            case R.id.flBrowserLogin:
                // 从浏览器登录
                // 友盟统计：从浏览器登录
                MobclickAgent.onEvent(getActivity(), Constants.UmengStatistics.IPGW_BROSWER);
                try {
                    Uri uri = Uri.parse(IPGW_URL);
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    AppToast.showToast("您没有安装浏览器。");
                }
                break;
            case R.id.flIPTV:
                // 网络电视
                // 友盟统计：看电视
                MobclickAgent.onEvent(getActivity(), Constants.UmengStatistics.WATCH_TV);
                // 检查设备是否连接到WiFi
                try {
                    if (!NetWorkUtils.isWifiByType(getActivity())) {
                        AppToast.showToast("设备未接入WiFi。");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                intent.setClass(getActivity(), NeuHdtvListActivity.class);
                startActivity(intent);
                break;
            case R.id.flNavigator:
                // 网址导航
                // 友盟统计：网址导航
                MobclickAgent.onEvent(getActivity(), Constants.UmengStatistics.NEU_NAVIGATOR);
                websiteNavigator();
                break;
            case R.id.flRecentPosts:
                // 最新网络中心黑板报
                // 友盟统计：最新网络中心黑板报
                MobclickAgent.onEvent(getActivity(), Constants.UmengStatistics.RECENT_POSTS);
                if (NetWorkUtils.isConnectedByState(getActivity())) {
                    intent.setClass(getActivity(), RecentPostsActivity.class);
                    startActivity(intent);
                } else {
                    // 未连接到网络
                    AppToast.showToast(R.string.network_unavailable);
                }
                break;
            case R.id.flExpress:
                // 快递查询
                // 友盟统计：快递查询
                MobclickAgent.onEvent(getActivity(), Constants.UmengStatistics.EXPRESS_QUERY);
                intent.setClass(getActivity(), ExpressActivity.class);
                startActivity(intent);
                break;
            case R.id.flSchoolCalendar:
                // 查看校历
                // 友盟统计：查看校历
                MobclickAgent.onEvent(getActivity(), Constants.UmengStatistics.SCHOOL_CALENDER);
                if (NetWorkUtils.isConnectedByState(getActivity())) {
                    intent.setClass(getActivity(), CalendarActivity.class);
                    startActivity(intent);
                } else {
                    AppToast.showToast(R.string.network_unavailable);
                }
                break;
            case R.id.flColorTheme:
                // 色彩主题
                // 友盟统计：色彩主题
                MobclickAgent.onEvent(getActivity(), Constants.UmengStatistics.COLOR_THEME);
                intent.setClass(getActivity(), ColorThemeActivity.class);
                startActivity(intent);
                break;
            case R.id.flAppFAQ:
                // 应用通知
                // 友盟统计：应用通知
                MobclickAgent.onEvent(getActivity(), Constants.UmengStatistics.APP_FAQ);
                if (NetWorkUtils.isConnectedByState(getActivity())) {
                    intent.setClass(getActivity(), AppFAQActivity.class);
                    startActivity(intent);
                } else {
                    // 未连接到网络
                    AppToast.showToast(R.string.network_unavailable);
                }
                break;
            case R.id.flCheckUpdate:
                // 检查更新
                // 友盟统计：检查更新
                MobclickAgent.onEvent(getActivity(), Constants.UmengStatistics.CHECK_UPDATE);
                MainActivity activity = (MainActivity) getActivity();
                if (activity.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    activity.writeExternalStorage();
                } else {
                    activity.requestPermissions(BaseActivity.WRITE_EXTERNAL_STORAGE_CODE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
                break;
            case R.id.flAbout:
                // 关于
                // 友盟统计：关于
                MobclickAgent.onEvent(getActivity(), Constants.UmengStatistics.ABOUT_IPGW);
                intent.setClass(getActivity(), AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.flEggBox:
                // 彩蛋收纳盒
                // 友盟统计：彩蛋收纳盒
                MobclickAgent.onEvent(getActivity(), Constants.UmengStatistics.EGG_BOX);
                intent.setClass(getActivity(), EggActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 网址导航
     */
    private void websiteNavigator() {
        AlertDialog.Builder builder = AccountApp.getAlertDialogBuilder(getActivity());
        View dialogView = LayoutInflater.from(getActivity())
            .inflate(R.layout.dialog_website_navigator, null);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        ListView lvWebsite = (ListView) dialogView.findViewById(R.id.lvWebsite);
        lvWebsite.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.item_simple_list,
            getResources().getStringArray(R.array.website_navigator)));

        lvWebsite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = "";
                switch (position) {
                    case 0:
                        url = "http://www.neu.edu.cn/";
                        break;
                    case 1:
                        url = "http://www.neu.edu.cn/info_calender.html";
                        break;
                    case 2:
                        url = "http://bb.neu.edu.cn/";
                        break;
                    case 3:
                        url = "http://aao.neu.edu.cn/";
                        break;
                    case 4:
                        url = "http://202.118.8.4/index.html";
                        break;
                    case 5:
                        url = "http://ecard.neu.edu.cn/";
                        break;
                    case 6:
                        url = "http://stu.neu.edu.cn/";
                        break;
                    case 7:
                        url = "http://www.neupioneer.com/index.html";
                        break;
                    case 8:
                        url = "http://ipgw.neu.edu.cn:8800/";
                        break;
                    case 9:
                        url = "http://hdtv.neu6.edu.cn/";
                        break;
                    default:
                        break;
                }
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                ComponentName componentName = intent.resolveActivity(getActivity().getPackageManager());
                if (componentName != null) {
                    startActivity(intent);
                } else {
                    AppToast.showToast("您没有安装浏览器。");
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 查询在线信息
     */
    private void queryOnlineInfo() {
        progressDialog.show();
        GetOnlineInfoTask task = new GetOnlineInfoTask(new OnlineInfoCallback() {
            @Override
            public void onlineInfo(String[] info) {
                if (isAdded()) {    // 防止Fragment not attached to Activity
                    progressDialog.dismiss();
                    AlertDialog.Builder builder = AccountApp.getAlertDialogBuilder(getActivity());
                    View dialogView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.dialog_online_info, null);
                    builder.setView(dialogView);
                    final AlertDialog dialog3 = builder.create();
                    TextView tvCurrentAccount = (TextView) dialogView.findViewById(R.id.tvCurrentAccount);
                    TextView tvOnlineInfo = (TextView) dialogView.findViewById(R.id.tvOnlineInfo);
                    Button btnOK3 = (Button) dialogView.findViewById(R.id.btnOK);
                    tvCurrentAccount.setText("帐户：" + AccountApp.lastSuccess);
                    final String infoText = getActivity().getString(R.string.online_info_statement) +
                        "\n\n已用流量：" + info[0] + "\n已用时长：" + info[1] +
                        "\n帐户余额：" + info[2] + "\n当前地址：" + info[3];
                    tvOnlineInfo.setText(infoText);
                    btnOK3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog3.dismiss();
                        }
                    });
                    tvOnlineInfo.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            // 复制在线信息，并触发彩蛋
                            ClipboardManager cmbName = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clipDataName = ClipData.newPlainText(null, infoText);
                            cmbName.setPrimaryClip(clipDataName);
                            AppToast.showToast("信息已复制。");
                            AccountApp.getInstance().triggerEgg(getActivity(), 2);
                            return true;
                        }
                    });
                    dialog3.show();
                }
            }

            @Override
            public void occurException(int type) {
                if (isAdded()) {    // 防止Fragment not attached to Activity
                    AlertDialog.Builder builder;    // Dialog Builder
                    View dialogView;    // 自定义的Dialog视图
                    switch (type) {
                        case OnlineInfoCallback.FLOW_ERROR:
                            progressDialog.dismiss();
                            builder = AccountApp.getAlertDialogBuilder(getActivity());
                            dialogView = LayoutInflater.from(getActivity())
                                .inflate(R.layout.dialog_account_invalid, null);
                            builder.setView(dialogView);
                            final AlertDialog dialog1 = builder.create();
                            Button btnOK1 = (Button) dialogView.findViewById(R.id.btnOK);
                            btnOK1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog1.dismiss();
                                }
                            });
                            dialog1.show();
                            break;
                        case OnlineInfoCallback.FLOW_NULL:
                            progressDialog.dismiss();
                            builder = AccountApp.getAlertDialogBuilder(getActivity());
                            dialogView = LayoutInflater.from(getActivity())
                                .inflate(R.layout.dialog_account_invalid, null);
                            builder.setView(dialogView);
                            final AlertDialog dialog2 = builder.create();
                            Button btnOK2 = (Button) dialogView.findViewById(R.id.btnOK);
                            btnOK2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog2.dismiss();
                                }
                            });
                            dialog2.show();
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        task.execute();
    }

    @Override
    public void ShowEggsBox() {
        AccountApp.isEggsBoxShow = true;
        flEggBox.setVisibility(View.VISIBLE);
    }
}
