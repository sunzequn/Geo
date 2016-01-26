package com.sunzequn.geo.data.utils;

import net.sf.chineseutils.ChineseUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sloriac on 15/12/20.
 */
public class MyStringUtils {

    public static String removePrefix(String string, String prefix) {
        return string.replaceFirst(prefix, "").trim();
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

    public static String encode(String string) throws UnsupportedEncodingException {
        char[] chs = string.toCharArray();
        String res = "";
        for (int i = 0; i < chs.length; i++) {
            char c = chs[i];
            if (MyStringUtils.isChinese(c)) {
                String temp = URLEncoder.encode(String.valueOf(c), "UTF-8");
                res = res + temp;
            } else {
                res = res + c;
            }
        }
        return res;
    }


    public static void main(String[] args) throws Exception {
//        String dir = "Data/src/main/resources/data/dbpedia/old/category.nt";
//        File file = new File(dir);
//        String s = "<http://zh.wikipedia.org/wiki/\\u5F6D\\u8428\\u79D1\\u62C9\\u7EA7\\u91CD\\u5DE1\\u6D0B\\u8230> <http://xmlns.com/foaf/0.1/primaryTopic> <http://zh.dbpedia.org/resource/\\u5F6D\\u8428\\u79D1\\u62C9\\u7EA7\\u91CD\\u5DE1\\u6D0B\\u8230> .\n";
//        System.out.println(s);
//        System.out.println(encode(s));

        String s = "菲爾˙普蘭提爾";
        System.out.println(ChineseUtils.tradToSimp(s));
    }
}
