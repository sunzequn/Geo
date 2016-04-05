package com.sunzequn.geo.data.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.regex.Pattern;

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

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException ex) {
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(StringUtils.isInteger("1213.2"));
        System.out.println(StringUtils.isDouble("1213"));
        System.out.println(StringUtils.isInteger("1213"));
        System.out.println(StringUtils.isInteger("121sdsd"));
    }
}
