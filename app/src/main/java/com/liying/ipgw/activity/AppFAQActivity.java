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
import com.liying.ipgw.adapter.FaqListAdapter;
import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.dialog.ProgressDialog;
import com.liying.ipgw.model.RecentPost;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cc.duduhuo.applicationtoast.AppToast;

public class AppFAQActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, OnResponseListener<String> {
    private TextView tvBack;
    private ListView lvFaqs;
    private FaqListAdapter adapter;
    private ProgressDialog progressDialog;
    private TextView tvMsgNone;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final int REQUEST_WHAT = 0x0001;
    private static final String REQUEST_URL = "http://duduhuo.cc/ddh/ipgw/FAQ.json";
    /**
     * 黑板报列表
     */
    private List<RecentPost> faqList;
    /**
     * 请求队列
     */
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_faq);
        AccountApp.activityList.add(this);
        findView();
        setListener();
        initData();
    }
    private void findView() {
        tvBack = (TextView) findViewById(R.id.tvBack);
        lvFaqs = (ListView) findViewById(R.id.lvFaqs);
        tvMsgNone = (TextView) findViewById(R.id.tvMsgNone);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.holo_red_light,
                R.color.holo_orange_light,
                R.color.holo_green_light,
                R.color.holo_blue_bright
        );
        progressDialog = new ProgressDialog(this, "正在获取应用通知...");
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
        adapter = new FaqListAdapter(this);
        lvFaqs.setAdapter(adapter);
        sendRequest();
        progressDialog.show();
    }

    /**
     * 发送请求，获取json字符串
     */
    public void sendRequest() {
        requestQueue.cancelAll();   // 取消队列中的所有请求
        Request<String> request = NoHttp.createStringRequest(REQUEST_URL);
        requestQueue.add(REQUEST_WHAT, request, this);
    }

    @Override
    public void onStart(int what) {

    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        tvMsgNone.setVisibility(View.GONE);
        // 解析json字符串
        try {
            JSONObject jsonObject = new JSONObject(response.get());
            int status = jsonObject.getInt("status");
            if (status == 0) {
                faqList = new ArrayList<>(10);
                JSONArray postArr = jsonObject.getJSONArray("faqs");
                for (int i = 0; i < postArr.length(); i++) {
                    JSONObject json = (JSONObject) postArr.get(i);
                    String title = json.getString("title");
                    String content = json.getString("content");
                    String date = json.getString("date");
                    String url = json.getString("url");
                    faqList.add(new RecentPost(title, date, content, url));
                }
            } else {
                AppToast.showToast("暂时无法获取应用通知，请稍候重试。");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (faqList != null && faqList.size() != 0) {
            adapter.setData(faqList);
        } else {
            tvMsgNone.setVisibility(View.VISIBLE);
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
            tvErrorMsg.setText("请求失败，请重试。");
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
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        sendRequest();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AccountApp.activityList.remove(this);
        requestQueue.cancelAll();
    }
}
