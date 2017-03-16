package com.liying.ipgw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.model.AccountInfo;
import com.liying.ipgw.utils.Constants;

import java.util.List;

import cc.duduhuo.applicationtoast.AppToast;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2017/3/15 15:22
 * 版本：1.0
 * 描述：初始化帐号
 * 备注：
 * =======================================================
 */
public class InitAccountActivity extends BaseActivity implements View.OnClickListener {
    private Button btnSaveAccount;
    private Button btnDelUsername, btnDelPassword;
    private TextView tvSkip;
    private EditText etUserName, etPassword;
    private AccountInfo accountInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_account);
        AccountApp.activityList.add(this);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        tvSkip = (TextView) findViewById(R.id.tvSkip);
        btnSaveAccount = (Button) this.findViewById(R.id.btnSaveAccount);
        btnDelUsername = (Button) this.findViewById(R.id.btnDelUsername);
        btnDelPassword = (Button) this.findViewById(R.id.btnDelPassword);
        etUserName = (EditText) this.findViewById(R.id.etUsername);
        etPassword = (EditText) this.findViewById(R.id.etPassword);
    }

    private void setListener() {
        tvSkip.setOnClickListener(this);
        btnDelUsername.setOnClickListener(this);
        btnDelPassword.setOnClickListener(this);
        btnSaveAccount.setOnClickListener(new SaveDefaultAccountListener());
        btnSaveAccount.setFocusable(true);
        btnSaveAccount.requestFocus();
        MyTextWatcher usernameTw = new MyTextWatcher(R.id.etUsername);
        etUserName.addTextChangedListener(usernameTw);    // 监听EditText内容变化
        MyTextWatcher passwordTw = new MyTextWatcher(R.id.etPassword);
        etPassword.addTextChangedListener(passwordTw);    // 监听EditText内容变化
    }

    private void initData() {
        accountInfo = new AccountInfo();
        init();
    }

    /**
     * 在init()方法中初始化两个EditText中的内容和单选按钮的值
     */
    private void init() {
        List<AccountInfo> list = AccountApp.aServices.findAllUsers();
        // 为了2.5版本及之前的版本能自动默认已保存的唯一帐号为默认使用帐号
        if (null != list && list.size() == 1) {
            AccountApp.defaultUserName = list.get(0).getUserName();
        }

        accountInfo = AccountApp.aServices.getAccountInfoByUserName(AccountApp.defaultUserName);
        if (accountInfo != null) {
            etUserName.setText(accountInfo.getUserName());
            etPassword.setText(accountInfo.getPsw());
        } else {
            etUserName.setText("");
            etPassword.setText("");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSkip:
                // 跳转到MainActivity，并关闭本界面
                startActivity(new Intent(InitAccountActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.btnDelUsername:
                etUserName.setText("");
                break;
            case R.id.btnDelPassword:
                etPassword.setText("");
                break;
            default:
                break;
        }

    }

    /**
     * 保存所有设置
     */
    private class SaveDefaultAccountListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String userName = etUserName.getText().toString();
            String password = etPassword.getText().toString();

            if (userName.equals("") || password.equals("")) {
                AppToast.showToast(R.string.userName_or_psw_is_null);
                return;
            } else if (null != AccountApp.aServices.getAccountInfoByUserName(userName)) {
                // 用户名已存在
                // 设置默认帐号
                AccountApp.getInstance().editor.putString(Constants.DEFAULT_ACCOUNT, userName).apply();
                AccountApp.defaultUserName = userName;
                AccountApp.aServices.updatePassword(userName, password);
                // 跳转到MainActivity，并关闭本界面
                startActivity(new Intent(InitAccountActivity.this, MainActivity.class));
                finish();
            } else {
                String range = Constants.INSIDE_CHINA;
                AccountInfo account = new AccountInfo(userName, password, range);
                //将信息加入到数据库中
                AccountApp.aServices.insertAccountInfo(account);
                AppToast.showToast(R.string.set_successfully);
                // 设置默认帐号
                AccountApp.getInstance().editor.putString(Constants.DEFAULT_ACCOUNT, userName).apply();
                AccountApp.defaultUserName = userName;
                // 跳转到MainActivity，并关闭本界面
                startActivity(new Intent(InitAccountActivity.this, MainActivity.class));
                finish();
            }
        }
    }

    class MyTextWatcher implements TextWatcher {
        private int editTextId;

        public MyTextWatcher(int editTextId) {
            this.editTextId = editTextId;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(s.toString())) {
                // 编辑框为空
                if (editTextId == R.id.etUsername) {
                    btnDelUsername.setVisibility(View.GONE);
                } else if (editTextId == R.id.etPassword) {
                    btnDelPassword.setVisibility(View.GONE);
                }
            } else {
                // 编辑框不空
                if (editTextId == R.id.etUsername) {
                    btnDelUsername.setVisibility(View.VISIBLE);
                } else if (editTextId == R.id.etPassword) {
                    btnDelPassword.setVisibility(View.VISIBLE);
                }
            }

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AccountApp.activityList.remove(this);
    }

}
