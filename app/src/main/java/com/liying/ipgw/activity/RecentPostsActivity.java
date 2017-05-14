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
import com.liying.ipgw.adapter.RecentPostsAdapter;
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

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2017/3/15 23:20
 * 版本：1.0
 * 描述：最新网络中心黑板报
 * 备注：经常请求速度过慢，原因不明
 * =======================================================
 */
public class RecentPostsActivity extends BaseActivity implements OnResponseListener<String>, SwipeRefreshLayout.OnRefreshListener {
    private TextView tvBack;
    private ListView lvPosts;
    private RecentPostsAdapter adapter;
    private ProgressDialog progressDialog;
    private TextView tvMsgNone;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final int REQUEST_WHAT = 0x0001;
    private static final String REQUEST_URL = "http://network.neu.edu.cn/api/get_recent_posts/";
    /** 黑板报列表 */
    private List<RecentPost> postList;
    /** 请求队列 */
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_posts);
        AccountApp.activityList.add(this);
        findView();
        setListener();
        initData();
    }

    private void findView() {
        tvBack = (TextView) findViewById(R.id.tvBack);
        lvPosts = (ListView) findViewById(R.id.lvPosts);
        tvMsgNone = (TextView) findViewById(R.id.tvMsgNone);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.holo_red_light,
                R.color.holo_orange_light,
                R.color.holo_green_light,
                R.color.holo_blue_bright
        );
        progressDialog = new ProgressDialog(this, "正在获取网络中心黑板报...");
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
        adapter = new RecentPostsAdapter(this);
        lvPosts.setAdapter(adapter);
        sendRequest();
        progressDialog.show();
    }

    /**
     * 发送请求，获取json字符串
     */
    public void sendRequest() {
        requestQueue.cancelAll();   // 取消队列中的所有请求
        Request<String> request = NoHttp.createStringRequest(REQUEST_URL);
        request.setConnectTimeout(20000);
        request.setReadTimeout(20000);
        request.setContentType("application/json; charset=UTF-8");
        request.setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
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
            String status = jsonObject.getString("status");
            if ("ok".equals(status)) {
                postList = new ArrayList<>(10);
                JSONArray postArr = jsonObject.getJSONArray("posts");
                for (int i = 0; i < postArr.length(); i++) {
                    JSONObject json = (JSONObject) postArr.get(i);
                    String title = json.getString("title");
                    String content = json.getString("content");
                    String date = json.getString("date");
                    String url = json.getString("url");
                    postList.add(new RecentPost(title, date, content, url));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (postList != null && postList.size() != 0) {
            adapter.setData(postList);
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
