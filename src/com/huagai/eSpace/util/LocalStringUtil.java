package com.huagai.eSpace.util;

import com.huawei.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 类名称：LocalStringUtil
 */
public final class LocalStringUtil {
    /**
     * Default constructor
     */
    private LocalStringUtil() {
    }

    public static boolean contains(String whole, String part) {
        if (StringUtil.isStringEmpty(whole) || StringUtil.isStringEmpty(part)) {
            return false;
        }

        return whole.toLowerCase(Locale.getDefault()).indexOf(part.toLowerCase(Locale.getDefault())) > -1;
    }

    public static boolean containsDisContinuous(String whole, String part) {
        if (whole == null || part == null) {
            return false;
        }

        whole = whole.toLowerCase(Locale.getDefault());
        part = part.toLowerCase(Locale.getDefault());

        int index = -1;

        for (int i = 0; i < part.length(); i++) {
            index = whole.indexOf(part.charAt(i), index + 1);

            if (index == -1) {
                return false;
            }
        }

        return true;
    }

    public static String remove(String source, int pos, char c) {
        String result = source;
        if ((source == null) || "".equals(source)) {
            return "";
        }
        if ((pos < 0) || (pos >= source.length())) {
            return result;
        }
        if (c == source.charAt(pos)) {
            result = source.substring(pos + 1);
        }
        return result;
    }

    public static String findElementToEnd(String source, String startTag) {
        if (source == null) {
            return null;
        }

        int i = source.indexOf(startTag);
        int j = source.length() - 1;

        if ((i != -1) && (j != -1)) {
            String s = source.substring(i + startTag.length(), j);
            return s;
        }

        return null;
    }

    public static String replaceNull(String text) {
        return null == text ? "" : text;
    }

    public static String replaceAllSymbol(String number) {
        if (null == number) {
            return null;
        }

        String regx = "\\D";

        return number.replaceAll(regx, "");
    }

    public static boolean matchNumber(String source, String com) {
        return StringUtil.matches(source, com, false);
    }

    /**
     * 匹配手机号码
     *
     * @param source        被匹配的字符串
     * @param com           数据源
     * @param isExactSearch 是否精确匹配，精确置true
     * @return
     */
    public static boolean matchNumber(String source, String com,
                                      boolean isExactSearch) {
        return StringUtil.matches(source, com, isExactSearch);
    }

    public static int sizeOf(String str) {
        if (StringUtil.isStringEmpty(str)) {
            return 0;
        }

        return str.length();
    }

//    public static boolean isMatch(LocalContact local, String number)
//    {
//        if (local == null || StringUtil.isStringEmpty(number))
//        {
//            return false;
//        }
//
//        final List<PhoneNumber> nums = local.getNumbers();
//        if (nums != null && !nums.isEmpty())
//        {
//            synchronized (nums)
//            {
//                for (PhoneNumber num : nums)
//                {
//                    if (LocalStringUtil.matchNumber(number, num.getNumber()))
//                    {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }

    /**
     * 用来计算连个布尔值的逻辑异或结果
     *
     * @param param_a
     * @param param_b
     * @return
     */
    public static boolean boolXOR(boolean param_a, boolean param_b) {
        return (param_a && !param_b) || (!param_a && param_b);
    }

    /**
     * 用来查询int[]数组中的元素
     *
     * @param array 待查询数组
     * @param value 查询值
     * @param lenth 数组长度
     * @return 返回元素的索引位置
     */
    public static int queryElement(int[] array, int value, int lenth) {
        if (array == null) {
            return -1;
        }

        for (int i = 0; i < lenth; i++) {
            if (array[i] == value) {
                return i;
            }
        }

        return -1;
    }

    public static List<String> findAllElement(String source, String start, String end) {
        if (source == null || start == null || end == null) {
            return null;
        }

        List<String> result = new ArrayList<String>();

        String temp = null;

        do {
            temp = null;

            int i = source.indexOf(start);
            int j = source.indexOf(end, i);

            if ((i != -1) && (j != -1) && j > i) {
                temp = source.substring(i + start.length(), j);
                result.add(temp);
            }
        }
        while (temp != null);

        return result;
    }

    public static StringPosInfo queryElementPos(String source, String start, String end) {
        if (source == null || start == null || end == null) {
            return null;
        }

        int i = source.indexOf(start);
        int j = source.indexOf(end, i);

        if ((i != -1) && (j != -1) && j > i) {
            StringPosInfo info = new StringPosInfo();

            String s = source.substring(i + start.length(), j);

            info.content = s;
            info.startPos = i;
            info.endPos = j + end.length();

            return info;
        }

        return null;
    }

    private static class StringPosInfo {
        public String content;
        public int startPos;
        public int endPos;
    }
}
