package com.liying.ipgw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.utils.EggUtils;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2017/3/15 23:13
 * 版本：1.0
 * 描述：彩蛋大图Activity
 * 备注：
 * =======================================================
 */
public class EggPicActivity extends BaseActivity {
    private TextView tvTitle;
    private TextView tvEggMsg;
    private ImageView ivEggPic;
    private ImageView ivStamp;
    private int id;
    private boolean isGot;
    private int[] gotEggPicArr = {R.drawable.egg01_got, R.drawable.egg02_got, R.drawable.egg03_got,
            R.drawable.egg04_got, R.drawable.egg05_got, R.drawable.egg06_got, R.drawable.egg07_got,
            R.drawable.egg08_got, R.drawable.egg09_got, R.drawable.egg10_got};
    private int[] missEggPicArr = {R.drawable.egg01_miss, R.drawable.egg02_miss, R.drawable.egg03_miss,
            R.drawable.egg04_miss, R.drawable.egg05_miss, R.drawable.egg06_miss, R.drawable.egg07_miss,
            R.drawable.egg08_miss, R.drawable.egg09_miss, R.drawable.egg10_miss};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg_pic);
        AccountApp.activityList.add(this);
        findView();
        initData();
    }

    private void findView() {
        TextView tvBack = (TextView) findViewById(R.id.tvBack);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvEggMsg = (TextView) findViewById(R.id.tvEggMsg);
        ivEggPic = (ImageView) findViewById(R.id.ivEggPic);
        ivStamp = (ImageView) findViewById(R.id.ivStamp);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        String[] num = {"①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "⑨", "⑩"};
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 1);
        isGot = intent.getBooleanExtra("got", false);

        tvTitle.setText("彩蛋 " + num[id - 1]);
        showPic();
    }

    private void showPic() {
        if (isGot) {
            // 已获得
            ivStamp.setImageResource(R.mipmap.egg_got);
            ivEggPic.setImageResource(gotEggPicArr[id - 1]);
            // 显示彩蛋信息
            tvEggMsg.setVisibility(View.VISIBLE);
            tvEggMsg.setText(EggUtils.getEggByNum(id).getEggMsg());
        } else {
            // 未获得
            ivStamp.setImageResource(R.mipmap.egg_miss);
            ivEggPic.setImageResource(missEggPicArr[id - 1]);
            tvEggMsg.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AccountApp.activityList.remove(this);
    }
}
