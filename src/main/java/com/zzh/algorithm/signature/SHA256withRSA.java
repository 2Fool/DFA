package com.zzh.algorithm.signature;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 使用SHA256withRSA进行签名
 */
public class SHA256withRSA {
    /**
     * RSA256
     */
    private static final String RSA_256 = "SHA256withRSA";
    private static final String UTF_8 = "UTF-8";

    /**
     * signPrivateKey
     */
    private static final String SIGNATURE_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDTc+GNzoQOJ6LkOPUPv/837zzhMmjBtfP/PWLsWin++uMJXwhsDjqZTXMsvUz5EF02KvyOpnVT32KOQ9Y7ZSknHm/a/GZyyM4AI4gKJoci1cwoHk8oaEMNHFv18imADJwOSyb1tg1oyrkNqafP6+D5WwHIy7jqYn69yjaxGBhMaBvJzDB9APQ3Uk1alaVOJiORTl3B2OTb03VDi1zQLnDjfNERZYePo+DhWTWne3rgg5sMx9J75hZyJDIehGlGKDvpN+RDhyr526dQourE4BH8AXWKmpv7L/MUu+2XYKvG9YBpCV0VU14Hd6S+ivCKvnP01usHXDupNggJ+uZP5JlNAgMBAAECggEBAI86p0XO5VRRNN0yV4zbmJziyHIiGyNbhHMXubIvQNMeTjtCzAmlebvt4l4ju8fZdcoDRB+8US0G21NSUALbewysaUgEP8Dwk9k0OCnn/xFxkGB8Z1IPjZuL4h6ucb3yzKJB9ZnqPxsEdmTyzmDgfftp0cOZeyAIp6EXIATFNXBKuIG8lpwjO0TaAW+bMdgr9zxcZETrb0sAYs08m6gfMQwuWCq9bUmABSdhpqcxXtMbq71o64c5LKRh1xVgWe8leLlw1HNLV7yC7Nk1r3KoacxTRxwwy+I7qrAnfZqmnZBydyqsj64jtNitNrXtg576JeoONDfdTG4xtQ93XAOZgwECgYEA/LcGA1xiiFHVaA75QdDqF5palCIA0PuZKVkNqYNqW2svLyk8cWMZgFF9wnmOEqAuvBQRiDY/wO3/YF6oZ/1yLOI/0l97WsZTBbEBBPBvGv51HjNkRPfwD6PviOl5wuGZoWE4oR0EyDp+ljyHX3XW3NU5gG2SApmOOqqQiBcidtUCgYEA1jOL3D/jKkr0BlF9SSCEV8L751PQ8tpHOhNemcKffhVZL+cFYN8wSM5gVBR2BdIaGjOw7uEA36gjL0S61pzRUyyPQ70Ega5rpqysgUE6IaMwDW+1zxVKbMLESVr5XL6yss3ZWQrJj/ZjxOfDG6TDwwTs6n8dkCSQq4yl3IVPRJkCgYA09oqtE6SW20e1ekXk9ErLTY8kMognREOSNda2KxOUOz91S9geD13d1bZclqse3jFNO4t9F5l+7qIx6US0HpraK0Si613n5V6q97C1/0nZx3B2NuERz0ChloLyF7RsEmnnN3/tzC4fZJr5E5BvgjvYpltZvhz2rIXxZ6PI0choTQKBgQCwJ2P1lXSz60ATkiB+awdrRSb0brF/hpLc5+D8glm3zsax5kM+D04eEdqWSt1knAxrT+dKDDAzvopw7QzaQczDofmPs3ppS4+sWoTjJ0kvMIzr/9p8mv3Bw8q4qOA5rXo4IaGE4KIyimYyIIcLzMxRLVPI5RDi4a835Urht6AgwQKBgQDvANr+qUXRVR2U6Zq4H+4z7qq0RNeLdID2wtyPOvW83j7tGGjuX6KrFi8Cayii5vXn3rp6scol1TijoRm1W8NG/BCjmil/elGc9+GZHBvDLZIGLNzrjQ4Cg630LOCVjzD2ktK8cVc1fFxF/w2Vp9KCOg69thPAU23ocnrDe9ZlXw==";

