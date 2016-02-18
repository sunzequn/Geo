package com.sunzequn.geo.data.link;

import com.sunzequn.geo.data.algorithm.similarity.LevenshteinDis;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by sloriac on 16-2-17.
 */
public class LinkUtils {

    private static final double THRESHOLD = 0.9;

    /**
     * 计算名称之间的匹配程度
     *
     * @param geoName
     * @param climateName
     * @return
     */
    public static double isNameEqual(String geoName, String climateName) {
        geoName = geoName.trim();
        climateName = climateName.trim();
        //完全匹配
        if (geoName.equals(climateName)) {
            return 3.0;
        }
        //忽略大小写下的匹配
        else if (geoName.equalsIgnoreCase(climateName)) {
            return 2.0;
        }
        //去除一些地点区划名称之后完全匹配
        else if (geoName.equals(climateNameClear(climateName))) {
            return 1.8;
        }
        //去除一些地点区划名称并且忽略大小写完全匹配
        else if (geoName.equalsIgnoreCase(climateNameClear(climateName))) {
            return 1.5;
        }
        //模糊相似度(忽略大小写)大于阈值
        else {
            double similarity = nameSimilarity(geoName, climateName);
            if (similarity >= THRESHOLD) {
                return similarity;
            } else {
                return 0;
            }
        }
    }

    /**
     * 判断名字和别名的最大相似度
     *
     * @param climateName
     * @param alterName   未处理的别名，以，分开
     * @return
     */
    public static double isAlternameEqual(String climateName, String alterName) {
        String[] alterNames = StringUtils.split(alterName, ",");
        double res = 0.0;
        for (String geoName : alterNames) {
            double similarity = isNameEqual(geoName, climateName);
            if (similarity > res) {
                res = similarity;
            }
        }
        //不允许模糊匹配
        if (res > 1.0) {
            res = res / 10.0 + 1;
            return res;
        }
        return 0.0;
    }

    public static String climateNameClear(String name) {
        String[] removeEnds = {"Province", "Governorate", "Oblast", "County", "City", "Region", "District", "Department", "İnzibati Ərazisi"};
        for (String remove : removeEnds) {
            if (name.endsWith(remove)) {
                name = StringUtils.removeEnd(name, remove).trim();
                return name;
            }
        }
        String[] removeStarts = {"Region of"};
        for (String remove : removeStarts) {
            if (name.startsWith(remove)) {
                name = StringUtils.removeStart(name, remove).trim();
                return name;
            }
        }
        return name;
    }

    public static String geonameClear(String name) {
        String[] removeEnds = {"District"};
        for (String remove : removeEnds) {
            if (name.endsWith(remove)) {
                name = StringUtils.removeEnd(name, remove).trim();
                return name;
            }
        }
        return name;
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

    public static void main(String[] args) {
        System.out.println(1.2 / 10.0);
    }
}
