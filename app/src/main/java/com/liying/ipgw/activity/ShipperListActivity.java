package com.liying.ipgw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.adapter.PinyinAdapter;
import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.model.Shipper;
import com.liying.ipgw.utils.Constants;
import com.liying.ipgw.utils.ShipperListUtils;
import com.liying.ipgw.view.FancyIndexer;

import java.util.List;

/**
 * 快递公司列表界面
 */
public class ShipperListActivity extends BaseActivity {
    private TextView tvBack;
    private ExpandableListView elvContent;
    private FancyIndexer bar;
    private PinyinAdapter<Shipper> adapter;
    private List<Shipper> shipperList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper_list);
        AccountApp.activityList.add(this);
        findView();
        setListener();
        initData();
    }

    private void findView() {
        tvBack = (TextView) findViewById(R.id.tvBack);
        elvContent = (ExpandableListView) findViewById(R.id.elvContent);
        bar = (FancyIndexer) findViewById(R.id.bar);
    }

    private void setListener() {
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initData() {
        shipperList = ShipperListUtils.shipperList;

        /**加入支持泛型*/
        adapter = new PinyinAdapter<Shipper>(elvContent, shipperList, R.layout.item_pinyin_shipper) {

            @Override
            public String getItemName(Shipper shipper) {
                return shipper.getName();
            }

            /**返回viewholder持有*/
            @Override
            public ViewHolder getViewHolder(final Shipper shipper) {
                /**View holder*/
                class DataViewHolder extends ViewHolder implements View.OnClickListener {
                    public TextView tvName;

                    public DataViewHolder(Shipper shipper1) {
                        super(shipper1);
                    }

                    /**初始化*/
                    @Override
                    public ViewHolder getHolder(View view) {
                        tvName = (TextView) view.findViewById(R.id.tvName);
                        /**在这里设置点击事件*/
                        view.setOnClickListener(this);
                        return this;
                    }

                    /**显示数据*/
                    @Override
                    public void show() {
                        tvName.setText(getItem().getName());
                    }

                    /**点击事件*/
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("shipper", getItem());
                        System.out.println("Shipper = " + shipper.getName());
                        setResult(Constants.SHIPPER_RESULT, intent);
                        finish();
                    }
                }
                return new DataViewHolder(shipper);
            }

            @Override
            public void onItemClick(Shipper shipper, View view, int position) {
            }
        };
        /**展开并设置adapter*/
        adapter.expandGroup();

        bar.setOnTouchLetterChangedListener(new FancyIndexer.OnTouchLetterChangedListener() {

            @Override
            public void onTouchLetterChanged(String letter) {
                for (int i = 0, j = adapter.getKeyMapList().getTypes().size(); i < j; i++) {
                    String str = adapter.getKeyMapList().getTypes().get(i);
                    if (letter.toUpperCase().equals(str.toUpperCase())) {
                        /**跳转到选中的字母表*/
                        elvContent.setSelectedGroup(i);
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AccountApp.activityList.remove(this);
    }
}
