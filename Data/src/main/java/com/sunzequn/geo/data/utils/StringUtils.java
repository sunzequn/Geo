package com.sunzequn.geo.data.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by Sloriac on 16/1/21.
 */
public class StringUtils {

    public static boolean isNullOrEmpty(String string) {
        if (string == null || string.equals("")) {
            return true;
        }
        return false;
    }

    public static String removeEnd(String string, String remove) {
        string = string.trim();
        return org.apache.commons.lang3.StringUtils.removeEnd(string, remove);
    }

    public static String removeStart(String string, String remove) {
        string = string.trim();
        return org.apache.commons.lang3.StringUtils.removeStart(string, remove);
    }

    public static InputStream string2InputStream(String string) {
        ByteArrayInputStream stream = new ByteArrayInputStream(string.getBytes());
        return stream;
    }
}
