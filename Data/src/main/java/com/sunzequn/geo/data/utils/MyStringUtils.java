package com.sunzequn.geo.data.utils;

import org.apache.commons.lang3.StringUtils;

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
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find()) {
            flg = true;
        }
        return flg;
    }

    public static void main(String[] args) {
        String s = "http://zh.dbpedia.org/resource/菲爾˙";
        System.out.println(isContainsChinese(s));
        System.out.println(after(s, "/"));
    }
}
