package com.liying.ipgw.widget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.liying.ipgw.R;
import com.liying.ipgw.activity.SplashActivity;
import com.liying.ipgw.callback.GwStateCallback;
import com.liying.ipgw.callback.OnlineInfoCallback;
import com.liying.ipgw.db.AccountInfoServices;
import com.liying.ipgw.model.AccountInfo;
import com.liying.ipgw.task.GetOnlineInfoTask;
import com.liying.ipgw.task.GwOperationTask;
import com.liying.ipgw.utils.Constants;
import com.liying.ipgw.utils.NetWorkUtils;

import lxy.liying.ipgw.post.IPGWOperation;

public class IpgwService extends Service {
    /**
     * 更新完成后的广播动作
     */
    public static final String ACTION_IPGW = "com.liying.ipgw.appwidget.ACTION_IPGW";
    public static final int NO_OP = 0x0000;
    public static final int CONNECT = 0x0010;
    public static final int DISCONNECT = 0x0011;
    private static String infoText = "无信息";
    private AccountInfo accountInfo;
    private AccountInfoServices aServices;
    private String userName = "";
    private String password = "";

    /**
     * 当前数据，通过静态值保存
     */
    private static OnlineInfo sInfo = new OnlineInfo();
    private RemoteViews views;

    public static OnlineInfo getsInfo() {
        return sInfo;
    }

    /**
     * 这里我们并没有绑定这个服务，所以这个方法返回null即可
     *
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        aServices = new AccountInfoServices(this);
        // 创建AppWidget视图
        views = new RemoteViews(getPackageName(), R.layout.widget_ipgw);

        setListener();
        int op = intent.getIntExtra("op", NO_OP);

        switch (op) {
            case NO_OP:
                checkAccount();
                views.setTextViewText(R.id.tvInfo, infoText);
                break;
            case CONNECT:
                checkAccount();
                opConnect();
                break;
            case DISCONNECT:
                checkAccount();
                opDisconnect();
                break;
        }

        // 这个服务不应继续运行下去
        stopSelf();
        return START_NOT_STICKY;
    }

    /**
     * 设置按钮点击事件监听
     */
    private void setListener() {
        // 启用连网服务
        Intent i1 = new Intent(this, IpgwService.class);
        i1.putExtra("op", CONNECT);
        PendingIntent connectIntent = PendingIntent.getService(this, 0, i1, 0);
        views.setOnClickPendingIntent(R.id.btnWidgetConnect, connectIntent);

        // 启用断网服务
        Intent i2 = new Intent(this, IpgwService.class);
        i2.putExtra("op", DISCONNECT);
        PendingIntent disconnectIntent = PendingIntent.getService(this, 1, i2, 0);
        views.setOnClickPendingIntent(R.id.btnWidgetDisconnectAll, disconnectIntent);

        // 打开主界面
        Intent i3 = new Intent(this, SplashActivity.class);
        PendingIntent startAtyIntent = PendingIntent.getActivity(this, 2, i3, 0);
        views.setOnClickPendingIntent(R.id.btnWidgetStartAty, startAtyIntent);
    }

    private void setInfo() {
        infoText = "用户：" + userName;
        infoText += "\n状态：" + sInfo.getState();
        if (!"".equals(sInfo.getBalance())) {
            infoText += "\n余额：" + sInfo.getBalance();
        }
        if (!"".equals(sInfo.getUsedFlow())) {
            infoText += "\n流量：" + sInfo.getUsedFlow();
        }
        views.setTextViewText(R.id.tvInfo, infoText);
        updateWidget();
    }

    private void updateWidget() {
        // 更新小部件
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        ComponentName widget = new ComponentName(this, IpgwWidgetProvider.class);
        manager.updateAppWidget(widget, views);

        // 发送一个广播，通知所有的监听者
        Intent broadcast = new Intent(ACTION_IPGW);
        sendBroadcast(broadcast);
    }

