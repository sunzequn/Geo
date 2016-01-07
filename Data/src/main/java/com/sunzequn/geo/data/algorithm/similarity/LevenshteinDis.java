package com.sunzequn.geo.data.algorithm.similarity;

/**
 * Created by Sloriac on 16/1/6.
 * <p>
 * Levenshtein距离
 */
public class LevenshteinDis {

    /**
     * 计算编辑距离并进行归一化,使其范围为0-1.
     * 思路是用较大的字符串长度减去编辑距离,然后将差除以这个较大字符串长度.
     *
     * @param source
     * @param target
     * @return 归一化后的编辑距离
     */
    public static double compute(char[] source, char[] target) {

        int sourceLen = source.length;
        int targetLen = target.length;
        double maxLen = sourceLen > targetLen ? sourceLen : targetLen;
        double dis = distance(source, target);
        if (maxLen != 0) {
            return 1 - dis / maxLen;
        }
        return 0;
    }

    /**
     * 计算编辑距离
     *
     * @param source 源字符串
     * @param target 目标字符串
     * @return 将源字符串转化为目标字符串所需的步数
     */
    private static int distance(char[] source, char[] target) {

        int sourceLen = source.length;
        int targetLen = target.length;
        int dis[][] = new int[sourceLen + 1][targetLen + 1];

        /*
        初始化距离上界
         */
        for (int i = 0; i <= sourceLen; i++) {
            dis[i][0] = i;
        }
        for (int i = 0; i <= targetLen; i++) {
            dis[0][i] = i;
        }

        for (int i = 1; i <= targetLen; i++) {
            for (int j = 1; j <= sourceLen; j++) {
                if (target[i - 1] == source[j - 1]) {
                    //花费步数和上一个一样
                    dis[j][i] = dis[j - 1][i - 1];
                } else {
                    //插入删除修改的权值都为1
                    dis[j][i] = Math.min(Math.min(dis[j - 1][i], dis[j][i - 1]), dis[j - 1][i - 1]) + 1;
                }
            }
        }
        return dis[sourceLen][targetLen];

    }
}
