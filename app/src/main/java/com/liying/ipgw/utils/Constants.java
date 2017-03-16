package com.liying.ipgw.utils;

import android.os.Environment;

import java.io.File;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2015/6/12
 * 版本：1.0
 * 描述：全局常量类
 * 备注：
 * =======================================================
 */
public interface Constants {
    String INSIDE_CHINA = "2";
    /** Activity传递的Intent数据的key */
    String PROGRAM = "program";
    /** Sharedpreferences的名字 */
    String PREFS_NAME = "spc";
    /** 存储彩蛋信息的Sharedpreferences的名字 */
    String EGG_PREFS_NAME = "egg";
    /** Sharedpreferences储存的版本号 */
    String VERSION = "VERSION_CODE";
    /** Sharedpreferences储存的默认帐号 */
    String DEFAULT_ACCOUNT = "DEFAULT_ACCOUNT";
    /** Sharedpreferences储存的临时语言 */
    String TEMP_LANGUAGE = "TEMP_LANGUAGE";
    /** Sharedpreferences储存的默认语言 */
    String DEFAULT_LANGUAGE = "DEFAULT_LANGUAGE";
    /** Sharedpreferences储存的连接IP地址 */
    String IP_ADDRESS = "IP_ADDRESS";
    /** Sharedpreferences储存的色彩主题 */
    String COLOR_THEME = "COLOR_THEME";
    /** Sharedpreferences储存的已接收到的推送的id */
    String PUSH_ID = "PUSH_ID";
    /** Sharedpreferences储存的彩蛋集合 */
    String EGG_SET = "eggset";
    /** Sharedpreferences储存的彩蛋收纳盒是否显示 */
    String EGG_BOX = "eggbox";

    String IPGW_DIR = "neuipgw";
    /**
     * 工作目录
     */
    String WORK_DIR = Environment.getExternalStorageDirectory() + File.separator + IPGW_DIR;
    /**
     * ResultCode：选择快递
     */
    int SHIPPER_RESULT = 1;
    /**
     * 友盟统计相关常量
     */
    interface UmengStatistics {
        /** 连接网络 */
        String CONNECT_CLICK = "connect_click";
        /** 断开网络 */
        String DISCONNECT_CLICK = "disconnect_click";
        /** 查询流量信息 */
        String QUERY_FLOW = "query_flow";
        /** 从浏览器登录 */
        String IPGW_BROSWER = "ipgw_broswer";
        /** 关于IPGW */
        String ABOUT_IPGW = "about_ipgw";
        /** 分享IPGW */
        String SHARE_IPGW = "share_ipgw";
        /** 桌面小工具 */
        String IPGW_TOOL = "ipgw_tool";
        /** 网址导航 */
        String NEU_NAVIGATOR = "neu_navigator";
        /** 用户反馈 */
        String USER_FEEDBACK = "user_feedback";
        /** 看电视 */
        String WATCH_TV = "watch_tv";
        /** 查看校历 */
        String SCHOOL_CALENDER = "school_calender";
        /** 关于作者 */
        String ABOUT_AUTHOR = "about_author";
        /** 色彩主题 */
        String COLOR_THEME = "color_theme";
        /** 检查更新 */
        String CHECK_UPDATE = "check_update";
        /** 彩蛋收纳盒 */
        String EGG_BOX = "egg_box";
        /** 最新网络中心黑板报 */
        String RECENT_POSTS = "recent_posts";
        /** 应用通知 */
        String APP_FAQ = "app_faq";
        /** 快递查询 */
        String EXPRESS_QUERY = "express_query";
        /** 彩蛋 */
        String EGG = "egg";
    }

}
