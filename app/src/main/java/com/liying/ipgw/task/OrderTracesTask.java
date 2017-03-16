package com.liying.ipgw.task;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.liying.ipgw.model.Trace;
import com.liying.ipgw.utils.KdApiUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;


/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/22 14:13
 * 版本：1.0
 * 描述：快递即时查询任务类
 * 备注：
 * =======================================================
 */
public class OrderTracesTask extends AsyncTask<String, Void, List<Trace>> {
    private String exceptionMsg;
    private OrderTracesCallback callback;
    public OrderTracesTask(OrderTracesCallback callback) {
        this.callback = callback;
    }
    @Override
    protected List<Trace> doInBackground(String... params) {
        try {
            String result = KdApiUtils.kdniaoTrackQuery(params[0], params[1]);
            if (TextUtils.isEmpty(result)) {
                exceptionMsg = "无法获取数据，请检查网络环境。";
                return null;
            }
            JSONObject jsonObject = new JSONObject(result);
            boolean success = jsonObject.getBoolean("Success");
            if (success) {
                // 有物流轨迹
                JSONArray traces = jsonObject.getJSONArray("Traces");
                int length = traces.length();
                if (length == 0) {
                    // 无轨迹
                    exceptionMsg = "此单无物流信息。";
                } else {
                    List<Trace> traceList = new LinkedList<>();
                    for (int i = 0; i < length; i++) {
                        JSONObject trace = (JSONObject) traces.get(i);
                        String acceptTime = trace.getString("AcceptTime");
                        String acceptStation = trace.getString("AcceptStation");
//                        String remark = trace.getString("Remark");    // 可能没有此字段
                        traceList.add(0, new Trace(acceptTime, acceptStation, ""));
                    }
                    return traceList;
                }
            } else {
                // 没有物流轨迹的
                exceptionMsg = "没有物流轨迹。";
            }
        } catch (Exception e) {
            exceptionMsg = e.getMessage();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Trace> traceList) {
        if (callback != null) {
            if (traceList == null) {
                callback.tracesFailure(exceptionMsg);
            } else {
                callback.tracesResults(traceList);
            }
        }
    }

    /**
     * 快递即时查询回调接口
     */
    public interface OrderTracesCallback {
        /**
         * 追踪结果
         * @param traces
         */
        void tracesResults(List<Trace> traces);

        /**
         * 追踪失败
         * @param exceptionMsg
         */
        void tracesFailure(String exceptionMsg);
    }
}
