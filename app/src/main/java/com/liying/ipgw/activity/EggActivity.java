package com.liying.ipgw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.model.Egg;
import com.liying.ipgw.utils.Constants;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2017/3/15 23:12
 * 版本：1.0
 * 描述：彩蛋列表（收纳盒）Activity
 * 备注：
 * =======================================================
 */
public class EggActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvBack, tvTitle;
    private ImageView ivEgg01, ivEgg02, ivEgg03, ivEgg04, ivEgg05;
    private ImageView ivEgg06, ivEgg07, ivEgg08, ivEgg09, ivEgg10;
    private List<Integer> gotId = new ArrayList<>(10);
    private List<Egg> gotList = AccountApp.eggList;
    private SparseArray<ImageView> eggArr = new SparseArray<>(10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg);
        AccountApp.activityList.add(this);
        initView();
        setListener();
        initData();

    }

    private void initView() {
        tvBack = (TextView) findViewById(R.id.tvBack);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        ivEgg01 = (ImageView) findViewById(R.id.ivEgg01);
        ivEgg02 = (ImageView) findViewById(R.id.ivEgg02);
        ivEgg03 = (ImageView) findViewById(R.id.ivEgg03);
        ivEgg04 = (ImageView) findViewById(R.id.ivEgg04);
        ivEgg05 = (ImageView) findViewById(R.id.ivEgg05);
        ivEgg06 = (ImageView) findViewById(R.id.ivEgg06);
        ivEgg07 = (ImageView) findViewById(R.id.ivEgg07);
        ivEgg08 = (ImageView) findViewById(R.id.ivEgg08);
        ivEgg09 = (ImageView) findViewById(R.id.ivEgg09);
        ivEgg10 = (ImageView) findViewById(R.id.ivEgg10);
        eggArr.put(1, ivEgg01);
        eggArr.put(2, ivEgg02);
        eggArr.put(3, ivEgg03);
        eggArr.put(4, ivEgg04);
        eggArr.put(5, ivEgg05);
        eggArr.put(6, ivEgg06);
        eggArr.put(7, ivEgg07);
        eggArr.put(8, ivEgg08);
        eggArr.put(9, ivEgg09);
        eggArr.put(10, ivEgg10);
    }

    private void setListener() {
        tvBack.setOnClickListener(this);
        ivEgg01.setOnClickListener(this);
        ivEgg02.setOnClickListener(this);
        ivEgg03.setOnClickListener(this);
        ivEgg04.setOnClickListener(this);
        ivEgg05.setOnClickListener(this);
        ivEgg06.setOnClickListener(this);
        ivEgg07.setOnClickListener(this);
        ivEgg08.setOnClickListener(this);
        ivEgg09.setOnClickListener(this);
        ivEgg10.setOnClickListener(this);
    }

    private void initData() {
        tvTitle.setText("彩蛋收纳盒 (" + AccountApp.eggCount + "/10)");
        gotId.clear();

        int gotEggCount = gotList.size();
        String eggGotList = "";
        for (int i = 0; i < gotEggCount; i++) {
            Egg egg = gotList.get(i);
            int id = egg.getEggNumber();
            gotId.add(id);
            gotEgg(id, egg.getEggDrawable());
            eggGotList += id + " | ";
        }

        // 友盟统计，彩蛋个数和已获得的彩蛋序号
        Map<String, String> eggValue = new HashMap<>();
        eggValue.put("eggId", eggGotList);
        MobclickAgent.onEventValue(this, Constants.UmengStatistics.EGG, eggValue, gotEggCount);
    }

    /**
     * 已经得到的彩蛋改为彩色
     *
     * @param eggNumber   彩蛋序号
     * @param eggDrawable 彩蛋图片资源ID
     */
    private void gotEgg(int eggNumber, int eggDrawable) {
        eggArr.get(eggNumber).setImageResource(eggDrawable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvBack:
                finish();
                break;
            case R.id.ivEgg01:
                startActivity(1);
                break;
            case R.id.ivEgg02:
                startActivity(2);
                break;
            case R.id.ivEgg03:
                startActivity(3);
                break;
            case R.id.ivEgg04:
                startActivity(4);
                break;
            case R.id.ivEgg05:
                startActivity(5);
                break;
            case R.id.ivEgg06:
                startActivity(6);
                break;
            case R.id.ivEgg07:
                startActivity(7);
                break;
            case R.id.ivEgg08:
                startActivity(8);
                break;
            case R.id.ivEgg09:
                startActivity(9);
                break;
            case R.id.ivEgg10:
                startActivity(10);
                break;
            default:
                break;
        }
    }

    /**
     * 启动EggPicActivity显示彩蛋大图
     *
     * @param id 彩蛋序号
     */
    private void startActivity(int id) {
        Intent intent = new Intent();
        intent.setClass(this, EggPicActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("got", gotId.contains(id));
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AccountApp.activityList.remove(this);
    }
}
