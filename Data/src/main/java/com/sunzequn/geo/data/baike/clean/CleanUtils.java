package com.sunzequn.geo.data.baike.clean;

import com.sunzequn.geo.data.regex.RegexUtils;
import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.MyStringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sunzequn on 2016/4/16.
 */
public class CleanUtils {

    public static String clean(String s) {
        s = s.trim();
        s = MyStringUtils.ToDBC(s);
        s = find(s);
        s = removeCh(s);
        s = replace(s, "^(【)+");
        s = replace(s, "(】|[1-9]|[１-９]|[①-⑨])+$");
        return s;
    }

    private static String find(String s) {
        List<String> strings = RegexUtils.extract("\\{\\{", "::", s);
        if (!ListUtils.isEmpty(strings)) {
            return strings.get(0);
        }
        return s;
    }

    private static String replace(String s, String reg) {
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(s);
        return matcher.replaceAll("");
    }

    private static String removeCh(String string) {
        String[] ss = {":", " ", ",", "%", "-"};
        for (String s : ss) {
            if (string.contains(String.valueOf(s))) {
                string = string.replace(s, "");
            }
        }
        return string;
    }

    public static String getPropValue(String value) {
        if (value.contains("::") && value.contains("{{")) {
            List<String> strings = RegexUtils.extract("\\{\\{", "::", value);
            String res = "";
            for (String s : strings) {
                res += s + " ";
            }
            return res.trim();
        }
        return value;
    }
}
