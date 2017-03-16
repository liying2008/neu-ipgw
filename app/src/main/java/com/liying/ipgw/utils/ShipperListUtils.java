package com.liying.ipgw.utils;

import com.liying.ipgw.model.Shipper;

import java.util.ArrayList;
import java.util.List;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/22 0:31
 * 版本：1.0
 * 描述：快递公司列表数据
 * 备注：
 * =======================================================
 */
public class ShipperListUtils {
    public static List<Shipper> shipperList = new ArrayList<>(380);
    static {
        // 国内
        shipperList.add(new Shipper("安捷快递", "AJ"));
        shipperList.add(new Shipper("安能物流", "ANE"));
        shipperList.add(new Shipper("安信达快递", "AXD"));
        shipperList.add(new Shipper("北青小红帽", "BQXHM"));
        shipperList.add(new Shipper("百福东方", "BFDF"));
        shipperList.add(new Shipper("百世快运", "BTWL"));
        shipperList.add(new Shipper("CCES快递", "CCES"));
        shipperList.add(new Shipper("城市100", "CITY100"));
        shipperList.add(new Shipper("COE东方快递", "COE"));
        shipperList.add(new Shipper("长沙创一", "CSCY"));
        shipperList.add(new Shipper("成都善途速运", "CDSTKY"));
        shipperList.add(new Shipper("德邦", "DBL"));
        shipperList.add(new Shipper("D速物流", "DSWL"));
        shipperList.add(new Shipper("大田物流", "DTWL"));
        shipperList.add(new Shipper("EMS", "EMS"));
        shipperList.add(new Shipper("快捷速递", "FAST"));
        shipperList.add(new Shipper("FEDEX联邦(国内件）", "FEDEX"));
        shipperList.add(new Shipper("FEDEX联邦(国际件）", "FEDEX_GJ"));
        shipperList.add(new Shipper("飞康达", "FKD"));
        shipperList.add(new Shipper("广东邮政", "GDEMS"));
        shipperList.add(new Shipper("共速达", "GSD"));
        shipperList.add(new Shipper("国通快递", "GTO"));
        shipperList.add(new Shipper("高铁速递", "GTSD"));
        shipperList.add(new Shipper("汇丰物流", "HFWL"));
        shipperList.add(new Shipper("天天快递", "HHTT"));
        shipperList.add(new Shipper("恒路物流", "HLWL"));
        shipperList.add(new Shipper("天地华宇", "HOAU"));
        shipperList.add(new Shipper("华强物流", "hq568"));
        shipperList.add(new Shipper("百世快递", "HTKY"));
        shipperList.add(new Shipper("华夏龙物流", "HXLWL"));
        shipperList.add(new Shipper("好来运快递", "HYLSD"));
        shipperList.add(new Shipper("京广速递", "JGSD"));
        shipperList.add(new Shipper("九曳供应链", "JIUYE"));
        shipperList.add(new Shipper("佳吉快运", "JJKY"));
        shipperList.add(new Shipper("嘉里物流", "JLDT"));
        shipperList.add(new Shipper("捷特快递", "JTKD"));
        shipperList.add(new Shipper("急先达", "JXD"));
        shipperList.add(new Shipper("晋越快递", "JYKD"));
        shipperList.add(new Shipper("加运美", "JYM"));
        shipperList.add(new Shipper("佳怡物流", "JYWL"));
        shipperList.add(new Shipper("跨越物流", "KYWL"));
        shipperList.add(new Shipper("龙邦快递", "LB"));
        shipperList.add(new Shipper("联昊通速递", "LHT"));
        shipperList.add(new Shipper("民航快递", "MHKD"));
        shipperList.add(new Shipper("明亮物流", "MLWL"));
        shipperList.add(new Shipper("能达速递", "NEDA"));
        shipperList.add(new Shipper("平安达腾飞快递", "PADTF"));
        shipperList.add(new Shipper("全晨快递", "QCKD"));
        shipperList.add(new Shipper("全峰快递", "QFKD"));
        shipperList.add(new Shipper("全日通快递", "QRT"));
        shipperList.add(new Shipper("如风达", "RFD"));
        shipperList.add(new Shipper("赛澳递", "SAD"));
        shipperList.add(new Shipper("圣安物流", "SAWL"));
        shipperList.add(new Shipper("盛邦物流", "SBWL"));
        shipperList.add(new Shipper("上大物流", "SDWL"));
        shipperList.add(new Shipper("顺丰快递", "SF"));
        shipperList.add(new Shipper("盛丰物流", "SFWL"));
        shipperList.add(new Shipper("盛辉物流", "SHWL"));
        shipperList.add(new Shipper("速通物流", "ST"));
        shipperList.add(new Shipper("申通快递", "STO"));
        shipperList.add(new Shipper("速腾快递", "STWL"));
        shipperList.add(new Shipper("速尔快递", "SURE"));
        shipperList.add(new Shipper("唐山申通", "TSSTO"));
        shipperList.add(new Shipper("全一快递", "UAPEX"));
        shipperList.add(new Shipper("优速快递", "UC"));
        shipperList.add(new Shipper("万家物流", "WJWL"));
        shipperList.add(new Shipper("万象物流", "WXWL"));
        shipperList.add(new Shipper("新邦物流", "XBWL"));
        shipperList.add(new Shipper("信丰快递", "XFEX"));
        shipperList.add(new Shipper("希优特", "XYT"));
        shipperList.add(new Shipper("新杰物流", "XJ"));
        shipperList.add(new Shipper("源安达快递", "YADEX"));
        shipperList.add(new Shipper("远成物流", "YCWL"));
        shipperList.add(new Shipper("韵达快递", "YD"));
        shipperList.add(new Shipper("义达国际物流", "YDH"));
        shipperList.add(new Shipper("越丰物流", "YFEX"));
        shipperList.add(new Shipper("原飞航物流", "YFHEX"));
        shipperList.add(new Shipper("亚风快递", "YFSD"));
        shipperList.add(new Shipper("运通快递", "YTKD"));
        shipperList.add(new Shipper("圆通速递", "YTO"));
        shipperList.add(new Shipper("亿翔快递", "YXKD"));
        shipperList.add(new Shipper("邮政平邮/小包", "YZPY"));
        shipperList.add(new Shipper("增益快递", "ZENY"));
        shipperList.add(new Shipper("汇强快递", "ZHQKD"));
        shipperList.add(new Shipper("宅急送", "ZJS"));
        shipperList.add(new Shipper("众通快递", "ZTE"));
        shipperList.add(new Shipper("中铁快运", "ZTKY"));
        shipperList.add(new Shipper("中通速递", "ZTO"));
        shipperList.add(new Shipper("中铁物流", "ZTWL"));
        shipperList.add(new Shipper("中邮物流", "ZYWL"));
        shipperList.add(new Shipper("亚马逊物流", "AMAZON"));
        shipperList.add(new Shipper("速必达物流", "SUBIDA"));
        shipperList.add(new Shipper("瑞丰速递", "RFEX"));
        shipperList.add(new Shipper("快客快递", "QUICK"));
        shipperList.add(new Shipper("城际快递", "CJKD"));
        shipperList.add(new Shipper("CNPEX中邮快递", "CNPEX"));
        shipperList.add(new Shipper("鸿桥供应链", "HOTSCM"));
        shipperList.add(new Shipper("海派通物流公司", "HPTEX"));
        shipperList.add(new Shipper("澳邮专线", "AYCA"));
        shipperList.add(new Shipper("泛捷快递", "PANEX"));
        shipperList.add(new Shipper("PCA Express", "PCA"));
        shipperList.add(new Shipper("UEQ Express", "UEQ"));
        // 国外
        shipperList.add(new Shipper("AAE全球专递", "AAE"));
        shipperList.add(new Shipper("ACS雅仕快递", "ACS"));
        shipperList.add(new Shipper("ADP Express Tracking", "ADP"));
        shipperList.add(new Shipper("安圭拉邮政", "ANGUILAYOU"));
        shipperList.add(new Shipper("澳门邮政", "AOMENYZ"));
        shipperList.add(new Shipper("APAC", "APAC"));
        shipperList.add(new Shipper("Aramex", "ARAMEX"));
        shipperList.add(new Shipper("奥地利邮政", "AT"));
        shipperList.add(new Shipper("Australia Post Tracking", "AUSTRALIA"));
        shipperList.add(new Shipper("比利时邮政", "BEL"));
        shipperList.add(new Shipper("BHT快递", "BHT"));
        shipperList.add(new Shipper("秘鲁邮政", "BILUYOUZHE"));
        shipperList.add(new Shipper("巴西邮政", "BR"));
        shipperList.add(new Shipper("不丹邮政", "BUDANYOUZH"));
        shipperList.add(new Shipper("加拿大邮政", "CA"));
        shipperList.add(new Shipper("递四方速递", "D4PX"));
        shipperList.add(new Shipper("DHL", "DHL"));
        shipperList.add(new Shipper("DHL(英文版)", "DHL_EN"));
        shipperList.add(new Shipper("DHL全球", "DHL_GLB"));
        shipperList.add(new Shipper("DHL Global Mail", "DHLGM"));
        shipperList.add(new Shipper("丹麦邮政", "DK"));
        shipperList.add(new Shipper("DPD", "DPD"));
        shipperList.add(new Shipper("DPEX", "DPEX"));
        shipperList.add(new Shipper("EMS国际", "EMSGJ"));
        shipperList.add(new Shipper("EShipper", "ESHIPPER"));
        shipperList.add(new Shipper("国际e邮宝", "GJEYB"));
        shipperList.add(new Shipper("国际邮政包裹", "GJYZ"));
        shipperList.add(new Shipper("GLS", "GLS"));
        shipperList.add(new Shipper("安的列斯群岛邮政", "IADLSQDYZ"));
        shipperList.add(new Shipper("澳大利亚邮政", "IADLYYZ"));
        shipperList.add(new Shipper("阿尔巴尼亚邮政", "IAEBNYYZ"));
        shipperList.add(new Shipper("阿尔及利亚邮政", "IAEJLYYZ"));
        shipperList.add(new Shipper("阿富汗邮政", "IAFHYZ"));
        shipperList.add(new Shipper("安哥拉邮政", "IAGLYZ"));
        shipperList.add(new Shipper("阿根廷邮政", "IAGTYZ"));
        shipperList.add(new Shipper("埃及邮政", "IAJYZ"));
        shipperList.add(new Shipper("阿鲁巴邮政", "IALBYZ"));
        shipperList.add(new Shipper("奥兰群岛邮政", "IALQDYZ"));
        shipperList.add(new Shipper("阿联酋邮政", "IALYYZ"));
        shipperList.add(new Shipper("阿曼邮政", "IAMYZ"));
        shipperList.add(new Shipper("阿塞拜疆邮政", "IASBJYZ"));
        shipperList.add(new Shipper("埃塞俄比亚邮政", "IASEBYYZ"));
        shipperList.add(new Shipper("爱沙尼亚邮政", "IASNYYZ"));
        shipperList.add(new Shipper("阿森松岛邮政", "IASSDYZ"));
        shipperList.add(new Shipper("博茨瓦纳邮政", "IBCWNYZ"));
        shipperList.add(new Shipper("波多黎各邮政", "IBDLGYZ"));
        shipperList.add(new Shipper("冰岛邮政", "IBDYZ"));
        shipperList.add(new Shipper("白俄罗斯邮政", "IBELSYZ"));
        shipperList.add(new Shipper("波黑邮政", "IBHYZ"));
        shipperList.add(new Shipper("保加利亚邮政", "IBJLYYZ"));
        shipperList.add(new Shipper("巴基斯坦邮政", "IBJSTYZ"));
        shipperList.add(new Shipper("黎巴嫩邮政", "IBLNYZ"));
        shipperList.add(new Shipper("便利速递", "IBLSD"));
        shipperList.add(new Shipper("玻利维亚邮政", "IBLWYYZ"));
        shipperList.add(new Shipper("巴林邮政", "IBLYZ"));
        shipperList.add(new Shipper("百慕达邮政", "IBMDYZ"));
        shipperList.add(new Shipper("波兰邮政", "IBOLYZ"));
        shipperList.add(new Shipper("宝通达", "IBTD"));
        shipperList.add(new Shipper("贝邮宝", "IBYB"));
        shipperList.add(new Shipper("出口易", "ICKY"));
        shipperList.add(new Shipper("达方物流", "IDFWL"));
        shipperList.add(new Shipper("德国邮政", "IDGYZ"));
        shipperList.add(new Shipper("爱尔兰邮政", "IE"));
        shipperList.add(new Shipper("厄瓜多尔邮政", "IEGDEYZ"));
        shipperList.add(new Shipper("俄罗斯邮政", "IELSYZ"));
        shipperList.add(new Shipper("厄立特里亚邮政", "IELTLYYZ"));
        shipperList.add(new Shipper("飞特物流", "IFTWL"));
        shipperList.add(new Shipper("瓜德罗普岛EMS", "IGDLPDEMS"));
        shipperList.add(new Shipper("瓜德罗普岛邮政", "IGDLPDYZ"));
        shipperList.add(new Shipper("俄速递", "IGJESD"));
        shipperList.add(new Shipper("哥伦比亚邮政", "IGLBYYZ"));
        shipperList.add(new Shipper("格陵兰邮政", "IGLLYZ"));
        shipperList.add(new Shipper("哥斯达黎加邮政", "IGSDLJYZ"));
        shipperList.add(new Shipper("韩国邮政", "IHGYZ"));
        shipperList.add(new Shipper("华翰物流", "IHHWL"));
        shipperList.add(new Shipper("互联易", "IHLY"));
        shipperList.add(new Shipper("哈萨克斯坦邮政", "IHSKSTYZ"));
        shipperList.add(new Shipper("黑山邮政", "IHSYZ"));
        shipperList.add(new Shipper("津巴布韦邮政", "IJBBWYZ"));
        shipperList.add(new Shipper("吉尔吉斯斯坦邮政", "IJEJSSTYZ"));
        shipperList.add(new Shipper("捷克邮政", "IJKYZ"));
        shipperList.add(new Shipper("加纳邮政", "IJNYZ"));
        shipperList.add(new Shipper("柬埔寨邮政", "IJPZYZ"));
        shipperList.add(new Shipper("克罗地亚邮政", "IKNDYYZ"));
        shipperList.add(new Shipper("肯尼亚邮政", "IKNYYZ"));
        shipperList.add(new Shipper("科特迪瓦EMS", "IKTDWEMS"));
        shipperList.add(new Shipper("科特迪瓦邮政", "IKTDWYZ"));
        shipperList.add(new Shipper("卡塔尔邮政", "IKTEYZ"));
        shipperList.add(new Shipper("利比亚邮政", "ILBYYZ"));
        shipperList.add(new Shipper("林克快递", "ILKKD"));
        shipperList.add(new Shipper("罗马尼亚邮政", "ILMNYYZ"));
        shipperList.add(new Shipper("卢森堡邮政", "ILSBYZ"));
        shipperList.add(new Shipper("拉脱维亚邮政", "ILTWYYZ"));
        shipperList.add(new Shipper("立陶宛邮政", "ILTWYZ"));
        shipperList.add(new Shipper("列支敦士登邮政", "ILZDSDYZ"));
        shipperList.add(new Shipper("马尔代夫邮政", "IMEDFYZ"));
        shipperList.add(new Shipper("摩尔多瓦邮政", "IMEDWYZ"));
        shipperList.add(new Shipper("马耳他邮政", "IMETYZ"));
        shipperList.add(new Shipper("孟加拉国EMS", "IMJLGEMS"));
        shipperList.add(new Shipper("摩洛哥邮政", "IMLGYZ"));
        shipperList.add(new Shipper("毛里求斯邮政", "IMLQSYZ"));
        shipperList.add(new Shipper("马来西亚EMS", "IMLXYEMS"));
        shipperList.add(new Shipper("马来西亚邮政", "IMLXYYZ"));
        shipperList.add(new Shipper("马其顿邮政", "IMQDYZ"));
        shipperList.add(new Shipper("马提尼克EMS", "IMTNKEMS"));
        shipperList.add(new Shipper("马提尼克邮政", "IMTNKYZ"));
        shipperList.add(new Shipper("墨西哥邮政", "IMXGYZ"));
        shipperList.add(new Shipper("南非邮政", "INFYZ"));
        shipperList.add(new Shipper("尼日利亚邮政", "INRLYYZ"));
        shipperList.add(new Shipper("挪威邮政", "INWYZ"));
        shipperList.add(new Shipper("葡萄牙邮政", "IPTYYZ"));
        shipperList.add(new Shipper("全球快递", "IQQKD"));
        shipperList.add(new Shipper("全通物流", "IQTWL"));
        shipperList.add(new Shipper("苏丹邮政", "ISDYZ"));
        shipperList.add(new Shipper("萨尔瓦多邮政", "ISEWDYZ"));
        shipperList.add(new Shipper("塞尔维亚邮政", "ISEWYYZ"));
        shipperList.add(new Shipper("斯洛伐克邮政", "ISLFKYZ"));
        shipperList.add(new Shipper("斯洛文尼亚邮政", "ISLWNYYZ"));
        shipperList.add(new Shipper("塞内加尔邮政", "ISNJEYZ"));
        shipperList.add(new Shipper("塞浦路斯邮政", "ISPLSYZ"));
        shipperList.add(new Shipper("沙特阿拉伯邮政", "ISTALBYZ"));
        shipperList.add(new Shipper("土耳其邮政", "ITEQYZ"));
        shipperList.add(new Shipper("泰国邮政", "ITGYZ"));
        shipperList.add(new Shipper("特立尼达和多巴哥EMS", "ITLNDHDBGE"));
        shipperList.add(new Shipper("突尼斯邮政", "ITNSYZ"));
        shipperList.add(new Shipper("坦桑尼亚邮政", "ITSNYYZ"));
        shipperList.add(new Shipper("危地马拉邮政", "IWDMLYZ"));
        shipperList.add(new Shipper("乌干达邮政", "IWGDYZ"));
        shipperList.add(new Shipper("乌克兰EMS", "IWKLEMS"));
        shipperList.add(new Shipper("乌克兰邮政", "IWKLYZ"));
        shipperList.add(new Shipper("乌拉圭邮政", "IWLGYZ"));
        shipperList.add(new Shipper("文莱邮政", "IWLYZ"));
        shipperList.add(new Shipper("乌兹别克斯坦EMS", "IWZBKSTEMS"));
        shipperList.add(new Shipper("乌兹别克斯坦邮政", "IWZBKSTYZ"));
        shipperList.add(new Shipper("西班牙邮政", "IXBYYZ"));
        shipperList.add(new Shipper("小飞龙物流", "IXFLWL"));
        shipperList.add(new Shipper("新喀里多尼亚邮政", "IXGLDNYYZ"));
        shipperList.add(new Shipper("新加坡EMS", "IXJPEMS"));
        shipperList.add(new Shipper("新加坡邮政", "IXJPYZ"));
        shipperList.add(new Shipper("叙利亚邮政", "IXLYYZ"));
        shipperList.add(new Shipper("希腊邮政", "IXLYZ"));
        shipperList.add(new Shipper("夏浦世纪", "IXPSJ"));
        shipperList.add(new Shipper("夏浦物流", "IXPWL"));
        shipperList.add(new Shipper("新西兰邮政", "IXXLYZ"));
        shipperList.add(new Shipper("匈牙利邮政", "IXYLYZ"));
        shipperList.add(new Shipper("意大利邮政", "IYDLYZ"));
        shipperList.add(new Shipper("印度尼西亚邮政", "IYDNXYYZ"));
        shipperList.add(new Shipper("印度邮政", "IYDYZ"));
        shipperList.add(new Shipper("英国邮政", "IYGYZ"));
        shipperList.add(new Shipper("伊朗邮政", "IYLYZ"));
        shipperList.add(new Shipper("亚美尼亚邮政", "IYMNYYZ"));
        shipperList.add(new Shipper("也门邮政", "IYMYZ"));
        shipperList.add(new Shipper("越南邮政", "IYNYZ"));
        shipperList.add(new Shipper("以色列邮政", "IYSLYZ"));
        shipperList.add(new Shipper("易通关", "IYTG"));
        shipperList.add(new Shipper("燕文物流", "IYWWL"));
        shipperList.add(new Shipper("直布罗陀邮政", "IZBLTYZ"));
        shipperList.add(new Shipper("智利邮政", "IZLYZ"));
        shipperList.add(new Shipper("日本邮政", "JP"));
        shipperList.add(new Shipper("荷兰邮政", "NL"));
        shipperList.add(new Shipper("ONTRAC", "ONTRAC"));
        shipperList.add(new Shipper("全球邮政", "QQYZ"));
        shipperList.add(new Shipper("瑞典邮政", "RDSE"));
        shipperList.add(new Shipper("瑞士邮政", "SWCH"));
        shipperList.add(new Shipper("台湾邮政", "TAIWANYZ"));
        shipperList.add(new Shipper("TNT快递", "TNT"));
        shipperList.add(new Shipper("UPS", "UPS"));
        shipperList.add(new Shipper("USPS美国邮政", "USPS"));
        shipperList.add(new Shipper("日本大和运输(Yamato)", "YAMA"));
        shipperList.add(new Shipper("YODEL", "YODEL"));
        shipperList.add(new Shipper("约旦邮政", "YUEDANYOUZ"));

        // 转运
        shipperList.add(new Shipper("爱购转运", "ZY_AG"));
        shipperList.add(new Shipper("爱欧洲", "ZY_AOZ"));
        shipperList.add(new Shipper("澳世速递", "ZY_AUSE"));
        shipperList.add(new Shipper("AXO", "ZY_AXO"));
        shipperList.add(new Shipper("澳转运", "ZY_AZY"));
        shipperList.add(new Shipper("八达网", "ZY_BDA"));
        shipperList.add(new Shipper("蜜蜂速递", "ZY_BEE"));
        shipperList.add(new Shipper("贝海速递", "ZY_BH"));
        shipperList.add(new Shipper("百利快递", "ZY_BL"));
        shipperList.add(new Shipper("斑马物流", "ZY_BM"));
        shipperList.add(new Shipper("败欧洲", "ZY_BOZ"));
        shipperList.add(new Shipper("百通物流", "ZY_BT"));
        shipperList.add(new Shipper("贝易购", "ZY_BYECO"));
        shipperList.add(new Shipper("策马转运", "ZY_CM"));
        shipperList.add(new Shipper("赤兔马转运", "ZY_CTM"));
        shipperList.add(new Shipper("CUL中美速递", "ZY_CUL"));
        shipperList.add(new Shipper("德国海淘之家", "ZY_DGHT"));
        shipperList.add(new Shipper("德运网", "ZY_DYW"));
        shipperList.add(new Shipper("EFS POST", "ZY_EFS"));
        shipperList.add(new Shipper("宜送转运", "ZY_ESONG"));
        shipperList.add(new Shipper("ETD", "ZY_ETD"));
        shipperList.add(new Shipper("飞碟快递", "ZY_FD"));
        shipperList.add(new Shipper("飞鸽快递", "ZY_FG"));
        shipperList.add(new Shipper("风雷速递", "ZY_FLSD"));
        shipperList.add(new Shipper("风行快递", "ZY_FX"));
        shipperList.add(new Shipper("风行速递", "ZY_FXSD"));
        shipperList.add(new Shipper("飞洋快递", "ZY_FY"));
        shipperList.add(new Shipper("皓晨快递", "ZY_HC"));
        shipperList.add(new Shipper("皓晨优递", "ZY_HCYD"));
        shipperList.add(new Shipper("海带宝", "ZY_HDB"));
        shipperList.add(new Shipper("汇丰美中速递", "ZY_HFMZ"));
        shipperList.add(new Shipper("豪杰速递", "ZY_HJSD"));
        shipperList.add(new Shipper("360hitao转运", "ZY_HTAO"));
        shipperList.add(new Shipper("海淘村", "ZY_HTCUN"));
        shipperList.add(new Shipper("365海淘客", "ZY_HTKE"));
        shipperList.add(new Shipper("华通快运", "ZY_HTONG"));
        shipperList.add(new Shipper("海星桥快递", "ZY_HXKD"));
        shipperList.add(new Shipper("华兴速运", "ZY_HXSY"));
        shipperList.add(new Shipper("海悦速递", "ZY_HYSD"));
        shipperList.add(new Shipper("LogisticsY", "ZY_IHERB"));
        shipperList.add(new Shipper("君安快递", "ZY_JA"));
        shipperList.add(new Shipper("时代转运", "ZY_JD"));
        shipperList.add(new Shipper("骏达快递", "ZY_JDKD"));
        shipperList.add(new Shipper("骏达转运", "ZY_JDZY"));
        shipperList.add(new Shipper("久禾快递", "ZY_JH"));
        shipperList.add(new Shipper("金海淘", "ZY_JHT"));
        shipperList.add(new Shipper("联邦转运FedRoad", "ZY_LBZY"));
        shipperList.add(new Shipper("领跑者快递", "ZY_LPZ"));
        shipperList.add(new Shipper("龙象快递", "ZY_LX"));
        shipperList.add(new Shipper("量子物流", "ZY_LZWL"));
        shipperList.add(new Shipper("明邦转运", "ZY_MBZY"));
        shipperList.add(new Shipper("美国转运", "ZY_MGZY"));
        shipperList.add(new Shipper("美嘉快递", "ZY_MJ"));
        shipperList.add(new Shipper("美速通", "ZY_MST"));
        shipperList.add(new Shipper("美西转运", "ZY_MXZY"));
        shipperList.add(new Shipper("168 美中快递", "ZY_MZ"));
        shipperList.add(new Shipper("欧e捷", "ZY_OEJ"));
        shipperList.add(new Shipper("欧洲疯", "ZY_OZF"));
        shipperList.add(new Shipper("欧洲GO", "ZY_OZGO"));
        shipperList.add(new Shipper("全美通", "ZY_QMT"));
        shipperList.add(new Shipper("QQ-EX", "ZY_QQEX"));
        shipperList.add(new Shipper("润东国际快线", "ZY_RDGJ"));
        shipperList.add(new Shipper("瑞天快递", "ZY_RT"));
        shipperList.add(new Shipper("瑞天速递", "ZY_RTSD"));
        shipperList.add(new Shipper("SCS国际物流", "ZY_SCS"));
        shipperList.add(new Shipper("速达快递", "ZY_SDKD"));
        shipperList.add(new Shipper("四方转运", "ZY_SFZY"));
        shipperList.add(new Shipper("SOHO苏豪国际", "ZY_SOHO"));
        shipperList.add(new Shipper("Sonic-Ex速递", "ZY_SONIC"));
        shipperList.add(new Shipper("上腾快递", "ZY_ST"));
        shipperList.add(new Shipper("通诚美中快递", "ZY_TCM"));
        shipperList.add(new Shipper("天际快递", "ZY_TJ"));
        shipperList.add(new Shipper("天马转运", "ZY_TM"));
        shipperList.add(new Shipper("滕牛快递", "ZY_TN"));
        shipperList.add(new Shipper("TrakPak", "ZY_TPAK"));
        shipperList.add(new Shipper("太平洋快递", "ZY_TPY"));
        shipperList.add(new Shipper("唐三藏转运", "ZY_TSZ"));
        shipperList.add(new Shipper("天天海淘", "ZY_TTHT"));
        shipperList.add(new Shipper("TWC转运世界", "ZY_TWC"));
        shipperList.add(new Shipper("同心快递", "ZY_TX"));
        shipperList.add(new Shipper("天翼快递", "ZY_TY"));
        shipperList.add(new Shipper("同舟快递", "ZY_TZH"));
        shipperList.add(new Shipper("UCS合众快递", "ZY_UCS"));
        shipperList.add(new Shipper("文达国际DCS", "ZY_WDCS"));
        shipperList.add(new Shipper("星辰快递", "ZY_XC"));
        shipperList.add(new Shipper("迅达快递", "ZY_XDKD"));
        shipperList.add(new Shipper("信达速运", "ZY_XDSY"));
        shipperList.add(new Shipper("先锋快递", "ZY_XF"));
        shipperList.add(new Shipper("新干线快递", "ZY_XGX"));
        shipperList.add(new Shipper("西邮寄", "ZY_XIYJ"));
        shipperList.add(new Shipper("信捷转运", "ZY_XJ"));
        shipperList.add(new Shipper("优购快递", "ZY_YGKD"));
        shipperList.add(new Shipper("友家速递(UCS)", "ZY_YJSD"));
        shipperList.add(new Shipper("云畔网", "ZY_YPW"));
        shipperList.add(new Shipper("云骑快递", "ZY_YQ"));
        shipperList.add(new Shipper("一柒物流", "ZY_YQWL"));
        shipperList.add(new Shipper("优晟速递", "ZY_YSSD"));
        shipperList.add(new Shipper("易送网", "ZY_YSW"));
        shipperList.add(new Shipper("运淘美国", "ZY_YTUSA"));
        shipperList.add(new Shipper("至诚速递", "ZY_ZCSD"));
    }
}