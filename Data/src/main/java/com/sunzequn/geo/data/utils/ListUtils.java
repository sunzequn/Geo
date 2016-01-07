package com.sunzequn.geo.data.utils;

import java.util.ArrayList;

/**
 * Created by Sloriac on 16/1/6.
 * <p>
 * List工具类
 */
public class ListUtils {

    public static <T extends ArrayList> boolean isEmpty(T t) {
        if (t == null || t.size() == 0) {
            return true;
        }
        return false;
    }
}
