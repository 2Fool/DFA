package com.zzh.algorithm.encryption;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * new String(Base64.encodeBase64(bytes[])) = java.util.Base64.getEncoder().encodeToString((bytes[]))
 * new String(Base64.decodeBase64(bytes[])) = java.util.Base64.getDecoder().decode(bytes)
 *
 * @Author: yuhui.guan
 * @Date: 2020/7/29 17:30
 */
public class Base64Test {
    public void test() throws UnsupportedEncodingException {
        final Base64.Decoder decoder = Base64.getDecoder();
        final Base64.Encoder encoder = Base64.getEncoder();
        final String text = "字串文字";
        final byte[] textByte = text.getBytes("UTF-8");
        //编码
        final String encodedText = encoder.encodeToString(textByte);
        System.out.println(encodedText);
        //解码
        System.out.println(new String(decoder.decode(encodedText), "UTF-8"));
    }
}
