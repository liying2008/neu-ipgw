package com.liying.ipgw.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liying.ipgw.model.Express;

import java.util.ArrayList;
import java.util.List;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2015/12/06 12:34
 * 版本：1.0
 * 描述：快递单信息存取操作
 * 备注：
 * =======================================================
 */
public class ExpressServices extends BaseDbService {

    private DatabaseHelper dbHelper;
    private String[] columns = {Express.TIME, Express.EXPRESS_CODE, Express.SHIPPER_NAME, Express.SHIPPER_CODE, Express.MARK};

    public ExpressServices(Context context) {
        super(context);
        dbHelper = super.dbHelper;
    }


    /**
     * 添加快递单信息到数据库
     *
     * @param express 快递单号信息
     */
    public void insertExpress(Express express) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues(5);
        values.put(columns[0], System.currentTimeMillis());
        values.put(columns[1], express.getExpressCode());
        values.put(columns[2], express.getShipperName());
        values.put(columns[3], express.getShipperCode());
        values.put(columns[4], express.getMark());
        db.insert(DBInfo.Table.EXPRESS_TB_NAME, null, values);
        db.close();        //关闭数据库
    }

    /**
     * 删除指定的快递单信息
     *
     * @param time 时间戳
     */
    public void delExpressByTime(long time) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        String sql = "DELETE FROM " + DBInfo.Table.EXPRESS_TB_NAME + " WHERE " + columns[0] + " = " + time;
        db.execSQL(sql);
        db.close();
    }

    /**
     * 查询所有快递单信息
     *
     * @return 所有快递单信息List
     */
    public List<Express> getAllExpress() {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        List<Express> expresses = null;
        Cursor cursor = db.query(DBInfo.Table.EXPRESS_TB_NAME, columns, null, null, null, null, null);
        if (null != cursor) {
            if (cursor.getCount() > 0) {
                expresses = new ArrayList<>(cursor.getCount());
                Express express = null;
                while (cursor.moveToNext()) {
                    express = new Express();
                    Long _time = cursor.getLong(cursor.getColumnIndex(columns[0]));
                    String _expressCode = cursor.getString(cursor.getColumnIndex(columns[1]));
                    String _shipperName = cursor.getString(cursor.getColumnIndex(columns[2]));
                    String _shipperCode = cursor.getString(cursor.getColumnIndex(columns[3]));
                    String _mark = cursor.getString(cursor.getColumnIndex(columns[4]));

                    express.setTime(_time);
                    express.setExpressCode(_expressCode);
                    express.setShipperName(_shipperName);
                    express.setShipperCode(_shipperCode);
                    express.setMark(_mark);

                    expresses.add(express);
                }
            }
            cursor.close();
        }
        db.close();
        return expresses;
    }

    /**
     * 快递单号是否已经存在于数据库中
     *
     * @param expressCode 快递单号
     * @param shipperCode 快递公司编码
     * @return true：已经存在
     */
    public boolean isExpressExist(String expressCode, String shipperCode) {
        boolean flag = false;
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBInfo.Table.EXPRESS_TB_NAME, columns,
                Express.EXPRESS_CODE + "=? and " + Express.SHIPPER_CODE + "=?",
                new String[]{expressCode, shipperCode}, null, null, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                flag = true;
            }
            cursor.close();
        }
        db.close();
        return flag;
    }
}
