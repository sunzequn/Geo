package com.sunzequn.geo.data.baike.clean;

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
    private static CatalogDao catalogDao = new CatalogDao();

    public static void main(String[] args) {
//        cleanByInfoBox(1);
        cleanByCatalog(3);
    }

    private static void cleanByCatalog(int priority) {
        List<RemoveRule> removeRules = removeRuleDao.getByPriority(priority);
        System.out.println(removeRules.size());
        List<UrlType> urlTypes = urlTypeDao.getAll();
        System.out.println(urlTypes.size());
        for (UrlType urlType : urlTypes) {
            List<Catalog> catalogs = catalogDao.getByUrl(urlType.getUrl());
            List<String> keys = new ArrayList<>();
            for (Catalog catalog : catalogs) {
                keys.add(catalog.getCatalog_item());
            }
            if (!ListUtils.isEmpty(catalogs)) {
                for (RemoveRule removeRule : removeRules) {
                    //规则匹配，去除
                    if (isMatch(removeRule, keys, "{", "}")) {
                        System.out.println("cleanByCatalog");
                        System.out.println(removeRule);
                        System.out.println(catalogs);
                        System.out.println("===================================");
                        urlTypeDao.updateConfidence(urlType.getUrl(), 0);
                        break;
                    }
                }
            }

        }
    }

    private static void cleanByInfoBox(int priority) {
        List<RemoveRule> removeRules = removeRuleDao.getByPriority(priority);
        System.out.println(removeRules.size());
        List<UrlType> urlTypes = urlTypeDao.getAll();
        System.out.println(urlTypes.size());
        for (UrlType urlType : urlTypes) {
            List<BasicInfo> basicInfos = basicInfoDao.getByUrl(urlType.getUrl());
            List<String> basicKeys = new ArrayList<>();
            for (BasicInfo basicInfo : basicInfos) {
                basicKeys.add(basicInfo.getKey());
            }
            if (!ListUtils.isEmpty(basicInfos)) {
                for (RemoveRule removeRule : removeRules) {
                    //规则匹配，去除
                    if (isMatch(removeRule, basicKeys, "[", "]")) {
                        System.out.println("cleanByInfoBox");
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

    private static boolean isMatch(RemoveRule removeRule, List<String> basicKeys, String prefix, String suffix) {
        List<String> keys = getKeyOfRule(removeRule, prefix, suffix);
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


    private static List<String> getKeyOfRule(RemoveRule removeRule, String prefix, String suffix) {
        if (removeRule.getRule().startsWith(prefix) && removeRule.getRule().endsWith(suffix)) {
            String rule = MyStringUtils.remove(removeRule.getRule(), prefix, suffix);
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
