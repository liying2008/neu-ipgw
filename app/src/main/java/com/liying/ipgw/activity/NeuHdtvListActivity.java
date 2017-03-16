package com.liying.ipgw.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.adapter.NeuHdtvListAdapter;
import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.dialog.ProgressDialog;
import com.liying.ipgw.model.Program;
import com.liying.ipgw.utils.RegexProgram;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2017/3/15 15:22
 * 版本：1.0
 * 描述：电视频道列表
 * 备注：
 * =======================================================
 */
public class NeuHdtvListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, OnResponseListener<String> {
    /** 观看时长 */
    public static long watchTime = 0L;
    private TextView tvBack;
    private TextView tvMsgNone;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog progressDialog;
    private ListView lvPrograms;
    private static final int REQUEST_WHAT = 0x0001;
    /** 东北大学IPv6视频直播测试站（人气排序） */
    private static final String NEU_HDTV_URL_ONLINE = "http://hdtv.neu6.edu.cn/?online";
    /**
     * 节目列表
     */
    private List<Program> mProgramsList;
    private NeuHdtvListAdapter adapter;
    // 创建请求队列, 默认并发3个请求, 传入数字改变并发数量: NoHttp.newRequestQueue(1);
    /**
     * 请求队列
     */
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neu_hdtv_list);
        AccountApp.activityList.add(this);
        initView();
        setListener();
        initData();

    }

    private void initView() {
        tvBack = (TextView) findViewById(R.id.tvBack);
        tvMsgNone = (TextView) findViewById(R.id.tvMsgNone);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        lvPrograms = (ListView) findViewById(R.id.lvPrograms);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.holo_red_light,
                R.color.holo_orange_light,
                R.color.holo_green_light,
                R.color.holo_blue_bright
        );
        progressDialog = new ProgressDialog(this, "正在获取节目列表...");
        progressDialog.setCanceledOnTouchOutside(false);
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
        // 创建请求队列, 默认并发3个请求, 传入数字改变并发数量: NoHttp.newRequestQueue(1);
        requestQueue = NoHttp.newRequestQueue();
        adapter = new NeuHdtvListAdapter(this);
        lvPrograms.setAdapter(adapter);
        sendRequest();
        progressDialog.show();
    }

    @Override
    public void onRefresh() {
        sendRequest();
    }

    @Override
    public void onStart(int what) {

    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        tvMsgNone.setVisibility(View.GONE);
        mProgramsList = RegexProgram.getAllPrograms(response.get());
        if (mProgramsList.size() == 0) {
            tvMsgNone.setVisibility(View.VISIBLE);
        } else {
            adapter.setData(mProgramsList);
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        tvMsgNone.setVisibility(View.VISIBLE);
        // 显示错误信息
        AlertDialog.Builder builder = AccountApp.getAlertDialogBuilder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_request_error, null);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        TextView tvErrorMsg = (TextView) dialogView.findViewById(R.id.tvErrorMsg);
        Button btnOK = (Button) dialogView.findViewById(R.id.btnOK);
        String msg = response.get();
        if (TextUtils.isEmpty(msg)) {
            tvErrorMsg.setText("请求失败。");
        } else {
            tvErrorMsg.setText(msg);
        }

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onFinish(int what) {
        swipeRefreshLayout.setRefreshing(false);
        progressDialog.dismiss();
    }

    /**
     * 发送请求，获取HDTV页面html
     */
    public void sendRequest() {
        requestQueue.cancelAll();   // 取消队列中的所有请求
        Request<String> request = NoHttp.createStringRequest(NEU_HDTV_URL_ONLINE);
        requestQueue.add(REQUEST_WHAT, request, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 观看电视超过10分钟获得彩蛋
        if (watchTime >= 10 * 60000) {
            AccountApp.getInstance().triggerEgg(this, 5);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AccountApp.activityList.remove(this);
        requestQueue.cancelAll();
    }
}
