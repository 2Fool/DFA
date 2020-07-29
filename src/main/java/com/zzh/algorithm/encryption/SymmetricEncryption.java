package com.zzh.algorithm.encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 对称加密算法是应用比较早的算法，在数据加密和解密的时用的都是同一个密钥，这就造成了密钥管理困难的问题。
 * 常见的对称加密算法有DES、3DES、AES128、AES192、AES256 (默认安装的 JDK 尚不支持 AES256，
 * 需要安装对应的 jce 补丁进行升级 jce1.7，jce1.8)。其中AES后面的数字代表的是密钥长度。
 * 对称加密算法的安全性相对较低，比较适用的场景就是内网环境中的加解密。
 *
 * @Author: yuhui.guan
 * @Date: 2020/7/29 10:51
 */
public class SymmetricEncryption {

    /**
     * DES是对称加密算法领域中的典型算法，其密钥默认长度为56位。
     */
    public static String desEncrypt(byte[] dataSource, String password) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKeySpec = new DESKeySpec(password.getBytes());
            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, random);
            //正式执行加密操作
//            return Base64.encodeBase64String(cipher.doFinal(dataSource));
            return dataSource.toString();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    // 解密
    public static String desDecrypt(String src, String password) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象
        DESKeySpec desKeySpec = new DESKeySpec(password.getBytes());
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, secretKey, random);
        // 真正开始解密操作
//        return new String(cipher.doFinal(Base64.decodeBase64(src)));
        return src.toString();
    }

    /**
     * 3DES（即Triple DES）是DES向AES过渡的加密算法，它使用3条56位的密钥对数据进行三次加密。是DES的一个更安全的变形。
     * 它以DES为基本模块，通过组合分组方法设计出分组加密算法。比起最初的DES，3DES更为安全。密钥长度默认为168位，还可以选择128位。
     */
    public static String encryptThreeDESECB(String src, String key) {
        try {
            DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey securekey = keyFactory.generateSecret(dks);

            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, securekey);
            byte[] b = cipher.doFinal(src.getBytes("UTF-8"));

//            String ss = new String(org.apache.commons.codec.binary.Base64.encodeBase64(b));
            String ss = Base64.getEncoder().encodeToString(b);
            ss = ss.replaceAll("\\+", "-");
            ss = ss.replaceAll("/", "_");
            return ss;
        } catch (Exception ex) {
            ex.printStackTrace();
            return src;
        }
    }

    public static String decryptThreeDESECB(String src, String key) {
        try {
            src = src.replaceAll("-", "+");
            src = src.replaceAll("_", "/");
//            byte[] bytesrc = org.apache.commons.codec.binary.Base64.decodeBase64(src.getBytes("UTF-8"));
            byte[] bytesrc = Base64.getDecoder().decode(src.getBytes("UTF-8"));
            // --解密的key
            DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey securekey = keyFactory.generateSecret(dks);

            // --Chipher对象解密
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, securekey);
            byte[] retByte = cipher.doFinal(bytesrc);

            return new String(retByte, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return src;
        }
    }


    /**
     * AES 高级数据加密标准，能够有效抵御已知的针对DES算法的所有攻击，默认密钥长度为128位，还可以供选择192位，256位。
     * 这里顺便提一句这个位指的是bit。
     * 推荐使用对称加密算法有：AES128、AES192、AES256。
     */
    private static final String defaultCharset = "UTF-8";
    private static final String KEY_AES = "AES";
    private static final String KEY_MD5 = "MD5";
    private static MessageDigest md5Digest;

    static {
        try {
            md5Digest = MessageDigest.getInstance(KEY_MD5);
        } catch (NoSuchAlgorithmException e) {

        }
    }

    /**
     * 加密
     */
    public static String encrypt(String data, String key) {
        return doAES(data, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 解密
     */
    public static String decrypt(String data, String key) {
        return doAES(data, key, Cipher.DECRYPT_MODE);
    }


    /**
     * 加解密
     */
    private static String doAES(String data, String key, int mode) {
        try {
            boolean encrypt = mode == Cipher.ENCRYPT_MODE;
            byte[] content;
            if (encrypt) {
                content = data.getBytes(defaultCharset);
            } else {
//                content = Base64.decodeBase64(data.getBytes());
                content = java.util.Base64.getDecoder().decode(data.getBytes());
            }
            SecretKeySpec keySpec = new SecretKeySpec(md5Digest.digest(key.getBytes(defaultCharset))
                    , KEY_AES);
            Cipher cipher = Cipher.getInstance(KEY_AES);// 创建密码器
            cipher.init(mode, keySpec);// 初始化
            byte[] result = cipher.doFinal(content);
            if (encrypt) {
//                return new String(Base64.encodeBase64(result));
                return Base64.getEncoder().encodeToString(result);
            } else {
                return new String(result, defaultCharset);
            }
        } catch (Exception e) {
        }
        return null;
    }

}
