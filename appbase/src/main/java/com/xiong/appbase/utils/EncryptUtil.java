package com.xiong.appbase.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

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
     *
     * @param userId
     * @return
     */
    public static String extendUserID(String userId) {
        if (userId == null)
            return null;
        String id = userId;
        if (userId.length() < 32)
            id = makeMD5(userId);

        StringBuilder sb = new StringBuilder(id);
        sb.insert(32, id.substring(30, 32));
        sb.insert(24, id.substring(20, 22));
        sb.insert(16, id.substring(10, 12));
        sb.insert(8, id.substring(0, 2));
        return sb.toString();
    }

    //AES加密
    public static String AESEncrypt(String str, String key) {
        byte[] raw = key.getBytes();
        try {
            SecretKeySpec sk = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, sk);
            return parseByte2HexStr(cipher.doFinal(str.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    //AES解密
    public static String AESDecrypt(String str, String key) {
        byte[] raw = key.getBytes();
        try {
            SecretKeySpec sk = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
            cipher.init(Cipher.DECRYPT_MODE, sk);
            byte[] twoStr = parseHexStr2Byte(str);
            return new String(cipher.doFinal(twoStr), "utf-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    //将二进制转换成16进制
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    //将16进制转换为二进制
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
