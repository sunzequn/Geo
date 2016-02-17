package com.sunzequn.geo.data.link;

import com.sunzequn.geo.data.algorithm.similarity.LevenshteinDis;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by sloriac on 16-2-17.
 */
public class LinkUtils {

    /**
     * 判断名字是否相等（忽略英文大小写）
     *
     * @param name1 第一个名字
     * @param name2 第一个名字
     * @return
     */
    public static boolean isNameEqual(String name1, String name2) {
        return name1.trim().equalsIgnoreCase(name2.trim());
    }

    /**
     * 判断名字和别名是否相等（忽略英文大小写）
     *
     * @param name
     * @param alterName 未处理的别名，以，分开
     * @return
     */
    public static boolean isAlternameEqual(String name, String alterName) {
        String[] alterNames = StringUtils.split(alterName, ",");
        name = name.trim();
        for (String str : alterNames) {
            if (name.equalsIgnoreCase(str.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 计算名称的相似度，使用编辑距离
     *
     * @param name1
     * @param name2
     * @return
     */
    public static double nameSimilarity(String name1, String name2) {
        return LevenshteinDis.compute(name1.trim().toLowerCase().toCharArray(), name2.trim().toLowerCase().toCharArray());
    }

    /**
     * 计算别名和名字之间的最大相似度
     *
     * @param name
     * @param alterName
     * @return
     */
    public static double alterNameSimilarity(String name, String alterName) {
        String[] alterNames = StringUtils.split(alterName.trim().toLowerCase(), ",");
        name = name.trim().toLowerCase();
        double res = -1.0;
        for (String str : alterNames) {
            double similarity = LevenshteinDis.compute(name.toCharArray(), str.toCharArray());
            if (similarity > res) {
                res = similarity;
            }
        }
        return res;
    }
}
