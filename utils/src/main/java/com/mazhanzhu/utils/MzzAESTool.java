package com.mazhanzhu.utils;

import android.text.TextUtils;

import java.security.AlgorithmParameters;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2022/1/11 14:39
 * Desc   : AES加密解密
 */
public class MzzAESTool {
    public static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final String AES_Algorithm = "AES";
    /**
     * AES转变
     * <p>法算法名称/加密模式/填充方式</p>
     * <p>加密模式有：电子密码本模式ECB、加密块链模式CBC、加密反馈模式CFB、输出反馈模式OFB</p>
     * <p>填充方式有：NoPadding、ZerosPadding、PKCS5Padding</p>
     * <p>ECB模式只用密钥即可对数据进行加密解密，CBC模式需要添加一个参数iv</p>
     */
    public static String AES_Transformation1 = "AES/ECB/NoPadding";
    public static String AES_Transformation2 = "AES/CBC/PKCS5Padding";

    /**
     * 默认采用 “AES/ECB/NoPadding”
     *
     * @param data 需要加密的字符串
     * @param key  加密的key
     * @return 16进制大写字符串
     */
    public static String encryptAES(String data, String key) {
        return AESTemplet(data, key, "", AES_Transformation1, true);
    }

    /**
     * 默认采用 “AES/CBC/PKCS5Padding”
     *
     * @param data 需要加密的字符串
     * @param key  加密的key
     * @param iv   加密偏移量
     * @return 16进制大写字符串
     */
    public static String encryptAES(String data, String key, String iv) {
        return AESTemplet(data, key, iv, AES_Transformation2, true);
    }

    /**
     * @param data    需要加密的字符串
     * @param key     加密的key
     * @param iv      加密偏移量
     * @param AESTYPE 指定加密模式 比如 “AES/CBC/PKCS5Padding”这种类似的
     * @return 16进制大写字符串
     */
    public static String encryptAES(String data, String key, String iv, String AESTYPE) {
        return AESTemplet(data, key, iv, AESTYPE, true);
    }

    /**
     * 默认采用 “AES/ECB/NoPadding”
     *
     * @param data 需要解密的字符串
     * @param key  加密的key
     * @return 16进制大写字符串
     */
    public static String decryptAES(String data, String key) {
        return AESTemplet(data, key, "", AES_Transformation1, false);
    }

    /**
     * 默认采用 “AES/CBC/PKCS5Padding”
     *
     * @param data 需要解密的字符串
     * @param key  加密的key
     * @param iv   加密偏移量
     * @return 16进制大写字符串
     */
    public static String decryptAES(String data, String key, String iv) {
        return AESTemplet(data, key, iv, AES_Transformation2, false);
    }

    /**
     * @param data    需要解密的字符串
     * @param key     加密的key
     * @param iv      加密偏移量
     * @param AESTYPE 指定加密模式 比如 “AES/CBC/PKCS5Padding”这种类似的
     * @return 16进制大写字符串
     */
    public static String decryptAES(String data, String key, String iv, String AESTYPE) {
        return AESTemplet(data, key, iv, AESTYPE, false);
    }


    private static String AESTemplet(String Date, String AESKEY, String IVVAL, String AESTYPE, boolean isEncrypt) {
        try {
            byte[] mDate = Date.getBytes();
            byte[] mKey = AESKEY.getBytes();

            Cipher cipher = Cipher.getInstance(AESTYPE);
            SecretKey secretKey = new SecretKeySpec(mKey, AES_Algorithm);//转化为密钥
            if (!TextUtils.isEmpty(IVVAL)) {
                byte[] iv = IVVAL.getBytes();
                AlgorithmParameters params = AlgorithmParameters.getInstance(AES_Algorithm);
                params.init(new IvParameterSpec(iv));
                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey, params);
            } else {
                SecureRandom random = new SecureRandom();
                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey, random);
            }
            byte[] encryptData = cipher.doFinal(mDate);
            return bytes2HexString(encryptData);
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * byteArr转hexString
     * <p>例如：</p>
     * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8
     *
     * @param bytes byte数组
     * @return 16进制大写字符串
     */
    private static String bytes2HexString(byte[] bytes) {
        char[] ret = new char[bytes.length << 1];
        for (int i = 0, j = 0; i < bytes.length; i++) {
            ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /**
     * 把字节数组转换成16进制字符串
     *
     * @param bArray
     * @return
     */
    private static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toLowerCase());
        }
        return sb.toString();
    }
}
