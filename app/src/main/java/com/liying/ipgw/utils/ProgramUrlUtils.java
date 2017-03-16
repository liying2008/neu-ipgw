package com.liying.ipgw.utils;


/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/5/20 20:27
 * 版本：1.0
 * 描述：节目地址工具类
 * 备注：
 * =======================================================
 */
public class ProgramUrlUtils {
    /**
     * 从传入的intent数据中得到节目地址
     * @param programInfo
     * @return 节目地址
     */
    public static String getProgramPathFromInfo(String[] programInfo) {
        if ("1".equals(programInfo[0])) {
            // NEU IPv6视频直播
            String p = programInfo[1];
            if ("".equals(p)) {
                return "";
            }
            return "http://media2.neu6.edu.cn/hls/" + p + ".m3u8";
        } else if ("2".equals(programInfo[0])) {
            // NEU IPv6视频回看
            String p = programInfo[3];
            if ("".equals(p) || "".equals(programInfo[1]) || "".equals(programInfo[2])) {
                return "";
            }
            return "http://media2.neu6.edu.cn/review/program-" + programInfo[1] + "-" + programInfo[2] + "-" + p + ".m3u8";
        } else {
            return "";
        }
    }
}
