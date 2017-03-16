package com.liying.ipgw.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.model.UpdateMsg;
import com.liying.ipgw.utils.ApkDownloader;

import cc.duduhuo.applicationtoast.AppToast;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/4 22:49
 * 版本：1.0
 * 描述：发现新版本对话框
 * 备注：
 * =======================================================
 */
public class NewVersionDialog extends Dialog {
    private Activity context;
    private UpdateMsg updateMsg;
    private Button btnCancel, btnUpdate;
    private String downloadUrl;

    public NewVersionDialog(Activity context, UpdateMsg updateMsg) {
        super(context);
        this.context = context;
        this.updateMsg = updateMsg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // 取消默认标题栏
        View contentView = View.inflate(context, R.layout.dialog_new_version, null);
        ((TextView) contentView.findViewById(R.id.tvVersionName)).setText(updateMsg.getVersionName());
        ((TextView) contentView.findViewById(R.id.tvSize)).setText(updateMsg.getSize());
        ((TextView) contentView.findViewById(R.id.tvUpdateDate)).setText(updateMsg.getUpdateDate());
        ((TextView) contentView.findViewById(R.id.tvUpdateLog)).setText(updateMsg.getUpdateLog());

        downloadUrl = updateMsg.getDownloadUrl();
        btnCancel = (Button) contentView.findViewById(R.id.btnCancel);
        btnUpdate = (Button) contentView.findViewById(R.id.btnUpdate);
        setContentView(contentView);
        setListener();
    }

    private void setListener() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                AppToast.showToast("正在下载新版本安装包。");
                ApkDownloader apkDownloader = new ApkDownloader();
                apkDownloader.downloadFile(context, downloadUrl);
            }
        });
    }
}
