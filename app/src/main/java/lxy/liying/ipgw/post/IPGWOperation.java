package lxy.liying.ipgw.post;


import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 执行连接网关或断开网关的操作并对返回的网页源代码进行加工
 *
 * @author 李颖
 */
public class IPGWOperation {

    private static String msg1 = "";
    private static String msg2 = "";
    private static String msg3 = "";
    private static String msg4 = "";
    private static String msg5 = "";
    private static String msg6 = "";
    private static String msg7 = "";
    private static String msg8 = "";
    private static String msg9 = "";
    private static String msg11 = "";
    private static String msg12 = "";
    private static String msg13 = "";
    private static String msg14 = "";

    /**
     * 连接状态信息数组
     */
    private static String[] information = new String[3];
    /**
     * 操作类型，1:连接网络
     */
    public static final int CONNECT = 0x00000001;
    /**
     * 操作类型，3:模拟主机端连接网络
     */
    public static final int CONNECT_PC = 0x00000003;
    /**
     * 操作类型，2:注销连接
     */
    public static final int DISCONNECT = 0x00000002;
    /**
     * 操作类型，0:断开全部连接
     */
    public static final int DISCONNECT_ALL = 0x00000000;

    /**
     * 将服务器返回的数据进行正则匹配
     *
     * @param uid      用户名
     * @param password 密码
     * @return 状态码：		<br />
     * 1：Authentication failed		<br />
     * 2：无法登录				<br />
     * 3：You are already online	<br />
     * 4：密码错误				<br />
     * 5：网络已断开 			<br />
     * 6：网络已连接			<br />
     * 7：注销成功				<br />
     * 8：无法连接到IPGW网关		<br />
     * 9：内部服务器错误			<br />
     * 10：未知的主机异常			<br />
     * 11：phone连接成功			<br />
     * 12：您似乎未曾连接到网络	<br />
     * 13：用户不存在			<br />
     * 14：已欠费	            <br />
     * -1：操作不合法！			<br />
     * -2：操作失败！			<br />
     */
    public static int getConnectState(String uid, String password) {
        String html;
        try {
            html = Connect.connect(uid, password);
        } catch (UnknownHostException e) {
            System.out.println("未知的主机异常");
            return 10;
        }
        if ("".equals(html)) {
            System.out.println("操作失败！");
            return -2;
        }
//        System.out.println(html);
        return getOperatingState(html);
    }

    /**
     * 取消所有请求
     */
    public static void cancel() {
        Connect.cancel();
        ConnectPC.cancel();
        Disconnect.cancel();
        DisconnectAll.cancel();
    }

    /**
     * 模拟主机端连接网络
     *
     * @param uid      用户名
     * @param password 密码
     * @return 状态码
     */
    public static int getConnectPCState(String uid, String password) {
        String html;
        try {
            html = ConnectPC.connectPC(uid, password);
        } catch (UnknownHostException e) {
            System.out.println("未知的主机异常");
            return 10;
        }
        if ("".equals(html)) {
            System.out.println("操作失败！");
            return -2;
        }
        System.out.println(html);
        return getOperatingState(html);
    }

    /**
     * @param uid      用户名
     * @param password 密码
     * @return
     */
    public static int getDisconnectAllState(String uid, String password) {
        String html;
        try {
            html = DisconnectAll.disconnectAll(uid, password);
        } catch (UnknownHostException e) {
            System.out.println("未知的主机异常");
            return 10;
        }
        if ("".equals(html)) {
            System.out.println("操作失败！");
            return -2;
        }
        return getOperatingState(html);
    }

    /**
     * 得到注销连接状态码
     *
     * @param ip 连接到的IP地址
     * @return
     */
    public static int getDisconnectState(String ip) {
        String html;
        try {
            html = Disconnect.disconnect(ip);
        } catch (UnknownHostException e) {
            System.out.println("未知的主机异常");
            return 10;
        }
        if ("".equals(html)) {
            System.out.println("操作失败！");
            return -2;
        }
        return getOperatingState(html);
    }

