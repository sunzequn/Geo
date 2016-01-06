package com.sunzequn.geocities.data.algorithm.similarity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sloriac on 16/1/6.
 */
public class JaccardDis {

    /**
     * 计算两个字符串的Jaccard距离
     *
     * @param source 源字符串
     * @param target 目标字符串
     * @return Jaccard距离, 范围是0-1
     */
    public static double compute(char[] source, char[] target) {
        int sourceLen = source.length;
        int targetLen = target.length;
        Set<Character> sourceSet = new HashSet<>();
        Set<Character> targetSet = new HashSet<>();
        for (int i = 0; i < sourceLen; i++) {
            sourceSet.add(source[i]);
        }
        for (int i = 0; i < targetLen; i++) {
            targetSet.add(target[i]);
        }
        Set<Character> intersection = new HashSet<>();
        Set<Character> union = new HashSet<>();
        intersection.addAll(sourceSet);
        intersection.retainAll(targetSet);
        union.addAll(sourceSet);
        union.addAll(targetSet);
        if (union.size() != 0) {
            return (double) intersection.size() / union.size();
        }
        return 0;
    }
}
