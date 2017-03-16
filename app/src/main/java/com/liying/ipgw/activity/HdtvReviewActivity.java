package com.liying.ipgw.activity;

import android.app.AlertDialog;
import com.liying.ipgw.dialog.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.adapter.NeuReviewListAdapter;
import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.model.ReviewDate;
import com.liying.ipgw.model.ReviewList;
import com.liying.ipgw.model.ReviewProgram;
import com.liying.ipgw.utils.Constants;
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
 * 日期：2017/3/15 23:16
 * 版本：1.0
 * 描述：回看节目列表Activity
 * 备注：由于选择的算法不好，导致在不同的时区情况下日期排序错乱
 * =======================================================
 */
public class HdtvReviewActivity extends BaseActivity implements OnResponseListener<String> {
    private TextView tvBack;
    private TextView tvMsgNone;
    private TextView tvReviewProgram;
    private ExpandableListView elvReview;
    private List<ReviewDate> groupDates;
    private List<List<ReviewProgram>> childPrograms;
    private NeuReviewListAdapter adapter;
    private ProgressDialog progressDialog;
    private String p;   // 回看频道
    private static final int REQUEST_WHAT = 0x0001;

    /**
     * 请求队列
     */
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hdtv_review);
        AccountApp.activityList.add(this);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        tvBack = (TextView) findViewById(R.id.tvBack);
        tvMsgNone = (TextView) findViewById(R.id.tvMsgNone);
        tvReviewProgram = (TextView) findViewById(R.id.tvReviewProgram);
        elvReview = (ExpandableListView) findViewById(R.id.elvReview);
        progressDialog = new ProgressDialog(this, "正在获取回看节目列表...");
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
        adapter = new NeuReviewListAdapter(this);
        elvReview.setAdapter(adapter);
        Intent intentData = getIntent();
        String[] programInfo = intentData.getStringArrayExtra(Constants.PROGRAM);
        p = programInfo[1];
        tvReviewProgram.setText(programInfo[0]);

        requestQueue = NoHttp.newRequestQueue();
        String url = "http://hdtv.neu6.edu.cn/time-select?p=" + p;
        sendRequest(url);
    }

    private void sendRequest(String url) {
        requestQueue.cancelAll();   // 取消队列中的所有请求
        Request<String> request = NoHttp.createStringRequest(url);
        requestQueue.add(REQUEST_WHAT, request, this);
    }

    @Override
    public void onStart(int what) {
        progressDialog.show();
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        tvMsgNone.setVisibility(View.GONE);
        ReviewList reviewList = RegexProgram.getReviewPrograms(response.get());
        this.groupDates = reviewList.getGroupDates();
        if (this.groupDates.size() == 0) {
            tvMsgNone.setVisibility(View.VISIBLE);
        } else {
            this.childPrograms = reviewList.getChildPrograms();
            adapter.setReviewData(groupDates, childPrograms, p);
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
        progressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AccountApp.activityList.remove(this);
        requestQueue.cancelAll();
    }
}
