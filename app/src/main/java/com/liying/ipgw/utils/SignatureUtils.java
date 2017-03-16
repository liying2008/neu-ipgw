package com.liying.ipgw.utils;

import android.content.pm.PackageInfo;
import android.content.pm.Signature;

import java.security.MessageDigest;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/4 15:06
 * 版本：1.0
 * 描述：签名校验工具
 * 备注：
 * =======================================================
 */
public class SignatureUtils {
    /**
     * 检查数字签名
     *
     * @return true:数字签名正确，数字签名错误
     */
    public static boolean checkSignature(PackageInfo packageInfo, String comparison) {
        Signature[] signatures = packageInfo.signatures;
        Signature sign = signatures[0];
        String md5Signature = getMD5String(sign.toByteArray());
        if (md5Signature != null && comparison.equals(md5Signature)) {
            return true;
        }
        return false;
    }

    private static String getMD5String(byte[] paramArrayOfByte) {
        // ascii表对应的数字和字符的编码
        char[] asciiTable = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
        try {
            MessageDigest md5MessageDigest = MessageDigest.getInstance("MD5");
            md5MessageDigest.update(paramArrayOfByte);//
            byte[] tempByte = md5MessageDigest.digest();
            int i = tempByte.length;
            char[] tempChar = new char[i * 2];
            int j = 0;
            int k = 0;
            while (true) { // 将二进制数组转换成字符串
                if (j >= i) {
                    return new String(tempChar);
                }
                int m = tempByte[j];
                int n = k + 1;
                tempChar[k] = asciiTable[(0xF & m >>> 4)];
                k = n + 1;
                tempChar[n] = asciiTable[(m & 0xF)];
                j++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
