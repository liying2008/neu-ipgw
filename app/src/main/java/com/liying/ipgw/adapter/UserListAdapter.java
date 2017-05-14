package com.liying.ipgw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liying.ipgw.R;
import com.liying.ipgw.dialog.UserListDialog;
import com.liying.ipgw.model.AccountInfo;

import java.util.List;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2015/12/6 21:46
 * 版本：1.0
 * 描述：用户帐户列表适配器
 * 备注：
 * =======================================================
 */
public class UserListAdapter extends BaseAdapter {
    private LayoutInflater listContainer;
    /** 用户列表数组 */
    private List<AccountInfo> users;

    public UserListAdapter(Context context, List<AccountInfo> users) {
        listContainer = LayoutInflater.from(context);
        this.users = users;
    }

    public void setData(List<AccountInfo> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ListItemView listItemView;
        if (convertView == null) {
            listItemView = new ListItemView();
            //获取list_item布局文件的视图
            convertView = listContainer.inflate(R.layout.item_userlist, null);
            listItemView.tvUser = (TextView) convertView.findViewById(R.id.tvUser);
            listItemView.ivDelUser = (ImageView) convertView.findViewById(R.id.ivDelUser);
            //设置控件集到convertView
            convertView.setTag(listItemView);
        } else {
            listItemView = (ListItemView) convertView.getTag();
        }

        listItemView.tvUser.setText(users.get(position).getUserName());
        listItemView.ivDelUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserListDialog dialog = UserListDialog.getInstance();
                if (dialog != null) {
                    dialog.delUser(position);
                }
            }
        });
        return convertView;
    }

    /**
     * 自定义控件集合
     */
    private static class ListItemView {
        private TextView tvUser;
        private ImageView ivDelUser;
    }
}
