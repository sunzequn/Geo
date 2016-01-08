package com.sunzequn.geo.data.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloriac on 15/12/20.
 */
public class StringUtils {

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
}
