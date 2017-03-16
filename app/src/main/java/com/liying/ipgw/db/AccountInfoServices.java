package com.liying.ipgw.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liying.ipgw.model.AccountInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2015/12/06 12:34
 * 版本：1.0
 * 描述：数据库操作层
 * 备注：
 * =======================================================
 */
public class AccountInfoServices extends BaseDbService {

    private DatabaseHelper dbHelper;
    private String[] columns = {AccountInfo.USERNAME, AccountInfo.PSW, AccountInfo.RANGE};

    public AccountInfoServices(Context context) {
        super(context);
        dbHelper = super.dbHelper;
    }


    /**
     * 添加用户账号到数据库
     *
     * @param account 帐号信息
     */
    public void insertAccountInfo(AccountInfo account) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues(3);
        values.put(columns[0], account.getUserName());
        values.put(columns[1], account.getPsw());
        values.put(columns[2], account.getRange());
        db.insert(DBInfo.Table.ACCOUNT_TB_NAME, null, values);
        db.close();        //关闭数据库
    }

    /**
     * 删除指定userName的账户
     */
    public void deleteAccountByUserName(String userName) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        String sql = "DELETE FROM " + DBInfo.Table.ACCOUNT_TB_NAME + " WHERE " + columns[0] + " = '" + userName + "'";
        db.execSQL(sql);
        db.close();
    }

    /**
     * 删除数据表中的全部数据
     */
    public void deleteAccountInfo() {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        db.execSQL("delete from " + DBInfo.Table.ACCOUNT_TB_NAME);
        db.close();
    }

    /**
     * 根据账号(userName)获取账号信息
     *
     * @param userName 序号
     * @return 账号信息，没有找到返回null
     */
    public AccountInfo getAccountInfoByUserName(String userName) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBInfo.Table.ACCOUNT_TB_NAME, columns,
                AccountInfo.USERNAME + "=?", new String[]{userName}, null, null, null);

        AccountInfo accountInfo = null;
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() > 0){
                accountInfo = new AccountInfo();

                String _psw = cursor.getString(cursor.getColumnIndex(AccountInfo.PSW));
                String _range = cursor.getString(cursor.getColumnIndex(AccountInfo.RANGE));

                accountInfo.setUserName(userName);
                accountInfo.setPsw(_psw);
                accountInfo.setRange(_range);
            }
            cursor.close();
        }
        db.close();
        return accountInfo;
    }

    /**
     * 查询所有用户的信息
     *
     * @return 保存有用户信息的List
     */
    public List<AccountInfo> findAllUsers() {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        List<AccountInfo> accounts = null;
        Cursor cursor = db.query(DBInfo.Table.ACCOUNT_TB_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                accounts = new ArrayList<>(cursor.getCount());
                AccountInfo accountInfo = null;
                while (cursor.moveToNext()) {
                    accountInfo = new AccountInfo();
                    String _userName = cursor.getString(cursor.getColumnIndex(AccountInfo.USERNAME));
                    String _psw = cursor.getString(cursor.getColumnIndex(AccountInfo.PSW));
                    String _range = cursor.getString(cursor.getColumnIndex(AccountInfo.RANGE));

                    accountInfo.setUserName(_userName);
                    accountInfo.setPsw(_psw);
                    accountInfo.setRange(_range);

                    accounts.add(accountInfo);
                }
                cursor.close();
            }
        }
        db.close();
        return accounts;
    }

    /**
     * 修改密码
     *
     * @param userName    用户名
     * @param newPassword 新密码
     */
    public void updatePassword(String userName, String newPassword) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String sql = "update " + DBInfo.Table.ACCOUNT_TB_NAME + " set psw='" + newPassword + "' where userName='" + userName + "'";
        db.execSQL(sql);
        db.close();
    }
}
