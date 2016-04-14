package com.sunzequn.geo.data.baike.clean;

import com.sun.org.apache.xerces.internal.xs.LSInputList;
import com.sunzequn.geo.data.baike.bdbk.*;
import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.MyStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/13.
 */
public class CleanHandler {

    private static RemoveRuleDao removeRuleDao = new RemoveRuleDao();
    private static UrlTypeDao urlTypeDao = new UrlTypeDao();
    private static BasicInfoDao basicInfoDao = new BasicInfoDao();

    public static void main(String[] args) {
        clean(1);
    }

    private static void clean(int priority) {
        List<RemoveRule> removeRules = removeRuleDao.getByPriority(priority);
        System.out.println(removeRules.size());
        List<UrlType> urlTypes = urlTypeDao.getAll();
        System.out.println(urlTypes.size());
        for (UrlType urlType : urlTypes) {
            List<BasicInfo> basicInfos = basicInfoDao.getByUrl(urlType.getUrl());
            if (!ListUtils.isEmpty(basicInfos)) {
                for (RemoveRule removeRule : removeRules) {
                    //规则匹配，去除
                    if (isMatch(removeRule, basicInfos)) {
                        System.out.println("clean");
                        System.out.println(removeRule);
                        System.out.println(basicInfos);
                        System.out.println("===================================");
                        urlTypeDao.updateConfidence(urlType.getUrl(), 0);
                        break;
                    }
                }
            }

        }
    }

    private static boolean isMatch(RemoveRule removeRule, List<BasicInfo> basicInfos) {
        List<String> keys = getKeyOfRule(removeRule);
        List<String> basicKeys = new ArrayList<>();
        for (BasicInfo basicInfo : basicInfos) {
            basicKeys.add(basicInfo.getKey());
        }
        if (keys != null) {
            for (String key : keys) {
                if (!basicKeys.contains(key)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    private static List<String> getKeyOfRule(RemoveRule removeRule) {
        if (removeRule.getRule().startsWith("[") && removeRule.getRule().endsWith("]")) {
            String rule = MyStringUtils.remove(removeRule.getRule(), "[", "]");
            List<String> res = new ArrayList<>();
            if (rule.contains("/")) {
                String[] strings = StringUtils.split(rule, "/");
                Collections.addAll(res, strings);
            } else {
                res.add(rule);
            }
            return res;
        }
        return null;
    }


}
