package com.liying.ipgw.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2017/3/19 19:34
 * 版本：1.0
 * 描述：
 * 备注：
 * =======================================================
 */
public class MyGlideMoudle implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // Apply options to the builder here.
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
