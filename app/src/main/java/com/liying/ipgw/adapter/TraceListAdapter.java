package com.liying.ipgw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.model.Trace;

import java.util.ArrayList;
import java.util.List;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/22 20:53
 * 版本：1.0
 * 描述：物流信息列表适配器
 * 备注：
 * =======================================================
 */
public class TraceListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Trace> traceList = new ArrayList<>(1);
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_MIDDLE = 0x0001;
    private static final int TYPE_BOTTOM = 0x0002;

    public TraceListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(List<Trace> traceList) {
        this.traceList = traceList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return traceList.size();
    }

    @Override
    public Trace getItem(int position) {
        return traceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final Trace trace = getItem(position);
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_trace, parent, false);
            holder.tvAcceptTime = (TextView) convertView.findViewById(R.id.tvAcceptTime);
            holder.tvAcceptStation = (TextView) convertView.findViewById(R.id.tvAcceptStation);
            holder.tvTopLine = (TextView) convertView.findViewById(R.id.tvTopLine);
            holder.tvDot = (TextView) convertView.findViewById(R.id.tvDot);
            convertView.setTag(holder);
        }

        if (getItemViewType(position) == TYPE_TOP) {
            // 第一行头的竖线不显示
            holder.tvTopLine.setVisibility(View.INVISIBLE);
            // 字体颜色加深
            holder.tvAcceptTime.setTextColor(0xff555555);
            holder.tvAcceptStation.setTextColor(0xff555555);
            holder.tvDot.setBackgroundResource(R.drawable.timelline_dot_first);
        } else if (getItemViewType(position) == TYPE_BOTTOM) {
            holder.tvTopLine.setVisibility(View.VISIBLE);
            holder.tvAcceptTime.setTextColor(0xff999999);
            holder.tvAcceptStation.setTextColor(0xff999999);
            holder.tvDot.setBackgroundResource(R.drawable.timelline_dot_normal);
        } else {
            holder.tvTopLine.setVisibility(View.VISIBLE);
            holder.tvAcceptTime.setTextColor(0xff999999);
            holder.tvAcceptStation.setTextColor(0xff999999);
            holder.tvDot.setBackgroundResource(R.drawable.timelline_dot_normal);
        }

        holder.tvAcceptTime.setText(trace.getAcceptTime());
        holder.tvAcceptStation.setText(trace.getAcceptStation());
        return convertView;
    }

    @Override
    public int getItemViewType(int id) {
        if (id == 0) {
            return TYPE_TOP;
        } else if (id == getCount() - 1) {
            return TYPE_BOTTOM;
        }
        return TYPE_MIDDLE;
    }

    private static class ViewHolder {
        private TextView tvAcceptTime, tvAcceptStation;
        private TextView tvTopLine, tvDot;
    }
}
