package com.liying.ipgw.fragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.activity.MainActivity;
import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.callback.GwStateCallback;
import com.liying.ipgw.callback.OnAccountChange;
import com.liying.ipgw.callback.OnlineInfoCallback;
import com.liying.ipgw.dialog.UserListDialog;
import com.liying.ipgw.model.AccountInfo;
import com.liying.ipgw.task.GetOnlineInfoTask;
import com.liying.ipgw.task.GwOperationTask;
import com.liying.ipgw.utils.Constants;
import com.liying.ipgw.utils.NetWorkUtils;
import com.liying.ipgw.widget.IpgwService;
import com.liying.ipgw.widget.OnlineInfo;
import com.umeng.analytics.MobclickAgent;

import cc.duduhuo.applicationtoast.AppToast;
import lxy.liying.ipgw.post.IPGWOperation;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/2 17:51
 * 版本：1.0
 * 描述：网关操作
 * 备注：
 * =======================================================
 */
public class GWFragment extends Fragment implements OnAccountChange {
    private EditText etUserName, etPassword;
    private TextView tvState, tvReason, tvIp, tvBalance, tvUsedFlow, tvUsedTime;
    private Button btnLogout, btnConnectPC, btnConnect, btnDisconnectAll;
    private Button btnMoreUsers;
    private AccountInfo accountInfo;
    private UserListDialog dialog = null;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gw, container, false);
        initView();
        setListener();
        setData(savedInstanceState);
        return view;
    }

    private void initView() {
        etUserName = (EditText) view.findViewById(R.id.etUsername);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        btnLogout = (Button) view.findViewById(R.id.btnLogout);
        btnConnectPC = (Button) view.findViewById(R.id.btnConnectPC);
        btnConnect = (Button) view.findViewById(R.id.btnConnect);
        btnDisconnectAll = (Button) view.findViewById(R.id.btnDisconnectAll);
        btnMoreUsers = (Button) view.findViewById(R.id.btnMoreUsers);
        tvState = (TextView) view.findViewById(R.id.tvState);
        tvReason = (TextView) view.findViewById(R.id.tvReason);
        tvIp = (TextView) view.findViewById(R.id.tvIp);
        tvBalance = (TextView) view.findViewById(R.id.tvBalance);
        tvUsedFlow = (TextView) view.findViewById(R.id.tvUsedFlow);
        tvUsedTime = (TextView) view.findViewById(R.id.tvUsedTime);
    }


    private void setListener() {
        btnConnect.setFocusable(true);
        btnConnect.requestFocus();
        btnConnect.setOnClickListener(new ConnectListener());
        btnConnectPC.setOnClickListener(new ConnectPCListener());
        btnLogout.setOnClickListener(new LogoutListener());
        btnDisconnectAll.setOnClickListener(new DisconnectAllListener());
        // 选择用户帐户
        btnMoreUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new UserListDialog(getActivity(), GWFragment.this);
                dialog.show();
            }
        });

        AccountApp.accountChangeCallback = this;    // 注册用户帐户改变监听器

        // 长按用户名输入框可以设置默认账户
        etUserName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // 设置当前帐号为默认帐号
                final String userName = etUserName.getText().toString();
                final String password = etPassword.getText().toString();

                if (null != AccountApp.aServices.getAccountInfoByUserName(userName)) {
                    // 说明数据库中已有该账户，则直接设置默认帐号
                    final AlertDialog.Builder builder = AccountApp.getAlertDialogBuilder(getActivity());
                    View dialogView = LayoutInflater.from(getActivity())
                            .inflate(R.layout.dialog_default_account, null);
                    builder.setView(dialogView);
                    final AlertDialog dialog = builder.create();
                    TextView tvDefaultAccountMsg = (TextView) dialogView.findViewById(R.id.tvDefaultAccountMsg);
                    Button btnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
                    Button btnOK = (Button) dialogView.findViewById(R.id.btnOK);
                    tvDefaultAccountMsg.setText(getActivity().getString(R.string.set_current_account_default, userName));
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    btnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AccountApp.defaultUserName = userName;
                            AccountApp.getInstance().editor.putString(Constants.DEFAULT_ACCOUNT, userName).apply();
                            AccountApp.aServices.updatePassword(userName, password);   // 更新数据库
                            AppToast.showToast(R.string.account_ready);
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                    // 账户或者密码为空
                    AppToast.showToast(R.string.userName_or_psw_is_null);
                } else {
                    // 说明数据库中没有该账户
                    // 将该账户添加到数据库中，再设置默认帐号
                    AlertDialog.Builder builder = AccountApp.getAlertDialogBuilder(getActivity());
                    View dialogView = LayoutInflater.from(getActivity())
                            .inflate(R.layout.dialog_default_account, null);
                    builder.setView(dialogView);
                    final AlertDialog dialog = builder.create();
                    TextView tvDefaultAccountMsg = (TextView) dialogView.findViewById(R.id.tvDefaultAccountMsg);
                    Button btnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
                    Button btnOK = (Button) dialogView.findViewById(R.id.btnOK);
                    tvDefaultAccountMsg.setText(getActivity().getString(R.string.save_and_set_default, userName));
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    btnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AccountApp.aServices.insertAccountInfo(new AccountInfo(userName, password, Constants.INSIDE_CHINA));
                            AccountApp.defaultUserName = userName;
                            AccountApp.getInstance().editor.putString(Constants.DEFAULT_ACCOUNT, userName).apply();
                            AppToast.showToast(R.string.account_ready);
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
                return false;
            }
        });
    }

    /**
     * 读取上次保存的状态或重新初始化
     *
     * @param data
     */
    public void setData(Bundle data) {
        if (data != null && data.size() > 0) {
            etUserName.setText(data.getString("etUsername"));
            etPassword.setText(data.getString("etPassword"));
            tvState.setText(data.getString("tvState"));
            tvReason.setText(data.getString("tvReason"));
            tvBalance.setText(data.getString("tvBalance"));
            tvUsedFlow.setText(data.getString("tvUsedFlow"));
            tvUsedTime.setText(data.getString("tvUsedTime"));
            tvIp.setText(data.getString("tvIp"));
        } else {
            init();
        }
    }

    /**
     * 保存当前Fragment状态
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("etUsername", etUserName.getText().toString());
        outState.putString("etPassword", etPassword.getText().toString());
        outState.putString("tvState", tvState.getText().toString());
        outState.putString("tvReason", tvReason.getText().toString());
        outState.putString("tvBalance", tvBalance.getText().toString());
        outState.putString("tvUsedFlow", tvUsedFlow.getText().toString());
        outState.putString("tvUsedTime", tvUsedTime.getText().toString());
        outState.putString("tvIp", tvIp.getText().toString());
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 更新视图
            updateOnlineInfo();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        updateOnlineInfo();
        // 注册一个接收器，在服务结束时接收更新
        IntentFilter filter = new IntentFilter(IpgwService.ACTION_IPGW);
        getActivity().registerReceiver(mReceiver, filter);

    }

    @Override
    public void onPause() {
        super.onPause();
        // 解除接收器的注册
        getActivity().unregisterReceiver(mReceiver);
    }

    /**
     * 更新在线信息显示
     */
    private void updateOnlineInfo() {
        OnlineInfo onlineInfo = IpgwService.getsInfo();
        tvState.setText(onlineInfo.getState());
        tvReason.setText(onlineInfo.getReason());
        tvIp.setText(onlineInfo.getIp());
        tvUsedTime.setText(onlineInfo.getUsedTime());
        tvUsedFlow.setText(onlineInfo.getUsedFlow());
        tvBalance.setText(onlineInfo.getBalance());
    }

    /**
     * 初始化两个EditText中的内容
     */
    private void init() {
        accountInfo = AccountApp.aServices.getAccountInfoByUserName(AccountApp.defaultUserName);
        if (accountInfo != null) {
            etUserName.setText(AccountApp.defaultUserName);
            etUserName.setSelection(AccountApp.defaultUserName.length());
            etPassword.setText(accountInfo.getPsw());
        }
    }

    private void ReSetMsg() {
        tvState.setText("");
        tvReason.setText("");
        tvBalance.setText("");
        tvUsedFlow.setText("");
        tvUsedTime.setText("");
        tvIp.setText("");
    }

    /**
     * 更改帐户
     *
     * @param accountInfo 新账户
     */
    @Override
    public void onNewAccount(AccountInfo accountInfo) {
        etUserName.setText(accountInfo.getUserName());
        etUserName.setSelection(accountInfo.getUserName().length());
        etPassword.setText(accountInfo.getPsw());
    }

    /**
     * 连接网络按钮的事件监听
     */
    private class ConnectListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 重置提示信息
            ReSetMsg();
            String[] usernamePsw = getUsernamePsw();
            if (usernamePsw[0].equals("") || usernamePsw[1].equals("")) {
                tvState.setText(getString(R.string.msg_state_n));
                tvReason.setText(getString(R.string.msg_reason_1));
                return;
            }

            //连接网络
            if (NetWorkUtils.isConnectedByState(getActivity())) {    // 网络状态可用
                // 执行连网操作
                executeOperation(IPGWOperation.CONNECT, usernamePsw[0], usernamePsw[1]);
            } else {
                pleaseCheckNetworkTip();
            }
            //获取在线信息
            getOnlineInfo();
            // 友盟统计：连接网络
            MobclickAgent.onEvent(getActivity(), Constants.UmengStatistics.CONNECT_CLICK);
        }
    }

    /**
     * 模拟PC端连接网络按钮的事件监听
     */
    private class ConnectPCListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 重置提示信息
            ReSetMsg();
            String[] usernamePsw = getUsernamePsw();
            if (usernamePsw[0].equals("") || usernamePsw[1].equals("")) {
                tvState.setText(getString(R.string.msg_state_n));
                tvReason.setText(getString(R.string.msg_reason_1));
                return;
            }
            //连接网络
            if (NetWorkUtils.isConnectedByState(getActivity())) {    // 网络状态可用
                // 执行连网操作
                executeOperation(IPGWOperation.CONNECT_PC, usernamePsw[0], usernamePsw[1]);
            } else {
                pleaseCheckNetworkTip();
            }
            //获取在线信息
            getOnlineInfo();
            // 友盟统计：连接网络
            MobclickAgent.onEvent(getActivity(), Constants.UmengStatistics.CONNECT_CLICK);
        }
    }

    /**
     * 注销登录按钮的事件监听
     */
    private class LogoutListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 友盟统计：断开网络
            MobclickAgent.onEvent(getActivity(), Constants.UmengStatistics.DISCONNECT_CLICK);
            // 重置提示信息
            ReSetMsg();
            String[] usernamePsw = getUsernamePsw();
            if (usernamePsw[0].equals("") || usernamePsw[1].equals("")) {
                tvState.setText(getString(R.string.msg_state_unknown));
                tvReason.setText(getString(R.string.msg_reason_1));
                return;
            }
            // 执行断网操作
            if (NetWorkUtils.isConnectedByState(getActivity())) {    // 网络状态可用
                executeOperation(IPGWOperation.DISCONNECT, usernamePsw[0], usernamePsw[1]);
            } else {
                pleaseCheckNetworkTip();
            }
        }
    }

    /**
     * 断开全部连接按钮的事件监听
     */
    private class DisconnectAllListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 友盟统计：断开网络
            MobclickAgent.onEvent(getActivity(), Constants.UmengStatistics.DISCONNECT_CLICK);
            // 重置提示信息
            ReSetMsg();
            String[] usernamePsw = getUsernamePsw();
            if (usernamePsw[0].equals("") || usernamePsw[1].equals("")) {
                tvState.setText(getString(R.string.msg_state_n));
                tvReason.setText(getString(R.string.msg_reason_1));
                return;
            }
            // 执行断网操作
            if (NetWorkUtils.isConnectedByState(getActivity())) {    // 网络状态可用
                executeOperation(IPGWOperation.DISCONNECT_ALL, usernamePsw[0], usernamePsw[1]);
            } else {
                pleaseCheckNetworkTip();
            }
        }
    }

    /**
     * 获取在线信息
     */
    private void getOnlineInfo() {
        if (NetWorkUtils.isConnectedByState(getActivity())) {        // 网络状态可用
            GetOnlineInfoTask task = new GetOnlineInfoTask(new OnlineInfoCallback() {
                @Override
                public void onlineInfo(String[] info) {
                    if (isAdded()) {    // 防止Fragment not attached to Activity
                       /*
                        已用流量：173.03M
                        已用时长：10:18:43
                        帐户余额：45.00
                        IP地址：58.154.209.141
                        */
                        tvUsedFlow.setText(info[0]);
                        tvUsedTime.setText(info[1]);
                        tvBalance.setText(info[2]);
                        tvIp.setText(info[3]);
                        String ip = info[3].replace("当前地址：", "");
                        AccountApp.getInstance().editor.putString(Constants.IP_ADDRESS, ip).apply();
                        // 修改连接状态显示
                        tvState.setText(getString(R.string.msg_state_y));
                        tvReason.setText("");
                        // 保存连接成功的帐号
                        AccountApp.lastSuccess = etUserName.getText().toString();
                        // 已用时长超700小时可获得彩蛋
                        try {
                            if (Integer.parseInt(info[1].split(":")[0]) >= 700) {
                                AccountApp.getInstance().triggerEgg(getActivity(), 6);
                            }
                        } catch (Exception e) {
                            // 异常，多数为NumberFormatException
                        }
                    }
                }

                @Override
                public void occurException(int type) {
                    if (isAdded()) {    // 防止Fragment not attached to Activity
                        switch (type) {
                            case OnlineInfoCallback.FLOW_ERROR:
                                tvUsedFlow.setText(R.string.msg_yiyongll_null);
                                tvUsedTime.setText(getString(R.string.msg_yiyongsc_null));
                                tvBalance.setText(getString(R.string.msg_yue_null));
                                tvIp.setText(getString(R.string.msg_address_null));
                                break;
                            case OnlineInfoCallback.FLOW_NULL:
                                tvUsedFlow.setText(getString(R.string.msg_yiyongll_null));
                                tvUsedTime.setText(getString(R.string.msg_yiyongsc_null));
                                tvBalance.setText(getString(R.string.msg_yue_null));
                                tvIp.setText(getString(R.string.msg_address_null));
                                break;
                            default:
                                break;
                        }
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
    public void executeOperation(final int operation, final String username, String password) {
        tvState.setText(getString(R.string.requesting));

        GwOperationTask task = new GwOperationTask(operation, new GwStateCallback() {
            @Override
            public void postGwState(int state) {
                if (isAdded()) {    // 防止Fragment not attached to Activity
                    String[] disconnectInfo;
                    String[] connectInfo;
                    switch (state) {
                        case 1:
                            // Authentication failed 验证失败
                            tvState.setText(getString(R.string.msg_state_n));
                            tvReason.setText(getString(R.string.msg_reason_2));
                            break;
                        case 2:
                            // 无法登录
                            tvState.setText(getString(R.string.msg_state_n));
                            tvReason.setText(getString(R.string.msg_reason_3));
                            break;
                        case 3:
                            // You are already online
                            tvState.setText(getString(R.string.msg_state_unknown));
                            tvReason.setText(getString(R.string.msg_reason_8));
                            break;
                        case 4:
                            // 密码错误
                            tvState.setText(getString(R.string.msg_state_n));
                            tvReason.setText(getString(R.string.msg_reason_4));
                            break;
                        case 5:     // 网络已断开
                        case 7:     // 注销成功
                            disconnectInfo = IPGWOperation.getInformation(operation);
                            tvState.setText(disconnectInfo[1]);
                            tvReason.setText(disconnectInfo[2]);
                            break;
                        case 6:     // 网络已连接
                        case 11:    // 客户端已连接
                            connectInfo = IPGWOperation.getInformation(operation);
                            tvState.setText(connectInfo[1]);
                            tvReason.setText(connectInfo[2]);
                            // 保存连接成功的帐号
                            AccountApp.lastSuccess = username;
                            // 检查推送
                            ((MainActivity) getActivity()).checkPush();
                            break;
                        case 8:
                            // 无法连接到IPGW网关
                            tvState.setText(getString(R.string.msg_state_n));
                            tvReason.setText(getString(R.string.msg_reason_7));
                            break;
                        case 9:
                            // 内部服务器错误
                            tvState.setText(getString(R.string.msg_state_n));
                            tvReason.setText(getString(R.string.msg_reason_6));
                            break;
                        case 12:
                            // 您似乎未曾连接到网络
                            tvState.setText(getString(R.string.msg_state_dis_already));
                            tvReason.setText("");
                            break;
                        case 13:
                            // 用户不存在
                            tvState.setText(getString(R.string.msg_state_n));
                            tvReason.setText(getString(R.string.msg_reason_10));
                            break;
                        case 14:
                            // 用户已欠费
                            tvState.setText(getString(R.string.msg_state_n));
                            tvReason.setText(getString(R.string.msg_reason_11));
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
            }
        });
        task.execute(username, password);
    }

    /**
     * 获取用户名和密码输入框的内容
     *
     * @return
     */
    private String[] getUsernamePsw() {
        String username = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        return new String[]{username, password};
    }

    /**
     * 未接入校园网，显示提示信息
     */
    private void pleaseCheckNetworkTip() {
        tvState.setText(getString(R.string.msg_state_n));
        tvReason.setText(getString(R.string.msg_reason_5));
    }
}
