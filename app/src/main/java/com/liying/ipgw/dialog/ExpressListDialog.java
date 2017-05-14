package com.liying.ipgw.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.adapter.ExpressListAdapter;
import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.callback.OnExpressChange;
import com.liying.ipgw.model.Express;

import java.lang.ref.WeakReference;
import java.util.List;

import cc.duduhuo.applicationtoast.AppToast;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/23 14:04
 * 版本：1.0
 * 描述：快递单号列表对话框
 * 备注：
 * =======================================================
 */
public class ExpressListDialog extends Dialog {
    private Activity mContext;
    private ListView lvExpress;
    private TextView tvExpressNone;
    private ExpressListAdapter adapter;
    /** 快递单信息列表 */
    private List<Express> expresses;
    private OnExpressChange callback;
    private static WeakReference<ExpressListDialog> dialog;

    public ExpressListDialog(Activity context, OnExpressChange callback) {
        super(context);
        mContext = context;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new WeakReference<ExpressListDialog>(this);
        initView();
        initData();
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_express);
        lvExpress = (ListView) findViewById(R.id.lvExpress);
        tvExpressNone = (TextView) findViewById(R.id.tvExpressNone);
    }

    private void initData() {
        // 获取所有保存的快递单号列表
        expresses = getAllExpress();
        System.out.println(expresses);
        if (null == expresses || expresses.size() == 0) {
            // 没有快递单号
            tvExpressNone.setVisibility(View.VISIBLE);
            lvExpress.setVisibility(View.GONE);
            return;
        }
        adapter = new ExpressListAdapter(mContext, expresses);
        lvExpress.setAdapter(adapter);
        lvExpress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                if (callback != null) {
                    callback.onNewExpress(expresses.get(position));
                }
            }
        });
    }

    public static ExpressListDialog getInstance() {
        return dialog.get();
    }
    /**
     * 删除一个快递信息
     * @param position
     */
    public void delExpress(int position) {
        long time = expresses.get(position).getTime();
        AccountApp.eServices.delExpressByTime(time);
        AppToast.showToast("快递单号已删除");
        expresses = getAllExpress();
        if (null != expresses && expresses.size() > 0){
            adapter.setData(expresses);
        } else {
            // 关闭对话框
            dismiss();
        }
    }

    /**
     * 获取所有已保存的快递信息
     *
     * @return 已保存的快递信息列表
     */
    private List<Express> getAllExpress() {
        return AccountApp.eServices.getAllExpress();
    }
}