    /**
     * 得到操作状态码
     *
     * @param html 服务器返回的网页源代码
     * @return 操作状态码
     */
    private static int getOperatingState(String html) {
//        System.out.println("网页代码：" + html);
        // 加工后的结果
        String info = "";
        // 连接状态
        int state = 0;
        String regex1 = "Authentication failed";
        String regex2 = "无法登录";
        String regex3 = "You are already online";
        String regex4 = "密码错误";
        String regex5 = "网络已断开";
        String regex6 = "网络已连接";
        String regex7 = "注销成功";
        String regex8 = "找不到网页";
        String regex9 = "Internal Server Error";
        String regex11 = "weui_btn_warn\" value=\"注销\"";
        String regex12 = "您似乎未曾连接到网络";
        String regex13 = "用户不存在";
        String regex14 = "已欠费";

        //将规则封装成对象
        Pattern p1 = Pattern.compile(regex1);
        Pattern p2 = Pattern.compile(regex2);
        Pattern p3 = Pattern.compile(regex3);
        Pattern p4 = Pattern.compile(regex4);
        Pattern p5 = Pattern.compile(regex5);
        Pattern p6 = Pattern.compile(regex6);
        Pattern p7 = Pattern.compile(regex7);
        Pattern p8 = Pattern.compile(regex8);
        Pattern p9 = Pattern.compile(regex9);
        Pattern p11 = Pattern.compile(regex11);
        Pattern p12 = Pattern.compile(regex12);
        Pattern p13 = Pattern.compile(regex13);
        Pattern p14 = Pattern.compile(regex14);

        //让正则对象和要作用的字符串相关联，获取匹配器对象
        Matcher m1 = p1.matcher(html);
        Matcher m2 = p2.matcher(html);
        Matcher m3 = p3.matcher(html);
        Matcher m4 = p4.matcher(html);
        Matcher m5 = p5.matcher(html);
        Matcher m6 = p6.matcher(html);
        Matcher m7 = p7.matcher(html);
        Matcher m8 = p8.matcher(html);
        Matcher m9 = p9.matcher(html);
        Matcher m11 = p11.matcher(html);
        Matcher m12 = p12.matcher(html);
        Matcher m13 = p13.matcher(html);
        Matcher m14 = p14.matcher(html);

        //将规则作用到字符串上，并进行符合规则的子串查找
        if (m1.find()) {
            msg1 = "验证失败";
            state = 1;
        } else if (m2.find()) {
            msg2 = "无法登录";
            state = 2;
        } else if (m3.find()) {
            msg3 = "已经在线了";
            state = 3;
        } else if (m4.find()) {
            msg4 = "密码错误";
            state = 4;
        } else if (m5.find()) {
            msg5 = "网络已断开";
            state = 5;
        } else if (m7.find()) {
            msg7 = "注销成功";
            state = 7;
        } else if (m8.find()) {
            msg8 = "无法连接到IPGW网关";
            state = 8;
        } else if (m9.find()) {
            msg9 = "内部服务器错误";
            state = 9;
        } else if (m11.find()) {
            msg11 = "连接成功";
            state = 11;
        } else if (m13.find()) {
            msg13 = "用户不存在";
            state = 13;
        } else if (m14.find()) {
            msg14 = "已欠费";
            state = 14;
        }
        if (m6.find()) {
            msg6 = "网络已连接";
            state = 6;
        }
        if (m12.find()) {
            msg12 = "您似乎未曾连接到网络";
            state = 12;
        }
        if (!msg6.equals("")) {
            msg5 = "";
        }
        info = msg1 + msg2 + msg3 + msg4 + msg5 + msg6 + msg7 + msg8 + msg9 + msg11 + msg12 + msg13 + msg14;
        if (info.equals("")) {
            state = 10;
        }
        return state;
    }

    /**
     * 得到连接状态信息
     *
     * @param operation 操作类型（连接或者断开）
     * @return 状态信息的字符串数组，长度为3
     */
    public static String[] getInformation(int operation) {
        if (operation == CONNECT || operation == CONNECT_PC) {
            information[0] = "";
            information[1] = "网络已连接";
            information[2] = "";
        } else if (operation == DISCONNECT) {
            information[0] = "";
            information[1] = "注销成功";
            information[2] = "";
        } else if (operation == DISCONNECT_ALL) {
            information[0] = "";
            information[1] = "网络已断开";
            information[2] = "";
        } else {
            return null;
        }
        return information;
    }
}
