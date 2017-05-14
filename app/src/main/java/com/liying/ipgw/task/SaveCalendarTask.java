package com.liying.ipgw.task;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.liying.ipgw.utils.Constants;
import com.liying.ipgw.utils.DrawableUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import cc.duduhuo.applicationtoast.AppToast;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2017/3/21 14:00
 * 版本：1.0
 * 描述：保存校历图片异步任务
 * 备注：
 * =======================================================
 */
public class SaveCalendarTask extends AsyncTask<Void, Void, Boolean> {
    private Drawable mResource;
    public SaveCalendarTask(Drawable resource) {
        this.mResource = resource;
    }
    @Override
    protected Boolean doInBackground(Void... params) {
        // 得到Bitmap
        Bitmap img = DrawableUtils.convertDrawable2Bitmap(mResource);
        String fn = "校历.jpg";
        String path = Constants.WORK_DIR + File.separator + fn;
        if (img != null) {
            try {
                OutputStream os = new FileOutputStream(path);
                img.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        if (success) {
            AppToast.showToast("校历图片已保存至“" + Constants.IPGW_DIR + "”文件夹下。");
        } else {
            AppToast.showToast("保存校历失败。");
        }
    }
}
