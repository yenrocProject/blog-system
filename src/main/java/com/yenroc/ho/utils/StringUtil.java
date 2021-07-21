package com.yenroc.ho.utils;

import javax.validation.constraints.NotNull;

/**
 * @author： heyanpeng
 * @date： 2021/7/21
 */
public class StringUtil {

    /**
     * 将字符串的首字母转大写(A65~Z90 a97~z122)
     * @param str 需要转换的字符串
     * @return 首字母大写
     */
    public static String firstCharToUpperCase(@NotNull String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs=str.toCharArray();
        // 第一个字母为【a~z】
        if (97 <= cs[0] && cs[0] <= 122) {
            cs[0]-=32;
        }
        return String.valueOf(cs);
    }

    /**
     * 将字符串的首字母转小写(A65~Z90 a97~z122)
     * @param str 需要转换的字符串
     * @return 首字母小写
     */
    public static String firstCharToLowerCase(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs=str.toCharArray();
        // 第一个字母为【A-Z】
        if (65 <= cs[0] && cs[0] <= 97) {
            cs[0] += 32;
        }
        return String.valueOf(cs);
    }
}
