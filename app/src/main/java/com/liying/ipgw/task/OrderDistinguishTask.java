package com.liying.ipgw.task;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.liying.ipgw.model.Shipper;
import com.liying.ipgw.utils.KdApiUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/22 14:13
 * 版本：1.0
 * 描述：快递单号识别任务类
 * 备注：
 * =======================================================
 */
public class OrderDistinguishTask extends AsyncTask<String, Void, List<Shipper>> {
    private String exceptionMsg;
    private OrderDistinguishCallback callback;
    public OrderDistinguishTask(OrderDistinguishCallback callback) {
        this.callback = callback;
    }
    @Override
    protected List<Shipper> doInBackground(String... params) {
        try {
            String result = KdApiUtils.orderDistinguish(params[0]);
            if (TextUtils.isEmpty(result)) {
                exceptionMsg = "无法获取数据，请检查网络环境。";
                return null;
            }
            JSONObject jsonObject = new JSONObject(result);
            boolean success = jsonObject.getBoolean("Success");
            if (success) {
                // 查询成功
                JSONArray shippers = jsonObject.getJSONArray("Shippers");
                int length = shippers.length();
                if (length == 0) {
                    exceptionMsg = "未能找到匹配的快递公司。";
                } else {
                    List<Shipper> shipperList = new ArrayList<>();
                    for (int i = 0; i < shippers.length(); i++) {
                        JSONObject shipper = (JSONObject) shippers.get(i);
                        shipperList.add(new Shipper(shipper.getString("ShipperName"), shipper.getString("ShipperCode")));
                    }
                    return shipperList;
                }
            } else {
                exceptionMsg = "单号未识别。";
            }
        } catch (Exception e) {
            exceptionMsg = e.getMessage();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Shipper> shipperList) {
        super.onPostExecute(shipperList);
        if (callback != null) {
            if (shipperList == null) {
                callback.distinguishFailure(exceptionMsg);
            } else {
                callback.distinguishResults(shipperList);
            }
        }
    }

    /**
     * 单号识别回调接口
     */
    public interface OrderDistinguishCallback {
        /**
         * 识别结果
         * @param shippers
         */
        void distinguishResults(List<Shipper> shippers);

        /**
         * 未能识别
         * @param exceptionMsg
         */
        void distinguishFailure(String exceptionMsg);
    }
}
