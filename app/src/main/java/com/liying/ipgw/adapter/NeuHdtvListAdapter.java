package com.liying.ipgw.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.activity.HdtvReviewActivity;
import com.liying.ipgw.activity.M3U8Player2;
import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.model.Program;
import com.liying.ipgw.utils.ColorThemeUtils;
import com.liying.ipgw.utils.Constants;
import com.liying.ipgw.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/5 22:13
 * 版本：1.0
 * 描述：节目列表适配器
 * 备注：
 * =======================================================
 */
public class NeuHdtvListAdapter extends BaseAdapter {
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_MIDDLE = 0x0001;
    private static final int TYPE_BOTTOM = 0x0002;
    /**
     * 节目列表集合
     */
    private List<Program> programsList = new ArrayList<>(190);
    private Context context;
    private LayoutInflater inflater;
    private int[] drawables;

    public NeuHdtvListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        drawables = ColorThemeUtils.getDrawablesByName(AccountApp.colorThemeName);
    }

    public void setData(List<Program> programs) {
        this.programsList.clear();
        if (programs != null) {
            this.programsList.addAll(programs);
        }
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return programsList.size();
    }

    @Override
    public Program getItem(int position) {
        return programsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Program programItem = getItem(position);
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_neu_hdtv, parent, false);
            convertView.setTag(viewHolder);
            viewHolder.flReview = (FrameLayout) convertView.findViewById(R.id.flReview);
            viewHolder.tvNumber = (TextView) convertView.findViewById(R.id.tvNumber);
            viewHolder.tvProgram = (TextView) convertView.findViewById(R.id.tvProgram);
            viewHolder.flNumber = (FrameLayout) convertView.findViewById(R.id.flNumber);
            viewHolder.llProgram = (LinearLayout) convertView.findViewById(R.id.llProgram);
            viewHolder.llItem = (LinearLayout) convertView.findViewById(R.id.llItem);

            viewHolder.llProgram.setOnClickListener(viewHolder);
            viewHolder.flReview.setOnClickListener(viewHolder);
        }

        if (getItemViewType(position) == TYPE_TOP) {
            viewHolder.flNumber.setBackgroundResource(drawables[0]);
            viewHolder.flReview.setBackgroundResource(drawables[3]);
            // 让第一行距顶部有一定的距离
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    DensityUtil.dip2px(context, 48));
            lp.setMargins(DensityUtil.dip2px(context, 16),
                    DensityUtil.dip2px(context, 10), DensityUtil.dip2px(context, 16), 0);
            viewHolder.llItem.setLayoutParams(lp);
        } else if (getItemViewType(position) == TYPE_BOTTOM) {
            viewHolder.flNumber.setBackgroundResource(drawables[2]);
            viewHolder.flReview.setBackgroundResource(drawables[5]);
            // 让最后一行距底部有一定的距离
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    DensityUtil.dip2px(context, 48));
            lp.setMargins(DensityUtil.dip2px(context, 16),
                    0, DensityUtil.dip2px(context, 16), DensityUtil.dip2px(context, 10));
            viewHolder.llItem.setLayoutParams(lp);
        } else {
            viewHolder.flNumber.setBackgroundResource(drawables[1]);
            viewHolder.flReview.setBackgroundResource(drawables[4]);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    DensityUtil.dip2px(context, 48));
            lp.setMargins(DensityUtil.dip2px(context, 16),
                    0, DensityUtil.dip2px(context, 16), 0);
            viewHolder.llItem.setLayoutParams(lp);
        }
        viewHolder.bindView(programItem);
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

    private class ViewHolder implements View.OnClickListener {
        private TextView tvNumber;
        private TextView tvProgram;
        private LinearLayout llProgram;
        private FrameLayout flReview;
        private FrameLayout flNumber;
        private LinearLayout llItem;

        private Program programItem;

        void bindView(Program programItem) {
            this.programItem = programItem;
            tvNumber.setText(String.valueOf(programItem.getId() + 1));
            tvProgram.setText(programItem.getName());
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.llProgram) {
                // 进入播放界面
                Intent intent = new Intent(context, M3U8Player2.class);
                intent.putExtra(Constants.PROGRAM, new String[]{"1", programItem.getP()});
                context.startActivity(intent);
            } else if (id == R.id.flReview) {
                // 进入回看列表选择
                Intent intent = new Intent(context, HdtvReviewActivity.class);
                intent.putExtra(Constants.PROGRAM, new String[]{programItem.getName(), programItem.getP()});
                context.startActivity(intent);
            }
        }
    }
}
