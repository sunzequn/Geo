package com.sunzequn.geo.data.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sloriac on 15/12/20.
 */
public class DateUtils {

    public static Date toDate(String string, String fromat) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fromat);
            return simpleDateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
