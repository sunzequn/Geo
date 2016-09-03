package com.sunzequn.geo.data.baike.basicinfo;

import com.sunzequn.geo.data.regex.RegexUtils;
import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.StringUtils;

import java.util.List;

/**
 * Created by sunzequn on 2016/4/30.
 */
public class BasicInfoUtils {

    private static String chufaci1 = "{{";
    private static String chufaci2 = "::";
    private static String chufaci3 = "}}";

    private static boolean isContainLink(String content) {
        return content.contains(chufaci1) && content.contains(chufaci2) && content.contains(chufaci3);
    }

    public static List<String> parseValue(String rawValue, int index) {
        if (!StringUtils.isNullOrEmpty(rawValue) && isContainLink(rawValue)) {
            List<String> values = RegexUtils.parseLink(rawValue, index);
            if (!ListUtils.isEmpty(values)) {
                return values;
            } else {
                System.out.println("value 解析出错");
                System.out.println(rawValue);
                return null;
            }
        }
        return null;
    }

    public static String parseKey(String rawKey) {
        if (!StringUtils.isNullOrEmpty(rawKey) && isContainLink(rawKey)) {
            List<String> keys = RegexUtils.parseLink(rawKey, 2);
            List<String> raws = RegexUtils.parseLink(rawKey, 1);
            if (!ListUtils.isEmpty(keys)) {
                for (int i = 0; i < keys.size(); i++) {
                    rawKey = rawKey.replace(raws.get(i), keys.get(i));
                }
                return rawKey;
            } else {
                System.out.println("key 解析出错");
                System.out.println(rawKey);
                return null;
            }
        }
        return rawKey;
    }

    public static void main(String[] args) {
        String str = "受到收到{{南京大学::</view/3143.htm>}}、{{东南大学::</view/3154.htm>}}等";
        System.out.println(parseValue(str, 3));
    }
}
