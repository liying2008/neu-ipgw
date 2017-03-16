package com.liying.ipgw.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.liying.ipgw.R;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/6 13:14
 * 版本：1.0
 * 描述：自定义进度Dialog
 * 备注：
 * =======================================================
 */
public class ProgressDialog extends android.app.ProgressDialog {
    private TextView tvLoadingMsg;
    private Context context;
    private String msg = "";
    public ProgressDialog(Context context, String msg) {
        super(context);
        this.context = context;
        this.msg = msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = View.inflate(context, R.layout.dialog_progress, null);
        tvLoadingMsg = (TextView) contentView.findViewById(R.id.tvLoadingMsg);
        tvLoadingMsg.setText(msg);
        setContentView(contentView);
    }
}
