package com.sunzequn.geo.data.baike.handler;

import com.sunzequn.geo.data.baike.bean.BaikePage;
import com.sunzequn.geo.data.baike.bean.Rule;
import com.sunzequn.geo.data.baike.dao.RuleDao;

import java.util.List;

/**
 * Created by sunzequn on 2016/4/9.
 */
public class RuleHandler {

    private static RuleDao ruleDao = new RuleDao();

    public static void main(String[] args) {
        List<Rule> rules = ruleDao.getAll();
        System.out.println(rules.size());
        for (Rule rule : rules) {
            rule.initRules();
            System.out.println(rule);
            BaikePage page = new BaikePage();
            page.setTitle("南京");
            page.setSubTitle("江苏省省会城市");
            System.out.println(rule.isMatched(page));
        }
    }
}
