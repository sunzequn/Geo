package com.sunzequn.geo.data.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloriac on 16/1/6.
 * <p>
 * List工具类
 */
public class ListUtils {

    public static <T extends List> boolean isEmpty(T t) {
        return t == null || t.size() == 0;
    }
}
