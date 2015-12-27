package com.sunzequn.geocities.data.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloriac on 15/12/20.
 */
public class StringUtil {

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
            strings.add(str.trim());
        }
        return strings;
    }
}
