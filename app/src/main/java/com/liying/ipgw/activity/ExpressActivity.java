package com.liying.ipgw.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.adapter.TraceListAdapter;
import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.callback.OnExpressChange;
import com.liying.ipgw.dialog.ExpressListDialog;
import com.liying.ipgw.dialog.ProgressDialog;
import com.liying.ipgw.model.Express;
import com.liying.ipgw.model.Shipper;
import com.liying.ipgw.model.Trace;
import com.liying.ipgw.task.OrderDistinguishTask;
import com.liying.ipgw.task.OrderTracesTask;
import com.liying.ipgw.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import cc.duduhuo.applicationtoast.AppToast;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2017/3/15 23:14
 * 版本：1.0
 * 描述：快递查询Activity
 * 备注：
 * =======================================================
 */
public class ExpressActivity extends BaseActivity implements View.OnClickListener, OnExpressChange {
    private TextView tvBack;
    private EditText etLogisticCode;
    private TextView tvShipperName;
    private Button btnShipperName, btnLogisticCode;
    private Button btnSaveExpress, btnQuery;
    private Button btnDelCode;
    private ListView lvExpress;
    private ScrollView svTraceDetails;
    private TraceListAdapter adapter;
    private List<String> commonNames = new ArrayList<>(11);
    private List<String> commonNos = new ArrayList<>(10);
    private String currentShipperCode; // 当前选择的快递公司编号
    private ProgressDialog progressDialog;
    private OrderDistinguishTask mDistinguishTask;
    private OrderTracesTask mTracesTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express);
        AccountApp.activityList.add(this);
        findView();
        setListener();
        initData();
    }

    private void findView() {
        tvBack = (TextView) findViewById(R.id.tvBack);
        tvShipperName = (TextView) findViewById(R.id.tvShipperName);
        etLogisticCode = (EditText) findViewById(R.id.etLogisticCode);
        btnLogisticCode = (Button) findViewById(R.id.btnLogisticCode);
        btnShipperName = (Button) findViewById(R.id.btnShipperName);
        btnSaveExpress = (Button) findViewById(R.id.btnSaveExpress);
        btnQuery = (Button) findViewById(R.id.btnQuery);
        btnDelCode = (Button) findViewById(R.id.btnDelCode);
        lvExpress = (ListView) findViewById(R.id.lvExpress);
        svTraceDetails = (ScrollView) findViewById(R.id.svTraceDetails);
    }

    private void setListener() {
        tvBack.setOnClickListener(this);
        tvShipperName.setOnClickListener(this);
        btnShipperName.setOnClickListener(this);
        btnLogisticCode.setOnClickListener(this);
        btnSaveExpress.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
        btnDelCode.setOnClickListener(this);
        CodeTextWatcher codeTw = new CodeTextWatcher();
        etLogisticCode.addTextChangedListener(codeTw);    // 监听EditText内容变化
    }

    private void initData() {
        // 初始化常用快递
        commonNames.add("申通快递");
        commonNames.add("顺丰快递");
        commonNames.add("圆通速递");
        commonNames.add("韵达快递");
        commonNames.add("中通速递");
        commonNames.add("宅急送");
        commonNames.add("EMS");
        commonNames.add("天天快递");
        commonNames.add("百世快递");
        commonNames.add("优速快递");
        commonNames.add("更多...");

        commonNos.add("STO");
        commonNos.add("SF");
        commonNos.add("YTO");
        commonNos.add("YD");
        commonNos.add("ZTO");
        commonNos.add("ZJS");
        commonNos.add("EMS");
        commonNos.add("HHTT");
        commonNos.add("HTKY");
        commonNos.add("UC");

        adapter = new TraceListAdapter(this);
        lvExpress.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvBack:
                finish();
                break;
            case R.id.tvShipperName:
                // 识别快递公司
                final String code = etLogisticCode.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    AppToast.showToast("请先输入快递单号。");
                    return;
                }
                progressDialog = new ProgressDialog(this, "正在识别快递单号...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                // 匹配到的快递公司多于一家
                mDistinguishTask = new OrderDistinguishTask(new OrderDistinguishTask.OrderDistinguishCallback() {
                    @Override
                    public void distinguishResults(List<Shipper> shippers) {
                        progressDialog.dismiss();
                        if (shippers.size() == 1) {
                            Shipper shipper = shippers.get(0);
                            tvShipperName.setText(shipper.getName());
                            currentShipperCode = shipper.getCode();
                        } else {
                            // 匹配到的快递公司多于一家
                            final List<String> shipperNames = new ArrayList<>();
                            final List<String> shipperCodes = new ArrayList<>();

                            for (int i = 0; i < shippers.size(); i++) {
                                shipperNames.add(shippers.get(i).getName());
                                shipperCodes.add(shippers.get(i).getCode());
                            }
                            AlertDialog.Builder builder = AccountApp.getAlertDialogBuilder(ExpressActivity.this);
                            View dialogView = LayoutInflater.from(ExpressActivity.this)
                                    .inflate(R.layout.dialog_website_navigator, null);
                            builder.setView(dialogView);
                            final AlertDialog dialog = builder.create();
                            TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvTitle);
                            tvTitle.setText("请选择");
                            ListView lvCommonShipper = (ListView) dialogView.findViewById(R.id.lvWebsite);
                            lvCommonShipper.setAdapter(new ArrayAdapter<>(ExpressActivity.this, R.layout.item_simple_list, shipperNames));
                            lvCommonShipper.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    tvShipperName.setText(shipperNames.get(position));
                                    currentShipperCode = shipperCodes.get(position);
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();

                        }
                    }

                    @Override
                    public void distinguishFailure(String exceptionMsg) {
                        progressDialog.dismiss();
                        AppToast.showToast(exceptionMsg);
                    }
                });
                mDistinguishTask.execute(code);
                break;
            case R.id.btnShipperName:
                showCommonShipper();
                break;
            case R.id.btnLogisticCode:
                // 显示已保存的单号列表
                ExpressListDialog dialog = new ExpressListDialog(this, this);
                dialog.show();
                break;
            case R.id.btnSaveExpress:
                // 保存单号
                if (checkEmpty()) {
                    return;
                }
                saveExpress();
                break;
            case R.id.btnQuery:
                // 快递即时查询
                if (checkEmpty()) {
                    return;
                }
                progressDialog = new ProgressDialog(this, "正在查询物流信息...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                mTracesTask = new OrderTracesTask(new OrderTracesTask.OrderTracesCallback() {
                    @Override
                    public void tracesResults(List<Trace> traces) {
                        progressDialog.dismiss();
                        adapter.setData(traces);
                        svTraceDetails.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void tracesFailure(String exceptionMsg) {
                        progressDialog.dismiss();
                        svTraceDetails.setVisibility(View.GONE);
                        AppToast.showToast(exceptionMsg);
                    }
                });
                mTracesTask.execute(currentShipperCode, etLogisticCode.getText().toString());
                break;
            case R.id.btnDelCode:
                etLogisticCode.setText("");
                break;
            default:
                break;
        }
    }

    /**
     * 检查快递单号和快递公司是否为空
     * @return true：空
     */
    private boolean checkEmpty() {
        String logisticCode = etLogisticCode.getText().toString();
        String shipperName = tvShipperName.getText().toString();
        if (TextUtils.isEmpty(logisticCode)) {
            AppToast.showToast("快递单号为空。");
            return true;
        } else if (TextUtils.isEmpty(shipperName)) {
            AppToast.showToast("快递公司为空。");
            return true;
        }
        return false;
    }

    /**
     * 保存单号
     */
    private void saveExpress() {
        // 检查单号是否已经保存
        final String expressCode = etLogisticCode.getText().toString();
        boolean exist = AccountApp.eServices.isExpressExist(expressCode, currentShipperCode);
        if (exist) {
            AppToast.showToast("该快递单号已经保存过了。");
            return;
        }
        AlertDialog.Builder builder = AccountApp.getAlertDialogBuilder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_save_express, null);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        Button btnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
        Button btnSave = (Button) dialogView.findViewById(R.id.btnSave);
        final EditText etMark = (EditText) dialogView.findViewById(R.id.etMark);
        etMark.requestFocus();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 保存单号信息
                String shipperName = tvShipperName.getText().toString();
                String mark = etMark.getText().toString();
                if ("".equals(mark)) {
                    mark = "— 无备注 —";
                }
                AccountApp.eServices.insertExpress(new Express(expressCode, shipperName, currentShipperCode, mark));
                AppToast.showToast("快递单号已保存。");
                dialog.dismiss();
            }
        });
        // 弹出输入法面板
        // FIXME: 2016/11/23 无效
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    /**
     * 常用快递
     */
    private void showCommonShipper() {
        AlertDialog.Builder builder = AccountApp.getAlertDialogBuilder(this);
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_website_navigator, null);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvTitle);
        tvTitle.setText("常用快递");
        ListView lvCommonShipper = (ListView) dialogView.findViewById(R.id.lvWebsite);
        lvCommonShipper.setAdapter(new ArrayAdapter<>(this, R.layout.item_simple_list, commonNames));
        lvCommonShipper.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 10) {
                    tvShipperName.setText(commonNames.get(position));
                    currentShipperCode = commonNos.get(position);
                } else {
                    Intent intent = new Intent(ExpressActivity.this, ShipperListActivity.class);
                    startActivityForResult(intent, 0);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SHIPPER_RESULT) {
            Shipper shipper = (Shipper) data.getSerializableExtra("shipper");
            tvShipperName.setText(shipper.getName());
            currentShipperCode = shipper.getCode();
        }
    }

    @Override
    public void onNewExpress(Express express) {
        etLogisticCode.setText(express.getExpressCode());
        etLogisticCode.setSelection(express.getExpressCode().length());
        tvShipperName.setText(express.getShipperName());
        currentShipperCode = express.getShipperCode();
    }

    private class CodeTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(s.toString())) {
                // 编辑框为空
                btnDelCode.setVisibility(View.GONE);
            } else {
                // 编辑框不空
                btnDelCode.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AccountApp.activityList.remove(this);
        if (mDistinguishTask != null && mDistinguishTask.getStatus() != AsyncTask.Status.FINISHED) {
            mDistinguishTask.cancel(true);
        }
        if (mTracesTask != null && mTracesTask.getStatus() != AsyncTask.Status.FINISHED) {
            mTracesTask.cancel(true);
        }
    }

}
