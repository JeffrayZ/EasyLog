package com.zff.easylog;

import android.util.Base64;

import java.security.MessageDigest;

/**
 * Created by wulb
 * com.lwtx.base.rsautil
 * 功能描述:
 * 2016/6/16 17:24
 */

public class Coder {

    public static final String KEY_SHA = "SHA";
    public static final String KEY_MD5 = "MD5";

    /**
     * BASE64解密
     */
    public static byte[] decodeBase64(String key) throws Exception {
        return Base64.decode(key,Base64.DEFAULT);
    }

    /**
     * BASE64加密
     */
    public static String encodeBase64(byte[] key) throws Exception {
        return Base64.encodeToString(key,Base64.DEFAULT);
    }

    /**
     * MD5加密
     */
    public static byte[] encryptMD5(byte[] data) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);

        return md5.digest();

    }

    /**
     * SHA加密
     */
    public static byte[] encryptSHA(byte[] data) throws Exception {

        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);

        return sha.digest();

    }
}