    /**
     * verifyPublicKey
     */
    private static final String SIGNATURE_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA03Phjc6EDiei5Dj1D7//N+884TJowbXz/z1i7Fop/vrjCV8IbA46mU1zLL1M+RBdNir8jqZ1U99ijkPWO2UpJx5v2vxmcsjOACOICiaHItXMKB5PKGhDDRxb9fIpgAycDksm9bYNaMq5Damnz+vg+VsByMu46mJ+vco2sRgYTGgbycwwfQD0N1JNWpWlTiYjkU5dwdjk29N1Q4tc0C5w43zREWWHj6Pg4Vk1p3t64IObDMfSe+YWciQyHoRpRig76TfkQ4cq+dunUKLqxOAR/AF1ipqb+y/zFLvtl2CrxvWAaQldFVNeB3ekvorwir5z9NbrB1w7qTYICfrmT+SZTQIDAQAB";

    public static void main(String[] args) throws Exception {
        // 加密密文
        String content = "{\"notifyType\":\"PAYMENT_RESULT\",\"payToAmount\":{\"currency\":\"HKD\",\"value\":\"2\"},\"payToId\":\"20211202154010800100188040200954708\",\"payToRequestId\":\"2604339906634473472\",\"paymentAmount\":{\"currency\":\"HKD\",\"value\":\"2\"},\"paymentDetailSummaries\":[{\"customerId\":\"2188240109083042\",\"extendInfo\":{\"payProvider\":\"WF_WF_WF\",\"authCode\":\"\",\"cardIndexNo\":\"2021092215027900188291400049604\"},\"paymentAmount\":{\"currency\":\"HKD\",\"value\":\"2\"},\"paymentMethodType\":\"WALLET_WF\"}],\"paymentId\":\"20211202150077000001188040200167137\",\"paymentTime\":\"2021-12-02T07:40:19Z\",\"result\":{\"resultCode\":\"SUCCESS\",\"resultMessage\":\"success.\",\"resultStatus\":\"S\"}}";

        String signature = sign(content, SIGNATURE_PRIVATE_KEY);

        boolean res = verify(content, signature, SIGNATURE_PUBLIC_KEY);
        if (res) {
            System.out.println("verify success.");
        }
    }

    /**
     * 签名
     */
    public static String sign(String content, String merchantPrivateKey) throws Exception {

        // 通过私钥进行加密
        String originalString = signWithSHA256RSA(content, merchantPrivateKey);
        // 对加密加过进行 URLEncoder
        return URLEncoder.encode(originalString, UTF_8);
    }

    /**
     * 通过公钥进行验签
     */
    public static boolean verify(String content, String signature, String alipayPublicKey) throws Exception {

        String decodedString = URLDecoder.decode(signature, UTF_8);

        return verifySignatureWithSHA256RSA(content, decodedString, alipayPublicKey);
    }


    /**
     * Generate base64 encoded signature using the sender's private key
     *
     * @param reqContent:    the original content to be signed by the sender
     * @param strPrivateKey: the private key which should be base64 encoded
     * @return
     * @throws Exception
     */
    private static String signWithSHA256RSA(String reqContent, String strPrivateKey) throws Exception {
        Signature privateSignature = Signature.getInstance(RSA_256);
        privateSignature.initSign(getPrivateKeyFromBase64String(strPrivateKey));
        privateSignature.update(reqContent.getBytes(UTF_8));
        byte[] bytes = privateSignature.sign();

        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * @param privateKeyString 私钥
     */
    private static PrivateKey getPrivateKeyFromBase64String(String privateKeyString) throws Exception {
        byte[] b1 = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b1);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    /**
     * Verify if the received signature is correctly generated with the sender's public key
     *
     * @param rspContent: the original content signed by the sender and to be verified by the receiver.
     * @param signature:  the signature generated by the sender
     * @param strPk:      the public key string-base64 encoded
     */
    private static boolean verifySignatureWithSHA256RSA(String rspContent, String signature, String strPk) throws Exception {
        PublicKey publicKey = getPublicKeyFromBase64String(strPk);

        Signature publicSignature = Signature.getInstance(RSA_256);
        publicSignature.initVerify(publicKey);
        publicSignature.update(rspContent.getBytes(UTF_8));

        byte[] signatureBytes = Base64.getDecoder().decode(signature);
        return publicSignature.verify(signatureBytes);
    }

    /**
     * @param publicKeyString 公钥
     */
    private static PublicKey getPublicKeyFromBase64String(String publicKeyString) throws Exception {
        byte[] b1 = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(b1);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(X509publicKey);
    }
}