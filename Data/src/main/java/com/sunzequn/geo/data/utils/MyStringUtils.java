package com.sunzequn.geo.data.utils;

import net.sf.chineseutils.ChineseUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sloriac on 15/12/20.
 */
public class MyStringUtils {

    public static String removePrefix(String string, String prefix) {
        return StringUtils.removeStart(string, prefix);
    }

    public static String removeSuffix(String string, String suffix) {
        return string.replace(suffix, "").trim();
    }

    public static String remove(String string, String prefix, String suffix) {
        String temp = removePrefix(string, prefix);
        return removeSuffix(temp, suffix);
    }

    public static List<String> split(String string, String division) {
        List<String> strings = new ArrayList<>();
        String[] array = string.split(division);
        for (String str : array) {
            if (!org.apache.commons.lang3.StringUtils.isEmpty(str.trim())) {
                strings.add(str.trim());
            }
        }
        if (strings.size() > 0) {
            return strings;
        }
        return null;
    }

    public static String after(String string, String division) {
        String[] strings = StringUtils.split(string, division);
        return strings[strings.length - 1];
    }

    public static boolean isContainsChinese(String str) {
        if (str == null)
            return false;
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find()) {
            flg = true;
        }
        return flg;
    }

    public static String encode(String string, String division) throws UnsupportedEncodingException {
        if (!string.contains(division)) {
            return string;
        }
        boolean isEnded = false;
        if (string.endsWith(division)) {
            isEnded = true;
        }
        String[] strings = StringUtils.split(string, division);
        String res = "";
        for (String s : strings) {
            s = encode(s, ":");
            if (isContainsChinese(s)){
                s.replace(" ", "_");
                s = URLEncoder.encode(s, "UTF-8");
            }
            res = res + s + division;
        }
        if (!isEnded){
            res = StringUtils.removeEnd(res, division);
        }
        return res;
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    public static boolean containsChinese(String string) {
        char[] chs = string.toCharArray();
        for (char ch : chs) {
            if (isChinese(ch)) {
                return true;
            }
        }
        return false;
    }

    public static String encode(String string) throws UnsupportedEncodingException {

        String prefix = "http://";
        if (string.startsWith(prefix)){
            string = StringUtils.removeStart(string, prefix);
        }else {
            prefix = "";
        }
        Set<Character> set = new HashSet<>();
        set.add('·');
        set.add(':');
        set.add('˙');
        char[] chs = string.toCharArray();
        String res = "";
        for (int i = 0; i < chs.length; i++) {
            char c = chs[i];
            if (MyStringUtils.isChinese(c) || set.contains(c)) {
                String temp = URLEncoder.encode(String.valueOf(c), "UTF-8");
                res = res + temp;
            } else {
                res = res + c;
            }
        }
        return prefix + res;
    }

    static public String filterOffUtf8Mb4(String text) throws UnsupportedEncodingException {
        byte[] bytes = text.getBytes("utf-8");
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        int i = 0;
        while (i < bytes.length) {
            short b = bytes[i];
            if (b > 0) {
                buffer.put(bytes[i++]);
                continue;
            }
            b += 256;
            if ((b ^ 0xC0) >> 4 == 0) {
                buffer.put(bytes, i, 2);
                i += 2;
            } else if ((b ^ 0xE0) >> 4 == 0) {
                buffer.put(bytes, i, 3);
                i += 3;
            } else if ((b ^ 0xF0) >> 4 == 0) {
                i += 4;
            }
        }
        buffer.flip();
        return new String(buffer.array(), "utf-8");
    }


    public static void main(String[] args) throws Exception {
//        String dir = "Data/src/main/resources/data/dbpedia/old/category.nt";
//        File file = new_test File(dir);
//        String s = "<http://zh.wikipedia.org/wiki/\\u5F6D\\u8428\\u79D1\\u62C9\\u7EA7\\u91CD\\u5DE1\\u6D0B\\u8230> <http://xmlns.com/foaf/0.1/primaryTopic> <http://zh.dbpedia.org/resource/\\u5F6D\\u8428\\u79D1\\u62C9\\u7EA7\\u91CD\\u5DE1\\u6D0B\\u8230> .\n";
//        System.out.println(s);
//        System.out.println(encode(s));

//        String s = "\u83f2\u723e\u02d9\u666e\u862d\u63d0\u723e";
//        String[] strings = StringUtils.split(s, "u");
//        System.out.println(strings.length);
//        System.out.println(s);
//        char[] chs = s.toCharArray();
//        for (char ch : chs) {
//            if (isChinese(ch))
//                System.out.println(ch);
//        }

        String s = "http://zh.dbpedia.org/resource/wiki:";
        System.out.println(encode(s));

    }
}
