package lxy.liying.ipgw.url;

/**
 * Post Url
 */
public class IPGWUrl {
	/** 连接网络的POST地址 */
	public static String POST_CONNECT_URL = "";
	/** 主机端连接网络的POST地址 */
	public static String PC_POST_CONNECT_URL = "";
	/** 断开网络的POST地址 */
	public static String POST_DISCONNECT_URL = "";
	/** 断开全部连接的POST地址 */
	public static String POST_DISCONNECTALL_URL = "";
	/** 获取详细信息的Post的服务器地址 */
	public static String POST_DETAILS = "";
	/** 获取详细信息的随机key */
	public static String KEY = "";

	/** 静态块：用来初始化信息 */
	static {
		POST_CONNECT_URL = "https://ipgw.neu.edu.cn/srun_portal_phone.php?ac_id=1&";
		POST_DISCONNECT_URL = "https://ipgw.neu.edu.cn/srun_portal_phone.php?ac_id=1&";
		PC_POST_CONNECT_URL = "https://ipgw.neu.edu.cn/srun_portal_pc.php?ac_id=1&";
		POST_DISCONNECTALL_URL = "https://ipgw.neu.edu.cn/include/auth_action.php";
		KEY = String.valueOf(getRandomKey());
		POST_DETAILS = "https://ipgw.neu.edu.cn/include/auth_action.php?k=" + KEY;
	}

	/**
	 * 得到5位的随机数key
	 * 
	 * @return
	 */
	private static int getRandomKey() {
		int key = (int) Math.floor(Math.random() * (100000 + 1));
		return key;
	}
}
