package com.zzh.algorithm.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 常见的不可逆加密算法有MD5，HMAC，SHA1、SHA-224、SHA-256、SHA-384，和SHA-512，其中SHA-224、SHA-256、SHA-384，
 * 和SHA-512我们可以统称为SHA2加密算法，SHA加密算法的安全性要比MD5更高，而SHA2加密算法比SHA1的要高。
 * 其中SHA后面的数字表示的是加密后的字符串长度，SHA1默认会产生一个160位的信息摘要。
 * <p>
 * 不可逆加密算法最大的特点就是密钥，但是HMAC是需要密钥的【手动狗头】。
 * <p>
 * 由于这些加密都是不可逆的，因此比较常用的场景就是用户密码加密，其验证过程就是通过比较两个加密后的字符串是否一样来确认身份的。
 * 网上也有很多自称是可以破解MD5密码的网站，其原理也是一样，就是有一个巨大的资源库，存放了许多字符串及对应的MD5加密后的字符串，
 * 通过你输入的MD5加密串来进行比较，如果过你的密码复杂度比较低，还是有很大机率验证出来的。
 *
 * @Author: yuhui.guan
 * @Date: 2020/7/29 9:59
 */
public class IrreversibleEncryption {

    /**
     * MD5信息摘要算法（英语：MD5 Message-Digest Algorithm），一种被广泛使用的密码散列函数，可以产生出一个128位（16字节）的散列值（hash value）
     * ，用于确保信息传输完整一致。
     *
     * @param text 输入信息
     * @return md5值
     */
    public static String md5(String text) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] bytes = messageDigest.digest(text.getBytes());
        return Hex.encodeHexString(bytes);
    }
}
