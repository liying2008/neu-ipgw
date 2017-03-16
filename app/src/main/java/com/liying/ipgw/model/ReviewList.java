package com.liying.ipgw.model;

import java.util.List;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/5/17 8:13
 * 版本：1.0
 * 描述：回看列表实体类
 * 备注：
 * =======================================================
 */
public class ReviewList {

    private List<ReviewDate> groupDates;
    private List<List<ReviewProgram>> childPrograms;

    public List<ReviewDate> getGroupDates() {
        return groupDates;
    }

    public void setGroupDates(List<ReviewDate> groupDates) {
        this.groupDates = groupDates;
    }

    public List<List<ReviewProgram>> getChildPrograms() {
        return childPrograms;
    }

    public void setChildPrograms(List<List<ReviewProgram>> childPrograms) {
        this.childPrograms = childPrograms;
    }

    public ReviewList(List<ReviewDate> groupDates, List<List<ReviewProgram>> childPrograms) {

        this.groupDates = groupDates;
        this.childPrograms = childPrograms;
    }
}
