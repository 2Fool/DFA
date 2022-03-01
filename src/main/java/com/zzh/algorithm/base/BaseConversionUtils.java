package com.zzh.algorithm.base;


/**
 * 36进制与10进制转换思路： 1、创建HashMap类型对象用于存放数字'0'到字母'Z'36个字符值键对
 * 2. 对值模除取余计算，递归调用
 *
 * @author guan.yuhui
 * @Date 2022/2/28
 */
public class BaseConversionUtils {

    /**
     * 十进制 转换成 N 进制
     *
     * @param val   十进制数据
     * @param radix 进制
     * @return 结果
     */
    static String TenTransToN(long val, int radix) {

        long temp = val;
        int len = 1; //目标进制的字符串长度
        while (temp >= radix) {
            temp = temp / radix;
            len++;
        }

        char[] buf = new char[len];
        formatLong(buf, val, radix, len);

        return new String(buf);
    }

    static void formatLong(char[] buf, long val, int radix, int len) {
        long temp;
        long radixPow;
        int charPos = len;
        --len;
        while (charPos != 0) {
            //Math.pow 方法某些数据较大时得不到正确的值。 如：Math.pow(15, 14) 结果的个位不是5. 见注释1
            radixPow = (long) Math.pow(radix, --charPos);
            temp = val;
            if (val >= radixPow) {
                val = temp % radixPow;
                buf[len - charPos] = digits[(int) (temp / radixPow)];
            } else {
                buf[len - charPos] = '0';
            }
        }
    }

    static char[] digits = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B',
            'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    public static void main(String[] args) {
        String arg = "1Y2P0IJ32E8E";
        //36进制转10进制
        long x = Long.valueOf(arg, 36);
        System.out.println(x);

        String s = TenTransToN(x, 36);
        System.out.println(s);

        System.out.println(arg.equals(s));
    }
}
