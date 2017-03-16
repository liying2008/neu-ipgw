package lxy.liying.ipgw.info;


import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.Locale;

import lxy.liying.ipgw.url.IPGWUrl;

/**
 * 得到在线信息类
 */
public class GetConnInfo {
	/**
	 * 得到连接用户的信息 例如： <br />
	 * 45223109,12623,45.00,,0,58.154.209.141	<br />
	 * 45278986,13189,45.00,,0,58.154.209.141 	<br />
	 * 流量，时长，余额，，0，IP地址
	 * 
	 * @return
	 */
	private static String getConnInfoHtml() {
        // 创建请求
        Request<String> request = NoHttp.createStringRequest(IPGWUrl.POST_DETAILS, RequestMethod.POST);
        request.add("action", "get_online_info")
                .add("key",  IPGWUrl.KEY);
        // 调用同步请求，直接拿到请求结果。
        Response<String> response = NoHttp.startRequestSync(request);
        if (response.isSucceed()) {
            return response.get();
        } else {
            return "";
        }
    }
	
	public static String[] getConnInfo() {
		String html = getConnInfoHtml();
//		System.out.println("在线信息："+html);
		String fluxInfo;
		String timeInfo;
		String moneyInfo;
		String ipAddrInfo;
		/*
		 * 45278986,13189,45.00,,0,58.154.209.141 <br />
		 * 流量，时长，余额，，0，IP地址 <br />
		 */
		try {
			String[] splitStr = html.split(",");
			int sec = Integer.parseInt(splitStr[1]);
			long bytes = Long.parseLong(splitStr[0]);
			fluxInfo = format_flux(bytes);
			timeInfo = format_time(sec);
			moneyInfo = splitStr[2] + "元";
			ipAddrInfo = splitStr[5].replace("\n", "");
			
		} catch (Exception e) {
			return null;
		}
		return new String[]{fluxInfo, timeInfo, moneyInfo, ipAddrInfo};
	}

	/**
	 * 格式化时间
	 * 
	 * @return
	 * 格式：09 : 08 : 31
	 */
	private static String format_time(int sec) {
		int h = sec / 3600;
		int m = (sec % 3600) / 60;
		int s = sec % 3600 % 60;
		String out = "";
		if (h < 10) {
			out += "0" + h + ":";
		} else {
			out += h + ":";
		}

		if (m < 10) {
			out += "0" + m + ":";
		} else {
			out += m + ":";
		}

		if (s < 10) {
			out += "0" + s + "";
		} else {
			out += s + "";
		}
		return out;
	}
	/**
	 * 格式化流量
	 * @param bytes
	 * @return
	 */
	private static String format_flux(long bytes) {
		if (bytes > (1024 * 1024 * 1024)) {
			// 以G为单位
			double rlt = (double)bytes / (double)(1024 * 1024 * 1024);
			return String.format(Locale.getDefault(), "%.3f", rlt) + "GB";
		} else if (bytes > (1024 * 1024)) {
			// 以M为单位
			double rlt = (double)bytes / (double)(1024 * 1024);
			return String.format(Locale.getDefault(), "%.2f", rlt) + "MB";
		} else if (bytes > 1024) {
			// 以K为单位
			double rlt = (double)bytes / (double)(1024);
			return String.format(Locale.getDefault(), "%.2f", rlt) + "KB";
		} else {
			// 以B为单位
			return String.valueOf(bytes) + "B";
		}
	}
}
