package com.liying.ipgw.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.application.AccountApp;
import com.liying.ipgw.model.RecentPost;
import com.liying.ipgw.utils.DensityUtil;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

import cc.duduhuo.applicationtoast.AppToast;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/20 14:21
 * 版本：1.0
 * 描述：最新网络中心黑板报列表适配器
 * 备注：
 * =======================================================
 */
public class RecentPostsAdapter extends BaseAdapter {
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_MIDDLE = 0x0001;
    private static final int TYPE_BOTTOM = 0x0002;
    private List<RecentPost> postList = new ArrayList<>(10);
    private Activity context;
    private LayoutInflater inflater;

    public RecentPostsAdapter(Activity context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(List<RecentPost> postList) {
        if (postList != null) {
            this.postList = postList;
        }
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public RecentPost getItem(int position) {
        return postList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final RecentPost post = getItem(position);
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_recent_posts, parent, false);
            holder.llItem = (LinearLayout) convertView.findViewById(R.id.llItem);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.tvContent = (HtmlTextView) convertView.findViewById(R.id.tvContent);
            convertView.setTag(holder);
        }

        if (getItemViewType(position) == TYPE_TOP) {
            // 让第一行距顶部有一定的距离
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(DensityUtil.dip2px(context, 16),
                    DensityUtil.dip2px(context, 10), DensityUtil.dip2px(context, 16), 0);
            holder.llItem.setLayoutParams(lp);
        } else if (getItemViewType(position) == TYPE_BOTTOM) {
            // 让最后一行距底部有一定的距离
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(DensityUtil.dip2px(context, 16),
                    0, DensityUtil.dip2px(context, 16), DensityUtil.dip2px(context, 10));
            holder.llItem.setLayoutParams(lp);
        } else {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(DensityUtil.dip2px(context, 16),
                    0, DensityUtil.dip2px(context, 16), 0);
            holder.llItem.setLayoutParams(lp);
        }

        holder.tvTitle.setText(post.getTitle());
        String dateMsg = "发布时间：" + post.getDate();
        holder.tvDate.setText(dateMsg);
        holder.tvContent.setHtml(post.getContent());
        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击条目
                AlertDialog.Builder builder = AccountApp.getAlertDialogBuilder(context);
                View view = inflater.inflate(R.layout.dialog_view_detail, null);
                TextView tvDetailUrl = (TextView) view.findViewById(R.id.tvDetailUrl);
                Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
                Button btnOpen = (Button) view.findViewById(R.id.btnOpen);
                tvDetailUrl.setText(post.getUrl());
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnOpen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 跳转到网页详情
                        Uri uri = Uri.parse(post.getUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(uri);
                        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
                        if (componentName != null) {
                            context.startActivity(intent);
                        } else {
                            AppToast.showToast("您没有安装浏览器。");
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
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
        private TextView tvTitle;
        private TextView tvDate;
        private HtmlTextView tvContent;
        private LinearLayout llItem;
    }

}
