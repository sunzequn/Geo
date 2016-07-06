package com.sunzequn.geo.data.baike.handler;

import com.sunzequn.geo.data.baike.bdbk.Rule;
import com.sunzequn.geo.data.baike.bdbk.RuleDao;
import com.sunzequn.geo.data.utils.StringUtils;
import com.sunzequn.geo.data.utils.WriteUtils;

import java.util.List;

/**
 * Created by sunzequn on 2016/7/5.
 */
public class Rule2Code {

    private static RuleDao ruleDao = new RuleDao("rules_all_extract");
    private static final String FILE = "Data/src/main/resources/clinga/rules";

    public static void main(String[] args) {
        handle();
//        System.out.println(jiexi("*岛!群岛!半岛"));
//        System.out.println(jiexi("[属性, 取值]"));
    }

    public static void handle() {
        WriteUtils writeUtils = new WriteUtils(FILE, false);
        List<Rule> rules = ruleDao.getAll();
        System.out.println(rules.size());
        for (Rule rule : rules) {
            rule.initRules();
        }
        for (Rule rule : rules) {
            String r = "{type=\"" + rule.getType() + "\"} :- {";
            List<String> ruleList = rule.getRuless();
            if (ruleList.size() == 1) {
                r += jiexi(ruleList.get(0)) + "}";
            } else {
                r += jiexi(ruleList.get(0));
                for (int i = 1; i < ruleList.size(); i++) {
                    r += ", " + jiexi(ruleList.get(i));
                }
                r += "}";
            }
            System.out.println(r);
            writeUtils.write(r);
        }
        writeUtils.close();
    }

    private static String jiexi(String rule) {
        rule = rule.trim();
        // *xx
        if (rule.startsWith("*") && !rule.endsWith("*") && !rule.contains("!")) {
            String v = StringUtils.removeStart(rule, "*");
            return "title.endsWith(\"" + v + "\")";
        }
        //*xx*
        else if (rule.startsWith("*") && rule.endsWith("*")) {
            String v = StringUtils.removeStart(rule, "*");
            v = StringUtils.removeEnd(v, "*");
            return "title.contains(\"" + v + "\")";
        }
        //(*xx*)
        else if (rule.startsWith("(*") && rule.endsWith("*)")) {
            String v = StringUtils.removeStart(rule, "(*");
            v = StringUtils.removeEnd(v, "*)");
            return "alternative.contains(\"" + v + "\")";
        }
        //(*xx)，和上面的顺序不能换
        else if (rule.startsWith("(*") && rule.endsWith(")")) {
            String v = StringUtils.removeStart(rule, "(*");
            v = StringUtils.removeEnd(v, ")");
            return "alternative.endsWith(\"" + v + "\")";
        }
        // *xx!xx
        else if (rule.startsWith("*") && rule.contains("!")) {
            String v = StringUtils.removeStart(rule, "*");
            String[] vs = v.split("!");
            String res = "title.endsWith(\"" + vs[0] + "\")";
            for (int i = 1; i < vs.length; i++) {
                res += ", !title.endsWith(\"" + vs[i] + "\")";
            }
            return res;
        }
        //[key]
        else if (rule.startsWith("[") && rule.endsWith("]") && !rule.contains(",")) {
            String v = StringUtils.removeStart(rule, "[");
            v = StringUtils.removeEnd(v, "]");
            return "hasProperty(\"" + v + "\")";
        }
        //[key， value]
        else if (rule.startsWith("[") && rule.endsWith("]") && rule.contains(",")) {
            String v = StringUtils.removeStart(rule, "[");
            v = StringUtils.removeEnd(v, "]");
            String[] vs = v.split(",");
            String key = vs[0].trim();
            String value = vs[1].trim();
            return "hasProperty(\"" + key + "\" value(\"" + value + "\"))";
        }

        return null;
    }


}
