package com.liying.ipgw.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.chrisbanes.photoview.PhotoView;
import com.liying.ipgw.R;
import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.task.SaveCalendarTask;

import cc.duduhuo.applicationtoast.AppToast;

/**
 * 校历显示界面
 */
public class CalendarActivity extends BaseActivity {
    private TextView tvBack;
    private PhotoView sdvCalendar;
    private GlideDrawable resource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        AccountApp.activityList.add(this);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        tvBack = (TextView) findViewById(R.id.tvBack);
        sdvCalendar = (PhotoView) findViewById(R.id.sdvCalendar);
    }

    private void setListener() {
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 设置长按校历监听器
        sdvCalendar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = AccountApp.getAlertDialogBuilder(CalendarActivity.this);
                View dialogView = LayoutInflater.from(CalendarActivity.this)
                        .inflate(R.layout.dialog_del_accounts, null);
                builder.setView(dialogView);
                final AlertDialog dialog = builder.create();
                TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvTitle);
                TextView tvMsg = (TextView) dialogView.findViewById(R.id.tvMsg);
                tvTitle.setText("保存图片");
                tvMsg.setText("保存校历图片到存储卡上？");
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
                        if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            writeExternalStorage();
                        } else {
                            requestPermissions(BaseActivity.WRITE_EXTERNAL_STORAGE_CODE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    private void initData() {
        Glide.with(this).load("http://duduhuo.cc/ddh/pic/xiaoli.jpg").placeholder(R.mipmap.loading).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                sdvCalendar.setImageDrawable(resource);
                CalendarActivity.this.resource = resource;
            }
        });
    }

    @Override
    protected void writeExternalStorage() {
        super.writeExternalStorage();
        AppToast.showToast("正在保存...");
        SaveCalendarTask task = new SaveCalendarTask(resource);
        task.execute();
        // 保存校历，触发彩蛋
        AccountApp.getInstance().triggerEgg(CalendarActivity.this, 7);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AccountApp.activityList.remove(this);
    }
}
