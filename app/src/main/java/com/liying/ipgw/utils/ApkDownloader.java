package com.liying.ipgw.utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import java.io.File;


/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2015/6/28 13:16
 * 版本：1.0
 * 描述：软件安装包下载器
 * 备注：
 * =======================================================
 */
public class ApkDownloader {
    private String mFileName = "";
    private static final String DOWNLOAD_DIR = Constants.IPGW_DIR;

    /**
     * 下载文件
     *
     * @param context
     * @param url
     */
    public void downloadFile(Context context, String url) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri resource = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(resource);
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();   // 获取文件类型实例
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));   // 获取文件类型
        request.setMimeType(mimeString);  // 指定下载文件类型
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);

        request.setShowRunningNotification(true);
        //显示下载完成通知
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(true);

        int index = url.lastIndexOf("/");
        mFileName = url.substring(index + 1); // 获取文件名
        // 给文件名加上时间戳
        mFileName = mFileName.replace(".apk", "_" + System.currentTimeMillis() + ".apk");
        request.setDestinationInExternalPublicDir(DOWNLOAD_DIR, mFileName);  // 指定下载的目录
        downloadManager.enqueue(request);

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    Intent intent1 = new Intent();
                    intent1.setAction("android.intent.action.VIEW");
                    intent1.addCategory("android.intent.category.DEFAULT");
                    String filePath = Constants.WORK_DIR + File.separator + mFileName;
                    intent1.setDataAndType(Uri.fromFile(new File(filePath)),
                            "application/vnd.android.package-archive");
                    context.startActivity(intent1);
                }
            }
        };
        context.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }
}
