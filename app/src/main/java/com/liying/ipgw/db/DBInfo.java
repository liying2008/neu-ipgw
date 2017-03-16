package com.liying.ipgw.db;

import com.liying.ipgw.model.AccountInfo;
import com.liying.ipgw.model.Express;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2015/12/06 12:29
 * 版本：1.0
 * 描述：数据库常量信息
 * 备注：
 * =======================================================
 */
class DBInfo {
    /**
     * 数据库名称
     */
    public static final String DB_NAME = "AutoConnect.db";
    /**
     * 数据库版本
     * version 1: 初始版本
     * version 2: 加入range字段
     * version 3: 新增Express表
     *
     */
    public static final int DB_VERSION = 3;

    /**
     * 数据表
     *
     * @author 李颖
     */
    public static class Table {
        public static final String ACCOUNT_TB_NAME = "UserAccount";
        public static final String EXPRESS_TB_NAME = "Express";
        public static final String ACCOUNT_TB_CREATE = "create table if not exists " + ACCOUNT_TB_NAME + " (" +
                AccountInfo.USERNAME + " varchar(20), " +
                AccountInfo.PSW + " varchar(30), " +
                AccountInfo.RANGE + " varchar(1))";
        public static final String EXPRESS_TB_CREATE = "create table if not exists " + EXPRESS_TB_NAME + " (" +
                Express.TIME + " LONG PRIMARY KEY NOT NULL," +
                Express.EXPRESS_CODE + " TEXT NOT NULL," +
                Express.SHIPPER_NAME + " TEXT, " +
                Express.SHIPPER_CODE + " TEXT, " +
                Express.MARK + "  TEXT);";
    }

}
