#include <jni.h>
#include <string>
extern "C"
jstring
Java_com_liying_ipgw_utils_KdApiUtils_getKdnAppKey(
        JNIEnv* env,
        jobject /* this */) {
    // 电商加密私钥，快递鸟提供，注意保管，不要泄漏
    std::string appKey = "在此填写你的APP_KEY";
    return env->NewStringUTF(appKey.c_str());
}
