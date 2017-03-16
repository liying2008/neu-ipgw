package com.liying.ipgw.activity;

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

import cc.duduhuo.applicationtoast.AppToast;

public class AccountActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvBack;
    private EditText etUserName, etPassword;
    private Button btnSaveAccount, btnSaveAccountAsDefault;
    private Button btnDelUsername, btnDelPassword;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        AccountApp.activityList.add(this);
        initView();
        setListener();
    }

    private void initView() {
        tvBack = (TextView) findViewById(R.id.tvBack);
        btnSaveAccount = (Button) this.findViewById(R.id.btnSaveAccount);
        btnSaveAccountAsDefault = (Button) this.findViewById(R.id.btnSaveAccountAsDefault);
        btnDelUsername = (Button) this.findViewById(R.id.btnDelUsername);
        btnDelPassword = (Button) this.findViewById(R.id.btnDelPassword);
        etUserName = (EditText) this.findViewById(R.id.etUsername);
        etPassword = (EditText) this.findViewById(R.id.etPassword);
    }

    private void setListener() {
        tvBack.setOnClickListener(this);
        btnSaveAccount.setOnClickListener(this);
        btnSaveAccountAsDefault.setOnClickListener(this);
        btnDelUsername.setOnClickListener(this);
        btnDelPassword.setOnClickListener(this);
        MyTextWatcher usernameTw = new MyTextWatcher(R.id.etUsername);
        etUserName.addTextChangedListener(usernameTw);    // 监听EditText内容变化
        MyTextWatcher passwordTw = new MyTextWatcher(R.id.etPassword);
        etPassword.addTextChangedListener(passwordTw);    // 监听EditText内容变化
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvBack:
                finish();
                break;
            case R.id.btnDelUsername:
                etUserName.setText("");
                break;
            case R.id.btnDelPassword:
                etPassword.setText("");
                break;
            case R.id.btnSaveAccount:
                if (!isEmpty()) {
                    saveAccount();
                }
                break;
            case R.id.btnSaveAccountAsDefault:
                if (!isEmpty()) {
                    setAsDefault();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 用户名密码输入框是否为空
     *
     * @return true：空
     */
    private boolean isEmpty() {
        username = etUserName.getText().toString();
        password = etPassword.getText().toString();
        if (username.equals("") || password.equals("")) {
            // 用户名或者密码为空
            AppToast.showToast(R.string.userName_or_psw_is_null);
            return true;
        }
        return false;
    }

    /**
     * 添加帐户
     *
     * @return 用户名密码是否为空
     */
    private void saveAccount() {
        if (null != AccountApp.aServices.getAccountInfoByUserName(username)) {
            // 用户名已存在
            AppToast.showToast(R.string.userName_exists_already);
        } else {
            String range = Constants.INSIDE_CHINA;
            AccountInfo account = new AccountInfo(username, password, range);
            //将信息加入到数据库中
            AccountApp.aServices.insertAccountInfo(account);
            AppToast.showToast(R.string.add_account_successfully);
            int accountCount = AccountApp.aServices.findAllUsers().size();
            // 如果数据库中只有一个帐号，则该账号就是默认帐号
            if (accountCount == 1) {
                AccountApp.getInstance().editor.putString(Constants.DEFAULT_ACCOUNT, username).apply();
                AccountApp.defaultUserName = username;
                AccountApp.accountChangeCallback.onNewAccount(account);
            } else if (accountCount == 5) {
                // 添加5个帐户，触发彩蛋
                AccountApp.getInstance().triggerEgg(this, 8);
            }
        }
    }

    /**
     * 设置为默认帐户
     */
    private void setAsDefault() {
        AccountInfo account = AccountApp.aServices.getAccountInfoByUserName(username);
        if (account == null) {
            String range = Constants.INSIDE_CHINA;
            account = new AccountInfo(username, password, range);
            //将信息加入到数据库中
            AccountApp.aServices.insertAccountInfo(account);
        }
        if (AccountApp.defaultUserName.equals(username)) {
            AppToast.showToast("该账户已是默认帐户。");
        } else {
            AccountApp.getInstance().editor.putString(Constants.DEFAULT_ACCOUNT, username).apply();
            AccountApp.defaultUserName = username;
            AppToast.showToast("已将该账户设为默认帐户。");
        }
        AccountApp.accountChangeCallback.onNewAccount(account);
        int accountCount = AccountApp.aServices.findAllUsers().size();
        if (accountCount == 5) {
            // 添加5个帐户，触发彩蛋
            AccountApp.getInstance().triggerEgg(this, 8);
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
