package com.sunzequn.geo.data.utils;

import java.math.BigDecimal;

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


}
