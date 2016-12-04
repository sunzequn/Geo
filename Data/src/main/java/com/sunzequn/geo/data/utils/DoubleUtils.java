package com.sunzequn.geo.data.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Sloriac on 16/2/13.
 */
public class DoubleUtils {

    private static final double ZERO = 0.0;

    public static boolean isZero(double d) {
        BigDecimal data1 = new BigDecimal(ZERO);
        BigDecimal data2 = new BigDecimal(d);
        int result = data1.compareTo(data2);
        return result == 0;
    }

    public static int compare(double d1, double d2) {
        BigDecimal data1 = new BigDecimal(d1);
        BigDecimal data2 = new BigDecimal(d2);
        return data1.compareTo(data2);
    }

    public static String m4(double d) {
        DecimalFormat df = new DecimalFormat("0.0000");
        return df.format(d);
    }

    public static double m2d(double d) {
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.valueOf(df.format(d));
    }
    public static void main(String[] args) {
        System.out.println(DoubleUtils.m4(200.99982287362163214124123213213));
    }

}
