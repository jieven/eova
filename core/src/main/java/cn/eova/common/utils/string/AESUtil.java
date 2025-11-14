/**
 * Copyright (c) 2015-2026 EOVA.CN. All rights reserved.
 * Licensed under the LGPL-3.0 license
 * For authorization, please contact: admin@eova.cn
 */
package cn.eova.common.utils.string;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import cn.eova.tools.x;

public class AESUtil {

    private static String getKey() {
        // 之前的key = yunyou17lw
        final String key = "eova.cn";
        return x.conf.get("aes.key", key);
    }

    /**
     * 加密
     * @param content 需要加密的内容
     * @param password  加密密码
     * @return
     */
    private static byte[] encryptByte(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    private static byte[] decryptByte(byte[] content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES加密
     * @param str
     * @return
     */
    public static String encrypt(String str) {
        return BinaryHex.binary2Hex(encryptByte(str, getKey()));
    }

    /**
     * AES解密
     * @param str
     * @return
     */
    public static String decrypt(String str) {
        try {
            // 16进制转2进制
            byte[] decryptFrom = BinaryHex.hex2Byte(str);
            // 根据byte进行解码
            byte[] decryptResult = decryptByte(decryptFrom, getKey());
            return new String(decryptResult);
        } catch (Exception e) {
            throw new RuntimeException("AES解密异常,请检查是否AES密文, 密文=" + str);
        }
    }

    public static void main(String[] args) {

        String str = "root";

        // 加密
        System.out.println("加密前：" + str);
        String s = AESUtil.encrypt(str);
        System.out.println("加密后：" + s);

        System.out.println("解密后：" + AESUtil.decrypt(s));
    }
}