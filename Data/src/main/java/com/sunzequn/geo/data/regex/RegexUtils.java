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

    /**
     * 解析连接中的文字
     *
     * @param content
     * @return
     */
    public static List<String> parseLink(String content, int index) {
        List<String> res = new ArrayList<>();
        String regex = "(\\{\\{([\\u0000-\\uffff]+?)::<(.+?)>\\}\\})";
        Matcher matcher = Pattern.compile(regex).matcher(content);
        while (matcher.find()) {
            res.add(matcher.group(index));
        }
        return res.size() > 0 ? res : null;
    }



    public static void main(String[] args) {
        String str = "受到收到{{南京大学::</view/3143.htm>}}、{{东南大学::</view/3154.htm>}}等";
        //"\\{\\{", "\\}\\}",
        System.out.println(RegexUtils.parseLink(str, 2));
    }
}
