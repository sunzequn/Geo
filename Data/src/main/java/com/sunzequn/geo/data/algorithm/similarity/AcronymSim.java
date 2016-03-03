package com.sunzequn.geo.data.algorithm.similarity;

/**
 * Created by Sloriac on 16/3/2.
 * <p>
 * 缩写词相似度的计算
 */
public class AcronymSim {

    /**
     * 判断一个词是不是另一个词的缩写
     *
     * @param fullString 单词或短语
     * @param acronym    缩写
     * @return
     */
    public static boolean isAcronym(String fullString, String acronym) {

        if (acronym == null || fullString == null || acronym.length() > fullString.length()) {
            return false;
        }
        //缩略词的指针
        int index = 0;
        //从带匹配的字符串进行扫描
        for (int i = 0; i < fullString.length(); i++) {
            char fullStringChar = fullString.charAt(i);
            char acronymChar = acronym.charAt(index);
            if (fullStringChar == acronymChar) {
                index++;
                if (index == acronym.length()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 在忽略大小写的情况下,判断一个词是否是另一个词的缩写
     *
     * @param fullString 单词或者短语
     * @param acronym    缩写词
     * @return
     */
    public static boolean isAcronymIngoreCase(String fullString, String acronym) {
        fullString = fullString.toLowerCase();
        acronym = acronym.toLowerCase();
        return isAcronym(fullString, acronym);
    }

    public static void main(String[] args) {
        System.out.println(AcronymSim.isAcronym("sunzequn", "szeq"));
    }
}
