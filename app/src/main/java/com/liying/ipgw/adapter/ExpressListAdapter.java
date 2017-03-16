package com.liying.ipgw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.dialog.ExpressListDialog;
import com.liying.ipgw.model.Express;

import java.util.List;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/23 14:08
 * 版本：1.0
 * 描述：保存的快递单信息列表适配器
 * 备注：
 * =======================================================
 */
public class ExpressListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    /**
     * 快递单列表数组
     */
    private List<Express> expresses;

    public ExpressListAdapter(Context context, List<Express> expresses) {
        this.inflater = LayoutInflater.from(context);
        this.expresses = expresses;
    }

    public void setData(List<Express> expresses) {
        this.expresses = expresses;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return expresses.size();
    }

    @Override
    public Object getItem(int position) {
        return expresses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Express express = expresses.get(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            //获取list_item布局文件的视图
            convertView = inflater.inflate(R.layout.item_express, null);
            holder.tvMark = (TextView) convertView.findViewById(R.id.tvMark);
            holder.tvExpressInfo = (TextView) convertView.findViewById(R.id.tvExpressInfo);
            holder.ivDelExpress = (ImageView) convertView.findViewById(R.id.ivDelExpress);
            //设置控件集到convertView
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvMark.setText(express.getMark());
        String expressInfo = express.getExpressCode() + "（"+express.getShipperName()+"）";
        holder.tvExpressInfo.setText(expressInfo);
        holder.ivDelExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpressListDialog dialog = ExpressListDialog.getInstance();
                if (dialog != null) {
                    dialog.delExpress(position);
                }
            }
        });
        return convertView;
    }

    /**
     * 自定义控件集合
     */
    private static class ViewHolder {
        private TextView tvMark, tvExpressInfo;
        private ImageView ivDelExpress;
    }
}
