package com.sunzequn.geo.data.algorithm.hanyu;

import org.junit.Test;

/**
 * Created by Sloriac on 16/1/8.
 */
public class PinyinTest {

    private Pinyin pinyin = new Pinyin();

    @Test
    public void getPinyinTest() {
        String[] pinyins = pinyin.getPinyin('孙');
        System.out.println(pinyins[0]);
    }
}
