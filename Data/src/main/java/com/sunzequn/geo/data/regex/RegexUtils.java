package com.sunzequn.geo.data.regex;

import com.sunzequn.geo.data.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sloriac on 16/2/19.
 */
public class RegexUtils {

    /**
     * 利用正则表达式从文本中抽取两个标签内的字符串
     *
     * @param startTag 起始标签
     * @param endTag   终止标签
     * @param content  文本内容
     * @return 两个标签之间的字符串
     */
    public static List<String> extract(String startTag, String endTag, String content) {
        if (StringUtils.isNullOrEmpty(startTag) || StringUtils.isNullOrEmpty(endTag)) {
            return null;
        }
        List<String> res = new ArrayList<>();
        String regex = startTag + "([\\s\\S]*?)" + endTag;
        Matcher matcher = Pattern.compile(regex).matcher(content);
        while (matcher.find()) {
            res.add(matcher.group(1));
        }
        return res.size() > 0 ? res : null;
    }

    public static void main(String[] args) {
        String str = "<chapter_abstract>\n" +
                "地质公园是国家公园的一种类型，它是以地质遗产和地质景观为主要内容的自然公园，如建于1872年的美国黄石公园、久负盛名的科罗拉多大峡谷就属于地质景观类的自然公园。\n" +
                "1999年，中国开始推进地质遗产保护和地质公园建设。几年来，中国已经建立了85处国家地质公园，其中8处在2004年2月被联合国教科文组织列入首批世界地质公园名单。由于复杂的地质构造条件和地理背景，中国无疑是世界上地质景观最为丰富多样的国家，拥有令人炫目的遗产：嵩山，从最古老的太古代到最近的新生代的各代岩层都有发现，被称为“五世同堂”；石林，一个用中国的地名命名的喀斯特地貌类型；丹霞山，由红色陆相砂岩构成的赤壁丹霞地貌；庐山，中国第四纪冰川学说的诞生地……\n" +
                "</chapter_abstract>";
        System.out.println(RegexUtils.extract("<chapter_abstract>", "</chapter_abstract>", str));
    }
}
