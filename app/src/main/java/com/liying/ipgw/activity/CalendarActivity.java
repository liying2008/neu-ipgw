package com.liying.ipgw.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.callback.OnCalendarZoom;
import com.liying.ipgw.view.PinchToZoomDraweeView;

/**
 * 校历显示界面
 */
public class CalendarActivity extends BaseActivity implements OnCalendarZoom {
    private TextView tvBack;
    private PinchToZoomDraweeView sdvCalendar;

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
        sdvCalendar = (PinchToZoomDraweeView) findViewById(R.id.sdvCalendar);
    }

    private void setListener() {
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 设置缩放校历监听器
        sdvCalendar.setCalendarZoomListener(this);
    }

    private void initData() {
        sdvCalendar.setImageURI(Uri.parse("http://duduhuo.cc/ddh/pic/xiaoli.jpg"));
    }

    @Override
    public void zoomCalendar() {
        // 缩放校历，触发彩蛋
        AccountApp.getInstance().triggerEgg(this, 7);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AccountApp.activityList.remove(this);
    }
}
