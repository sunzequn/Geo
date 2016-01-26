package com.sunzequn.geo.data;

import java.net.URLEncoder;

/**
 * Created by Sloriac on 16/1/26.
 */
public class Test {

    public static void main(String[] args) {
        String s = "逻辑错误_(程序设计)";
        System.out.println(URLEncoder.encode(s));
    }
}
