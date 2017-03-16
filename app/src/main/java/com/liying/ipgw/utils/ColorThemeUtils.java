package com.liying.ipgw.utils;

import com.liying.ipgw.R;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/4 15:14
 * 版本：1.0
 * 描述：色彩主题工具
 * 备注：
 * =======================================================
 */
public class ColorThemeUtils {
    /**
     * 根据名字获取相应的色彩主题
     * @param name 主题名
     * @return style
     */
    public static int getThemeByName(String name) {
        switch (name) {
            case "遠州鼠":
                return R.style.YZS_Theme;
            case "落栗":
                return R.style.LL_Theme;
            case "蘇芳":
                return R.style.SF_Theme;
            case "石竹":
                return R.style.SZ_Theme;
            case "枯草":
                return R.style.KC_Theme;
            case "柳煤竹茶":
                return R.style.LMZC_Theme;
            case "錆青磁":
                return R.style.QQC_Theme;
            case "鳩羽紫":
                return R.style.JYZ_Theme;
            default:
                return 0;
        }
    }

    /**
     * 根据名字获取相应的Drawable资源
     * @param name 主题名
     * @return Drawable资源数组
     */
    public static int[] getDrawablesByName(String name) {
        int[] drawables = new int[6];
        switch (name) {
            case "遠州鼠":
                drawables[0] = R.drawable.selector_ext_list_left_top_yzs;
                drawables[1] = R.drawable.selector_ext_list_left_middle_yzs;
                drawables[2] = R.drawable.selector_ext_list_left_bottom_yzs;
                drawables[3] = R.drawable.selector_ext_list_right_2_top_yzs;
                drawables[4] = R.drawable.selector_ext_list_right_2_middle_yzs;
                drawables[5] = R.drawable.selector_ext_list_right_2_bottom_yzs;
                break;
            case "落栗":
                drawables[0] = R.drawable.selector_ext_list_left_top_ll;
                drawables[1] = R.drawable.selector_ext_list_left_middle_ll;
                drawables[2] = R.drawable.selector_ext_list_left_bottom_ll;
                drawables[3] = R.drawable.selector_ext_list_right_2_top_ll;
                drawables[4] = R.drawable.selector_ext_list_right_2_middle_ll;
                drawables[5] = R.drawable.selector_ext_list_right_2_bottom_ll;
                break;
            case "蘇芳":
                drawables[0] = R.drawable.selector_ext_list_left_top_sf;
                drawables[1] = R.drawable.selector_ext_list_left_middle_sf;
                drawables[2] = R.drawable.selector_ext_list_left_bottom_sf;
                drawables[3] = R.drawable.selector_ext_list_right_2_top_sf;
                drawables[4] = R.drawable.selector_ext_list_right_2_middle_sf;
                drawables[5] = R.drawable.selector_ext_list_right_2_bottom_sf;
                break;
            case "石竹":
                drawables[0] = R.drawable.selector_ext_list_left_top_sz;
                drawables[1] = R.drawable.selector_ext_list_left_middle_sz;
                drawables[2] = R.drawable.selector_ext_list_left_bottom_sz;
                drawables[3] = R.drawable.selector_ext_list_right_2_top_sz;
                drawables[4] = R.drawable.selector_ext_list_right_2_middle_sz;
                drawables[5] = R.drawable.selector_ext_list_right_2_bottom_sz;
                break;
            case "枯草":
                drawables[0] = R.drawable.selector_ext_list_left_top_kc;
                drawables[1] = R.drawable.selector_ext_list_left_middle_kc;
                drawables[2] = R.drawable.selector_ext_list_left_bottom_kc;
                drawables[3] = R.drawable.selector_ext_list_right_2_top_kc;
                drawables[4] = R.drawable.selector_ext_list_right_2_middle_kc;
                drawables[5] = R.drawable.selector_ext_list_right_2_bottom_kc;
                break;
            case "柳煤竹茶":
                drawables[0] = R.drawable.selector_ext_list_left_top_lmzc;
                drawables[1] = R.drawable.selector_ext_list_left_middle_lmzc;
                drawables[2] = R.drawable.selector_ext_list_left_bottom_lmzc;
                drawables[3] = R.drawable.selector_ext_list_right_2_top_lmzc;
                drawables[4] = R.drawable.selector_ext_list_right_2_middle_lmzc;
                drawables[5] = R.drawable.selector_ext_list_right_2_bottom_lmzc;
                break;
            case "錆青磁":
                drawables[0] = R.drawable.selector_ext_list_left_top_qqc;
                drawables[1] = R.drawable.selector_ext_list_left_middle_qqc;
                drawables[2] = R.drawable.selector_ext_list_left_bottom_qqc;
                drawables[3] = R.drawable.selector_ext_list_right_2_top_qqc;
                drawables[4] = R.drawable.selector_ext_list_right_2_middle_qqc;
                drawables[5] = R.drawable.selector_ext_list_right_2_bottom_qqc;
                break;
            case "鳩羽紫":
                drawables[0] = R.drawable.selector_ext_list_left_top_jyz;
                drawables[1] = R.drawable.selector_ext_list_left_middle_jyz;
                drawables[2] = R.drawable.selector_ext_list_left_bottom_jyz;
                drawables[3] = R.drawable.selector_ext_list_right_2_top_jyz;
                drawables[4] = R.drawable.selector_ext_list_right_2_middle_jyz;
                drawables[5] = R.drawable.selector_ext_list_right_2_bottom_jyz;
                break;
            default:
                break;
        }
        return drawables;
    }
}
