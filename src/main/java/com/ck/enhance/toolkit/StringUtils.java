package com.ck.enhance.toolkit;

/**
 * @author 陈坤
 * @serial 2019/1/9
 */
public class StringUtils {

    /**
     * 驼峰转下划线
     * <p>
     * 处理类时 类第一个字母大写, 字段第一个小写!! 这里使用 isClass 区分下
     *
     * @param str     处理str
     * @param isClass 处理的是否为 类
     * @return .
     * @author 陈坤
     */
    public static String humpConversion(String str, boolean isClass) {
        StringBuilder ss = new StringBuilder();
        char[] chars = str.toCharArray();
        int i = 0;
        if (isClass) {
            i = 1;
            ss.append(chars[0]);
        }

        for (; i < chars.length; i++)
            if (isUpper(chars[i])) {
                ss.append("_").append(chars[i]);
            } else {
                ss.append(chars[i]);
            }
        return ss.toString().toLowerCase();
    }

    /**
     * 判断字符是否为大写
     *
     * @param ct 字符
     * @return boolean
     * @author 陈坤
     */
    public static boolean isUpper(Character ct) {
        return ct >= 'A' && ct <= 'Z';
    }

}
