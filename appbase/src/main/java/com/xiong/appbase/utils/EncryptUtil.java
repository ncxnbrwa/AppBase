package com.xiong.appbase.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by JI on 2016/1/12.
 * 加密工具类
 */

public class EncryptUtil {
    private static final String TAG = EncryptUtil.class.getName();
    // MD5加密,32位
    public static String makeMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (byte anEncryption : encryption) {
                if (Integer.toHexString(0xff & anEncryption).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & anEncryption));
                } else {
                    strBuf.append(Integer.toHexString(0xff & anEncryption));
                }
            }
            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return info;
        } catch (UnsupportedEncodingException e) {
            return info;
        }
    }

    /**
     * 如果字符串长度少于32位，则用md5扩展到32位
     * 字符串变形，在8,16,24,32位置后增加【0,2】【10,12】【20,22】【30,32】子串
     * @param userId
     * @return
     */
    public static String extendUserID(String userId) {
        if(userId == null)
            return null;
        String id = userId;
        if (userId.length() < 32)
            id = makeMD5(userId);

        StringBuilder sb = new StringBuilder(id);
        sb.insert(32,id.substring(30,32));
        sb.insert(24,id.substring(20,22));
        sb.insert(16,id.substring(10,12));
        sb.insert(8,id.substring(0,2));
        return sb.toString();
    }
}
