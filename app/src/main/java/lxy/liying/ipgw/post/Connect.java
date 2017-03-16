package lxy.liying.ipgw.post;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.net.UnknownHostException;

import lxy.liying.ipgw.url.IPGWUrl;

/**
 * 连接网络
 *
 * @author 李颖
 */
class Connect {

    private static Request<String> mRequest;

    /**
     * 静态方法，连接网络
     *
     * @param uid      用户名
     * @param password 密码
     * @return 成功：Post结果，网页源代码。 失败："提交数据失败"
     */
    static String connect(String uid, String password) throws UnknownHostException {
        // 创建请求
        mRequest = NoHttp.createStringRequest(IPGWUrl.POST_CONNECT_URL, RequestMethod.POST);
        mRequest.setUserAgent("Mozilla/5.0 (Linux; Android 4.4.4; Nexus 5 Build/KTU84P) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.114 Mobile Safari/537.36");
        mRequest.setAcceptLanguage("zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4");
        mRequest.add("action", "login")
                .add("ac_id", 1)
                .add("user_ip", "")
                .add("nas_ip", "")
                .add("user_mac", "")
                .add("username", uid)
                .add("password", password);
        // 调用同步请求，直接拿到请求结果。
        Response<String> response = NoHttp.startRequestSync(mRequest);
//        System.out.println("Code = " + response.responseCode());
        if (response.isSucceed()) {
            return response.get();
        } else {
            return "";
        }
    }

    /**
     * 取消请求
     */
    static void cancel() {
        if (mRequest != null) {
            mRequest.cancel();
        }
    }
}
