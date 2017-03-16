package com.liying.ipgw.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.adapter.UserListAdapter;
import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.callback.OnAccountChange;
import com.liying.ipgw.model.AccountInfo;
import com.liying.ipgw.utils.Constants;

import java.lang.ref.WeakReference;
import java.util.List;

import cc.duduhuo.applicationtoast.AppToast;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2015/12/6 21:57
 * 版本：1.0
 * 描述：帐户列表对话框
 * 备注：
 * =======================================================
 */
public class UserListDialog extends Dialog {
    private Activity mContext;
    private ListView lvUsers;
    private TextView tvAccountNone;
    private UserListAdapter adapter;
    /**
     * 用户列表
     */
    private List<AccountInfo> users;
    private OnAccountChange callback;
    private static WeakReference<UserListDialog> dialog;

    public UserListDialog(Activity context, OnAccountChange callback) {
        super(context);
        mContext = context;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new WeakReference<UserListDialog>(this);
        initView();
        initData();
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_userlist);
        lvUsers = (ListView) findViewById(R.id.lvUsers);
        tvAccountNone = (TextView) findViewById(R.id.tvAccountNone);
    }

    private void initData() {
        // 获取用户列表
        users = getAllUsers();
        if (null == users || users.size() == 0) {
            // 没有帐户
            tvAccountNone.setVisibility(View.VISIBLE);
            lvUsers.setVisibility(View.GONE);
            return;
        }
        adapter = new UserListAdapter(mContext, users);
        lvUsers.setAdapter(adapter);
        lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                if (callback != null) {
                    callback.onNewAccount(users.get(position));
                }
            }
        });
        lvUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // 设置当前帐号为默认帐号
                AccountInfo accountInfo = users.get(position);
                AccountApp.defaultUserName = accountInfo.getUserName();
                AccountApp.getInstance().editor.putString(Constants.DEFAULT_ACCOUNT, AccountApp.defaultUserName).apply();
                AppToast.showToast(R.string.set_current_account_default_ok);
                if (callback != null) {
                    callback.onNewAccount(accountInfo);
                }
                dismiss();
                return false;
            }
        });
    }

    public static UserListDialog getInstance() {
        return dialog.get();
    }
    /**
     * 删除一个用户
     * @param position users的下标
     */
    public void delUser(int position) {
        String username = users.get(position).getUserName();
        AccountApp.aServices.deleteAccountByUserName(username);
        AppToast.showToast(mContext.getString(R.string.account_has_been_deleted, username));
        users = getAllUsers();
        if (null != users && users.size() > 0){
            adapter.setData(users);
        } else {
            // 删除所有帐户触发彩蛋
            AccountApp.getInstance().triggerEgg(mContext, 3);
            // 关闭对话框
            dismiss();
        }
    }

    /**
     * 获取所有用户
     *
     * @return 用户列表
     */
    private List<AccountInfo> getAllUsers() {
        return AccountApp.aServices.findAllUsers();
    }
}
