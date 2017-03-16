package com.liying.ipgw.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2015/9/2 19:32
 * 版本：1.0
 * 描述：可以嵌套在ScrollView中的ListView
 * 备注：
 * =======================================================
 */
public class ListViewForScrollView extends ListView {
    public ListViewForScrollView(Context context) {
        super(context);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
