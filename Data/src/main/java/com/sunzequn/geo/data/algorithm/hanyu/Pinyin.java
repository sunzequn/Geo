package com.sunzequn.geo.data.algorithm.hanyu;

import com.sunzequn.geo.data.alignment.sameas.SameModel;
import com.sunzequn.geo.data.utils.MyStringUtils;
import com.sunzequn.geo.data.utils.StringUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by Sloriac on 16/1/8.
 */
public class Pinyin {


    /**
     * SunZeQun
     *
     * @param hanzi
     * @return
     */
    public String getPinyinWithFirstOneUpper(String hanzi) {
        if (StringUtils.isNullOrEmpty(hanzi)) {
            return null;
        }
        char[] chars = hanzi.trim().toCharArray();
        String res = "";
        for (char ch : chars) {
            String s = getPinyin(ch);
            if (s == null) {
                return null;
            }
            s = StringUtils.toUpperCaseFirstOne(s);
            res = res + s;
        }
        return res;
    }

    /**
     * sunZeQun
     *
     * @param hanzi
     * @return
     */
    public String getPinyinWithFirstOneLower(String hanzi) {
        if (StringUtils.isNullOrEmpty(hanzi)) {
            return null;
        }
        char[] chars = hanzi.trim().toCharArray();

        if (chars.length == 1) {
            return getPinyin(chars[0]);
        } else {
            String res = "";
            res = res + getPinyin(chars[0]);
            for (int i = 1; i < chars.length; i++) {
                String s = getPinyin(chars[i]);
                s = StringUtils.toUpperCaseFirstOne(s);
                res = res + s;
            }
            return res;
        }

    }

    public String getPinyin(char singleWord) {
        //不是中文直接返回
        if (!MyStringUtils.isChinese(singleWord)) {
            return String.valueOf(singleWord);
        }

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        //大小写
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // WITHOUT_TONE：无音标  (zhong)
        // WITH_TONE_NUMBER：1-4数字表示英标  (zhong4)
        // WITH_TONE_MARK：直接用音标符（必须WITH_U_UNICODE否则异常）(zhòng)
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        // WITH_V：用v表示ü  (nv)
        // WITH_U_AND_COLON：用"u:"表示ü  (nu:)
        // WITH_U_UNICODE：直接用ü (nü)
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        try {
            return PinyinHelper.toHanyuPinyinStringArray(singleWord, format)[0];
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Pinyin pinyin = new Pinyin();
        System.out.println(pinyin.getPinyinWithFirstOneUpper("GDP"));
        System.out.println(pinyin.getPinyinWithFirstOneLower("合计GDP"));
        System.out.println(pinyin.getPinyinWithFirstOneUpper("孙泽群"));
        System.out.println(pinyin.getPinyinWithFirstOneLower("孙泽群"));
    }


}
