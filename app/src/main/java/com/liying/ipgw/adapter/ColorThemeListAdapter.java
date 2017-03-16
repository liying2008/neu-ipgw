package com.liying.ipgw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.model.ColorTheme;
import com.liying.ipgw.utils.DensityUtil;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/4 19:58
 * 版本：1.0
 * 描述：色彩主题列表适配器
 * 备注：
 * =======================================================
 */
public class ColorThemeListAdapter extends RecyclerView.Adapter<ColorThemeListAdapter.ViewHolder> {
    private Context context;
    private ColorTheme[] colorThemes;
    private static final int TYPE_LEFT_TOP = 0x0000;
    private static final int TYPE_LEFT_BOTTOM = 0x0001;
    private static final int TYPE_RIGHT_TOP = 0x0002;
    private static final int TYPE_RIGHT_BOTTOM = 0x0003;
    private static final int TYPE_MIDDLE = 0x0004;

    public ColorThemeListAdapter(Context context, ColorTheme[] colorThemes) {
        this.context = context;
        this.colorThemes = colorThemes;
    }
    public void setData(ColorTheme[] colorThemes) {
        this.colorThemes = colorThemes;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_color_theme, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ColorTheme colorTheme = colorThemes[position];
        int type = getItemViewType(colorTheme.getPosition());
        if (type == TYPE_LEFT_TOP) {
            holder.llItem.setBackgroundResource(R.drawable.selector_grid_left_top);
            // 让第一行距顶部有一定的距离
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, DensityUtil.dip2px(context, 10), 0, 0);
            holder.llItem.setLayoutParams(lp);
        } else if (type == TYPE_LEFT_BOTTOM) {
            holder.llItem.setBackgroundResource(R.drawable.selector_grid_left_bottom);
            // 让最后一行距底部有一定的距离
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 0, DensityUtil.dip2px(context, 10));
            holder.llItem.setLayoutParams(lp);
        } else if (type == TYPE_RIGHT_TOP) {
            holder.llItem.setBackgroundResource(R.drawable.selector_grid_right_top);
            // 让第一行距顶部有一定的距离
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, DensityUtil.dip2px(context, 10), 0, 0);
            holder.llItem.setLayoutParams(lp);
        } else if (type == TYPE_RIGHT_BOTTOM) {
            holder.llItem.setBackgroundResource(R.drawable.selector_grid_right_bottom);
            // 让最后一行距底部有一定的距离
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 0, DensityUtil.dip2px(context, 10));
            holder.llItem.setLayoutParams(lp);
        } else {
            holder.llItem.setBackgroundResource(R.drawable.selector_grid_middle);
            // 还原
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 0, 0);
            holder.llItem.setLayoutParams(lp);
        }

        holder.tvThemeName.setText(colorTheme.getThemeName());
        holder.ivThemePreview.setBackgroundResource(colorTheme.getThemePreId());
        if (colorTheme.isSelected()) {
            holder.ivSelected.setVisibility(View.VISIBLE);
        } else {
            holder.ivSelected.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return colorThemes.length;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_LEFT_TOP;
            case 1:
                return TYPE_RIGHT_TOP;
            case 6:
                return TYPE_LEFT_BOTTOM;
            case 7:
                return TYPE_RIGHT_BOTTOM;
            default:
                return TYPE_MIDDLE;

        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llItem;
        private ImageView ivThemePreview, ivSelected;
        private TextView tvThemeName;

        ViewHolder(View itemView) {
            super(itemView);
            llItem = (LinearLayout) itemView.findViewById(R.id.llItem);
            ivThemePreview = (ImageView) itemView.findViewById(R.id.ivThemePreview);
            ivSelected = (ImageView) itemView.findViewById(R.id.ivSelected);
            tvThemeName = (TextView) itemView.findViewById(R.id.tvThemeName);
        }
    }
}