    /**
     * 检查校园网帐户
     */
    private void checkAccount() {
        accountInfo = new AccountInfo();
        sInfo.clear();
        // 默认用户名
        String defUser = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE)
                .getString(Constants.DEFAULT_ACCOUNT, "");
        accountInfo = aServices.getAccountInfoByUserName(defUser);
        if (accountInfo != null) {
            userName = accountInfo.getUserName();
            password = accountInfo.getPsw();
        } else {
            infoText = "未设置默认帐户，无法使用小工具。";
            views.setTextViewText(R.id.tvInfo, infoText);
        }
        updateWidget();
    }

    private void opDisconnect() {
        if ("".equals(userName)) {
            return;
        }
        // 执行断网操作
        if (NetWorkUtils.isConnectedByState(IpgwService.this)) {    // 网络状态可用
            executeOperation(IPGWOperation.DISCONNECT_ALL);
        } else {
            pleaseCheckNetworkTip();
        }
    }

    private void opConnect() {
        if ("".equals(userName)) {
            return;
        }
        //连接网络
        if (NetWorkUtils.isConnectedByState(IpgwService.this)) {    // 网络状态可用
            // 执行连网操作
            executeOperation(IPGWOperation.CONNECT);
        } else {
            pleaseCheckNetworkTip();
        }
        //获取在线信息
        getOnlineInfo();
    }

    /**
     * 获取在线信息
     */
    private void getOnlineInfo() {
        if (NetWorkUtils.isConnectedByState(IpgwService.this)) {        // 网络状态可用
            GetOnlineInfoTask task = new GetOnlineInfoTask(new OnlineInfoCallback() {
                @Override
                public void onlineInfo(String[] info) {
                    sInfo.setUsedFlow(info[0]);
                    sInfo.setUsedTime(info[1]);
                    sInfo.setBalance(info[2]);
                    sInfo.setIp(info[3]);
                    String ip = info[3].replace("当前地址：", "");
                    // 修改连接状态显示
                    sInfo.setState(getString(R.string.msg_state_y));
                    sInfo.setReason("");
                    // 保存连接成功的帐号
                    setInfo();
                }

                @Override
                public void occurException(int type) {
                    switch (type) {
                        case OnlineInfoCallback.FLOW_ERROR:
                            sInfo.setUsedFlow(getString(R.string.msg_yiyongll_null));
                            sInfo.setUsedTime(getString(R.string.msg_yiyongsc_null));
                            sInfo.setBalance(getString(R.string.msg_yue_null));
                            sInfo.setIp(getString(R.string.msg_address_null));
                            setInfo();
                            break;
                        case OnlineInfoCallback.FLOW_NULL:
                            sInfo.setUsedFlow(getString(R.string.msg_yiyongll_null));
                            sInfo.setUsedTime(getString(R.string.msg_yiyongsc_null));
                            sInfo.setBalance(getString(R.string.msg_yue_null));
                            sInfo.setIp(getString(R.string.msg_address_null));
                            setInfo();
                            break;
                        default:
                            break;
                    }
                }
            });
            task.execute();
        }
    }

    /**
     * 执行操作，得到连网和断网信息
     *
     * @param operation 操作类型
     */
    public void executeOperation(final int operation) {
        // 正在发送请求
        sInfo.setState(getString(R.string.requesting));
        setInfo();

        GwOperationTask task = new GwOperationTask(operation, new GwStateCallback() {
            @Override
            public void postGwState(int state) {
                String[] disconnectInfo;
                String[] connectInfo;
                switch (state) {
                    case 1:
                        // Authentication failed 验证失败
                        sInfo.setState(getString(R.string.msg_state_n));
                        sInfo.setReason(getString(R.string.msg_reason_2));
                        setInfo();
                        break;
                    case 2:
                        // 无法登录
                        sInfo.setState(getString(R.string.msg_state_n));
                        sInfo.setReason(getString(R.string.msg_reason_3));
                        setInfo();
                        break;
                    case 3:
                        // You are already online
                        sInfo.setState(getString(R.string.msg_state_unknown));
                        sInfo.setReason(getString(R.string.msg_reason_8));
                        setInfo();
                        break;
                    case 4:
                        // 密码错误
                        sInfo.setState(getString(R.string.msg_state_n));
                        sInfo.setReason(getString(R.string.msg_reason_4));
                        setInfo();
                        break;
                    case 5: // 网络已断开
                    case 7: // 注销成功
                        disconnectInfo = IPGWOperation.getInformation(operation);
                        sInfo.setState(disconnectInfo[1]);
                        sInfo.setReason(disconnectInfo[2]);
                        setInfo();
                        break;
                    case 6:
                        // 网络已连接
                    case 11:
                        // 客户端连接成功
                        connectInfo = IPGWOperation.getInformation(operation);
                        sInfo.setState(connectInfo[1]);
                        sInfo.setReason(connectInfo[2]);
                        setInfo();
                        break;
                    case 8:
                        // 无法连接到IPGW网关
                        sInfo.setState(getString(R.string.msg_state_n));
                        sInfo.setReason(getString(R.string.msg_reason_7));
                        setInfo();
                        break;
                    case 9:
                        // 内部服务器错误
                        sInfo.setState(getString(R.string.msg_state_n));
                        sInfo.setReason(getString(R.string.msg_reason_6));
                        setInfo();
                        break;
                    case 12:
                        // 您似乎未曾连接到网络
                        sInfo.setState(getString(R.string.msg_state_dis_already));
                        sInfo.setReason("");
                        setInfo();
                        break;
                    case 13:
                        // 用户不存在
                        sInfo.setState(getString(R.string.msg_state_n));
                        sInfo.setReason(getString(R.string.msg_reason_10));
                        setInfo();
                        break;
                    case 14:
                        // 用户已欠费
                        sInfo.setState(getString(R.string.msg_state_n));
                        sInfo.setReason(getString(R.string.msg_reason_11));
                        setInfo();
                        break;
                    case 10:
                    case -1:
                    case -2:
                        // 请检查网络设置
                        pleaseCheckNetworkTip();
                    default:
                        break;
                }
            }
        });
        task.execute(userName, password);
    }

    /**
     * 未接入校园网，显示提示信息
     */
    private void pleaseCheckNetworkTip() {
        sInfo.setState(getString(R.string.msg_state_n));
        sInfo.setReason(getString(R.string.msg_reason_5));
        setInfo();
    }
}