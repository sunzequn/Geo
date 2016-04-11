package com.sunzequn.geo.data.baike.bean;

import com.sunzequn.geo.data.utils.MyStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

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
