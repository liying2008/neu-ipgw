package com.liying.ipgw.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.activity.M3U8Player2;
import com.liying.ipgw.model.ReviewDate;
import com.liying.ipgw.model.ReviewProgram;
import com.liying.ipgw.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/6 0:18
 * 版本：1.0
 * 描述：节目回看列表适配器
 * 备注：
 * =======================================================
 */
public class NeuReviewListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private LayoutInflater listContainer;
    private String p;
    private List<ReviewDate> groupDates = new ArrayList<>(8);
    private List<List<ReviewProgram>> childPrograms = new ArrayList<>(20);

    public NeuReviewListAdapter(Context context) {
        this.context = context;
        this.listContainer = LayoutInflater.from(context);
    }

    public void setReviewData(List<ReviewDate> groupDates, List<List<ReviewProgram>> childPrograms, String p) {
        this.p = p;
        this.groupDates = groupDates;
        this.childPrograms = childPrograms;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return groupDates.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childPrograms.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupDates.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPrograms.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ExListItemView listItemView = null;
        if (convertView == null){
            listItemView = new ExListItemView();
            //获取list_item布局文件的视图
            convertView = listContainer.inflate(R.layout.item_neu_review_date, null);
            listItemView.tvReviewDate = (TextView) convertView.findViewById(R.id.tvReviewDate);

            //设置控件集到convertView
            convertView.setTag(listItemView);
        } else {
            listItemView = (ExListItemView) convertView.getTag();
        }

        listItemView.tvReviewDate.setText(groupDates.get(groupPosition).getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String string = childPrograms.get(groupPosition).get(childPosition).getName();
        ExListItemView listItemView = null;
        if (convertView == null){
            listItemView = new ExListItemView();
            //获取list_item布局文件的视图
            convertView = listContainer.inflate(R.layout.item_neu_review_program, null);
            listItemView.tvReviewProgram = (TextView) convertView.findViewById(R.id.tvReviewProgram);
            listItemView.llReviewProgram = (LinearLayout) convertView.findViewById(R.id.llReviewProgram);
            //设置控件集到convertView
            convertView.setTag(listItemView);
        } else {
            listItemView = (ExListItemView) convertView.getTag();
        }

        listItemView.tvReviewProgram.setText(string);

        final ReviewProgram program = childPrograms.get(groupPosition).get(childPosition);
        listItemView.llReviewProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, M3U8Player2.class);
                intent.putExtra(Constants.PROGRAM, new String[]{"2", program.getTimeStart(), program.getTimeEnd(), p});
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /** 自定义控件集合 */
    private static class ExListItemView{
        private TextView tvReviewDate;
        private TextView tvReviewProgram;
        private LinearLayout llReviewProgram;
    }
}
