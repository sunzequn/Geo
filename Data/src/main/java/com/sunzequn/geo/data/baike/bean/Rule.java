package com.sunzequn.geo.data.baike.bean;

import com.sunzequn.geo.data.utils.MyStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/9.
 */
public class Rule {

    private String type;
    private String rules;
    private List<String> ruless = new ArrayList<>();

    public void initRules() {
        if (rules.contains("/")) {
            String[] strings = StringUtils.split(rules, "/");
            Collections.addAll(ruless, strings);
        } else {
            ruless.add(rules);
        }
    }

    //判断百科页面是否和当前规则匹配
    public boolean isMatched(BaikePage page) {
        for (String rule : ruless) {
            if (!isMatched(rule, page)) {
                return false;
            }
        }
        return true;
    }

    private boolean isMatched(String rule, BaikePage page) {
        rule = rule.trim();
        //*xx
        if (rule.startsWith("*") && !rule.endsWith("*")) {
            String match = StringUtils.removeStart(rule, "*");
            return page.getTitle().endsWith(match);
        }
        //*xx*
        else if (rule.startsWith("*") && rule.endsWith("*")) {
            String match = MyStringUtils.remove(rule, "*", "*");
            return page.getTitle().contains(match);
        }
        //(*xx*)
        else if (rule.startsWith("(*") && rule.endsWith("*)")) {
            String match = MyStringUtils.remove(rule, "(*", "*)");
            return page.getSubTitle().contains(match);
        }
        //(*xx)，和上面的顺序不能换
        else if (rule.startsWith("(*") && rule.endsWith(")")) {
            String match = MyStringUtils.remove(rule, "(*", ")");
            System.out.println(page);
            return page.getSubTitle().endsWith(match);
        } else {
            System.out.println("出错");
        }
        return false;
    }

    public Rule() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public List<String> getRuless() {
        return ruless;
    }

    public void setRuless(List<String> ruless) {
        this.ruless = ruless;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "type='" + type + '\'' +
                ", rules='" + rules + '\'' +
                ", ruless=" + ruless +
                '}';
    }
}
