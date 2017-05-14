package com.liying.ipgw.utils;

import com.liying.ipgw.R;
import com.liying.ipgw.model.Egg;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/7 11:03
 * 版本：1.0
 * 描述：彩蛋工具类
 * 备注：
 * =======================================================
 */
public class EggUtils {
    /**
     * 根据彩蛋序号得到彩蛋
     *
     * @param num 彩蛋序号
     * @return 彩蛋对象
     */
    public static Egg getEggByNum(int num) {
        switch (num) {
            case 1:
                return new Egg(1, R.drawable.egg01_got, "不要问我为什么跟这个图标有仇！");
            case 2:
                return new Egg(2, R.drawable.egg02_got, "我坚信长按这些文字可以将它们复制！");
            case 3:
                return new Egg(3, R.drawable.egg03_got, "我发誓要好好学习，不再上网！");
            case 4:
                return new Egg(4, R.drawable.egg04_got, "其实，仔细阅读说明文档也是一大优点呢。");
            case 5:
                return new Egg(5, R.drawable.egg05_got, "对这个播放器我已经无力吐槽了……");
            case 6:
                return new Egg(6, R.drawable.egg06_got, "感觉最近泡在网上的日子越来越多了呢~");
            case 7:
                return new Egg(7, R.drawable.egg07_got, "一劳永逸，有备无患。");
            case 8:
                return new Egg(8, R.drawable.egg08_got, "流量黑洞！五个帐户已经不够我用的了！");
            case 9:
                return new Egg(9, R.drawable.egg09_got, "认真阅读一下作者的信息也是对作者的一种尊重呢~");
            case 10:
                return new Egg(10, R.drawable.egg10_got, "选了一遍也不知道哪个颜色比较好看……");
            default:
                return null;
        }
    }
}
