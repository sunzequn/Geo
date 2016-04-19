package com.sunzequn.geo.data.baike.clean;

import com.sunzequn.geo.data.utils.ReadUtils;
import com.sunzequn.geo.data.utils.StringUtils;

import java.util.List;

/**
 * Created by sunzequn on 2016/4/14.
 * <p>
 * 将规则导入数据库
 */
public class RemoveRuleHandler {

    private static final String RULE_FILE = "D:/DevSpace/github/Geo/Data/src/main/resources/baike/rules.txt";
    private static RemoveRuleDao removeRuleDao = new RemoveRuleDao();

    public static void main(String[] args) {

        ReadUtils readUtils = new ReadUtils(RULE_FILE);
        List<String> lines = readUtils.readByLine();
        for (String line : lines) {
            if (StringUtils.isNullOrEmpty(line)) {
                continue;
            }
            line = "{" + line.trim() + "}";
            RemoveRule removeRule = new RemoveRule(line, "*", 4, "");
            removeRuleDao.save(removeRule);
        }

    }
}
