package com.liying.ipgw.utils;

import com.liying.ipgw.model.Program;
import com.liying.ipgw.model.ReviewDate;
import com.liying.ipgw.model.ReviewList;
import com.liying.ipgw.model.ReviewProgram;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/5 22:51
 * 版本：1.0
 * 描述：使用正则表达式匹配节目信息
 * 备注：
 * =======================================================
 */
public class RegexProgram {
    // 获取所有节目
    public static List<Program> getAllPrograms(String html) {
        List<Program> programs = new ArrayList<>(190);
        List<String> list = new ArrayList<>(190);
        // 定义规则
        String regex = "td .+播放";
        //将规则封装成对象
        Pattern p = Pattern.compile(regex);
        //让正则对象和要作用的字符串相关联，获取匹配器对象
        Matcher m = p.matcher(html);

        // 将规则作用到字符串上，并进行符合规则的子串查找
        while (m.find()) {
            list.add(m.group());
        }
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            s = s.replace("td >", "").replace("<br /><a href=\"newplayer?p=", "").replace("\">播放", "")
                    .replace("td style=\"background-color: #F0FFFF;\">", "");
            String[] arr = s.split(" ");
            programs.add(new Program(i, arr[0], arr[1]));
        }
        return programs;
    }

    private static Calendar cal = Calendar.getInstance(Locale.ENGLISH);

    public static ReviewList getReviewPrograms(String html) {
        /*
        div id="2016-05-16">
        <div id="noon">
        <div id="list_item">00:05 实况录像-2016年国际田联钻石联赛上海站精选</div>
        <div id="list_status"><a href="player-review?timeline=1463328300-1463333700-cctv5hd">回看</a></div>
         */
//        System.out.println("html = " + html);
        List<ReviewDate> groupDates = new ArrayList<>(8);
        List<List<ReviewProgram>> childPrograms = new ArrayList<>(8);
        List<ReviewProgram> reviewPrograms = new ArrayList<>(20);

        List<String> list = new ArrayList<>(160);
        // 定义规则
        String regex1 = "list_item.+div>\\s+.+回看";
        //将规则封装成对象
        Pattern pattern1 = Pattern.compile(regex1);
        //让正则对象和要作用的字符串相关联，获取匹配器对象
        Matcher m1 = pattern1.matcher(html);
//        System.out.println("找到匹配？" + (m1.find()));
        // 将规则作用到字符串上，并进行符合规则的子串查找
        while (m1.find()) {
            list.add(m1.group());
        }
//        System.out.println("list = " + list);
        // 分组用
        String groupName = "";
        for (int i = 0; i < list.size(); i++) {
            /*
            list_item">00:05 实况录像-2016年国际田联钻石联赛上海站精选><a href="player-review?timeline=1463328300-1463333700-cctv5hd">回看
             */
            String s = list.get(i);
            s = s.replace("list_item\">", "").replaceAll("</div>\\s+<div id=\"list_status\">", "")
                    .replace("<a href=\"player-review?", "").replace("\">回看", "");
//            System.out.println("s = " + s);
//             00:05 实况录像-2016年国际田联钻石联赛上海站精选timeline=1463328300-1463333700-cctv5hd
            String[] arr = s.split("timeline=");
            String reviewProgramName = arr[0];  // 节目的名字
            String[] timeline = arr[1].split("-");
            long timeStart = Long.parseLong(timeline[0]);   // 节目开始时间
//            System.out.println("timeStart = " + timeline[0]);
            String toDate = timeToDate(timeStart);
            if (groupName.equals(toDate)) {
                reviewPrograms.add(new ReviewProgram(reviewProgramName, timeline[0], timeline[1]));
            } else {
                groupDates.add(new ReviewDate(toDate));
                if (reviewPrograms.size() > 0) {
                    childPrograms.add(reviewPrograms);
                }
                reviewPrograms = new ArrayList<>(20);
                reviewPrograms.add(new ReviewProgram(reviewProgramName, timeline[0], timeline[1]));
                groupName = toDate;
            }
        }

        childPrograms.add(reviewPrograms);
        return new ReviewList(groupDates, childPrograms);
    }

    /**
     * 由StartTime获取日期
     */
    public static String timeToDate(long second) {
        Date date = new Date(second * 1000L);
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
//        if (w < 0) w = 0;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String dateStr = format.format(date) + " @" + weekDays[w];
//        System.out.println(dateStr);
        return dateStr;
    }
}
