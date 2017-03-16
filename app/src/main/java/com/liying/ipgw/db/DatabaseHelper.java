package com.liying.ipgw.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2015/12/06 12:31
 * 版本：1.0
 * 描述：自定义数据库Helper
 * 备注：
 * =======================================================
 */
class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, DBInfo.DB_NAME, null, DBInfo.DB_VERSION);
    }

    /**
     * 数据库第一次创建时候调用
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库表
        db.execSQL(DBInfo.Table.ACCOUNT_TB_CREATE);
        db.execSQL(DBInfo.Table.EXPRESS_TB_CREATE);
    }

    /**
     * 数据库文件版本号发生变化时调用
     *
     * @param db         数据库对象
     * @param oldVersion 旧版本号
     * @param newVersion 新版本号
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1) {
            updateTo2(db);
            updateTo3(db);
        } else if (oldVersion == 2) {
            updateTo3(db);
        }

    }

    private void updateTo2(SQLiteDatabase db) {
        String update1 = "CREATE TABLE temp1 AS SELECT userName, psw, range FROM " + DBInfo.Table.ACCOUNT_TB_NAME;
        String update2 = "DROP TABLE IF EXISTS " + DBInfo.Table.ACCOUNT_TB_NAME;
        String update3 = "ALTER TABLE temp1 RENAME TO " + DBInfo.Table.ACCOUNT_TB_NAME;

        db.execSQL(update1);
        db.execSQL(update2);
        db.execSQL(update3);
    }

    private void updateTo3(SQLiteDatabase db) {
        //创建新数据库表Express
        db.execSQL(DBInfo.Table.EXPRESS_TB_CREATE);
    }
}
