package com.liying.ipgw.model;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/4 20:11
 * 版本：1.0
 * 描述：色彩主题实体
 * 备注：
 * =======================================================
 */
public class ColorTheme {
    /** 主题位置 */
    private int position;
    /** 主题名称 */
    private String themeName;
    /** 预览图片的Drawable id */
    private int themePreId;
    /** 是否选中 */
    private boolean isSelected;
    /** 主题style id */
    private int styleId;

    public ColorTheme() {
    }

    public ColorTheme(int position, String themeName, int themePreId, boolean isSelected, int styleId) {
        this.position = position;
        this.themeName = themeName;
        this.themePreId = themePreId;
        this.isSelected = isSelected;
        this.styleId = styleId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public int getThemePreId() {
        return themePreId;
    }

    public void setThemePreId(int themePreId) {
        this.themePreId = themePreId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }
}
