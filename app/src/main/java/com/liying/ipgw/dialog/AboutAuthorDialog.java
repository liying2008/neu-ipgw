package com.liying.ipgw.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.liying.ipgw.R;
import com.liying.ipgw.application.AccountApp;

import java.util.Timer;
import java.util.TimerTask;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/7 14:16
 * 版本：1.0
 * 描述：关于作者对话框
 * 备注：
 * =======================================================
 */
public class AboutAuthorDialog extends Dialog {
    private Activity mContext;
    private Button btnOK;
    private Timer timer;
    private static final int TRIGGER_EGG = 0x0001;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == TRIGGER_EGG) {
                // 显示“关于作者”对话框15秒，触发彩蛋
                AccountApp.getInstance().triggerEgg(mContext, 9);
                if (timer != null) {
                    timer.cancel();
                    task.cancel();
                    task = null;
                    timer = null;
                }
            }
            return true;
        }
    });
    // 构造方法
    public AboutAuthorDialog(Activity context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_about_author);
        initView();
        setListener();
        timer = new Timer(true);
        timer.schedule(task, 15000, 15000); //延时15s后执行，仅执行一次
    }

    // 定时任务
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            handler.sendEmptyMessage(TRIGGER_EGG);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (task != null) {
            task.cancel();
        }
    }

    private void initView() {
        btnOK = (Button) findViewById(R.id.btnOK);
    }

    private void setListener() {
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutAuthorDialog.this.dismiss();
            }
        });
    }
}
