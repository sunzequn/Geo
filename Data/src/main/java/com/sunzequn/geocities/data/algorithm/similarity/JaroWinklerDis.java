package com.sunzequn.geocities.data.algorithm.similarity;

import java.util.Arrays;

/**
 * Created by Sloriac on 16/1/6.
 * <p>
 * Jaro–Winkler distance
 */
public class JaroWinklerDis {

    //用于添加额外奖励的阈值,默认=取0.7
    private static double threshold = 0.7;

    /**
     * 计算 Jaro-Winkler 距离.
     *
     * @param source 源字符串
     * @param target 目标字符串
     * @return 两个字符串的 Jaro-Winkler 距离
     */
    public static double compute(char[] source, char[] target) {
        int[] results = distance(source, target);
        //匹配的字符串的数量
        double matches = (double) results[0];
        if (matches == 0) {
            return 0;
        }
        // Jaro 距离
        double dj = ((matches / source.length + matches / target.length + (matches - results[1]) / matches)) / 3;
        // Jaro-Winkler 距离
        double djw = dj < threshold ? dj : dj + Math.min(0.1f, 1f / results[3]) * results[2] * (1 - dj);
        return djw;
    }

    private static int[] distance(char[] source, char[] target) {
        char[] max, min;
        if (source.length > target.length) {
            max = source;
            min = target;
        } else {
            max = target;
            min = source;
        }
        //计算匹配窗口
        int range = Math.max(max.length / 2 - 1, 0);
        //当用较短字符串和长字符串比较的时候,记录匹配字符串在长字符串的下标位置
        int[] matchIndexes = new int[min.length];
        Arrays.fill(matchIndexes, -1);
        //当用较短字符串和长字符串比较的时候,记录匹配的字符串
        boolean[] matchFlags = new boolean[max.length];
        //匹配的字符串的数量
        int matches = 0;
        //外循环从短字符串开始
        for (int i = 0; i < min.length; i++) {
            char ch = min[i];
            //匹配范围,从(index-range) 到 (index+range)
            for (int j = Math.max(i - range, 0), end = Math.min(i + range + 1, max.length); j < end; j++) {
                if (!matchFlags[j] && ch == max[j]) {
                    matchIndexes[i] = j;
                    matchFlags[j] = true;
                    matches++;
                    break;
                }
            }
        }
        //复制短字符串内的匹配字符,保持原有顺序
        char[] ch1 = new char[matches];
        //复制长字符串内的匹配字符,保持原有顺序
        char[] ch2 = new char[matches];
        for (int i = 0, si = 0; i < min.length; i++) {
            if (matchIndexes[i] != -1) {
                ch1[si] = min[i];
                si++;
            }
        }
        for (int i = 0, si = 0; i < max.length; i++) {
            if (matchFlags[i]) {
                ch2[si] = max[i];
                si++;
            }
        }
        //计算置换次数
        int transpositions = 0;
        for (int i = 0; i < ch1.length; i++) {
            if (ch1[i] != ch2[i]) {
                transpositions++;
            }
        }
        //计算前缀字符的长度
        int prefix = 0;
        for (int i = 0; i < min.length; i++) {
            if (source[i] == target[i]) {
                prefix++;
            } else {
                break;
            }
        }
        //返回匹配字符串的长度,置换次数,前缀和较大字符串长度
        return new int[]{matches, transpositions / 2, prefix, max.length};
    }
}
