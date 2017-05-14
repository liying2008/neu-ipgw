package com.liying.ipgw.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2017/3/21 14:03
 * 版本：1.0
 * 描述：Drawable/Bitmap工具类
 * 备注：
 * =======================================================
 */
public class DrawableUtils {
    /**
     * drawable -> bitmap
     * @param drawable
     * @return
     */
    public static Bitmap convertDrawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = null;
        if (drawable != null) {
            bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
        }
        return bitmap;
    }

}
