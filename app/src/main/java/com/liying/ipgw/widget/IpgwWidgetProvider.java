package com.liying.ipgw.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import com.liying.ipgw.utils.Constants;
import com.umeng.analytics.MobclickAgent;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/6 15:56
 * 版本：1.0
 * 描述：
 * 备注：
 * =======================================================
 */
public class IpgwWidgetProvider extends AppWidgetProvider {
    /**
     * 更新由这个提供程序创建的小部件时，这个方法会被调用
     * 通常情况下，以下情况会调用该方法：
     * 1、开始创建小部件
     * 2、达到AppWidgetProviderInfo中定义的updatePeriodMillis时间间隔
     * 3、AppWidgetManager中的updateAppWidget()方法被手动调用
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        // 友盟统计：桌面小工具
        MobclickAgent.onEvent(context, Constants.UmengStatistics.IPGW_TOOL);
        // 启动一个后台服务来更新此小部件
        context.startService(new Intent(context, IpgwService.class));
    }
}
