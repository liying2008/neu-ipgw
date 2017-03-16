package com.liying.ipgw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.application.AccountApp;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 应用说明
 */
public class AppDeclareActivity extends BaseActivity {
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_declare);
        AccountApp.activityList.add(this);
        Intent intent = getIntent();
        String back = intent.getStringExtra("back");
        TextView tvBack = (TextView) findViewById(R.id.tvBack);
        // 设置返回按钮的标题
        tvBack.setText(back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        timer = new Timer(true);
        timer.schedule(task, 20000, 20000); //延时20s后执行，仅执行一次
    }

    // 定时任务
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AccountApp.getInstance().triggerEgg(AppDeclareActivity.this, 4);
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                    if (task != null) {
                        task.cancel();
                    }
                }
            });
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (task != null) {
            task.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AccountApp.activityList.remove(this);
    }
}
