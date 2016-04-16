package com.sunzequn.geo.data.baike.clean;

import com.sunzequn.geo.data.baike.bdbk.*;
import com.sunzequn.geo.data.regex.RegexUtils;
import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.MyStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sunzequn on 2016/4/13.
 */
public class CleanHandler {

    private static RemoveRuleDao removeRuleDao = new RemoveRuleDao();
    //    private static UrlTypeDao urlTypeDao = new UrlTypeDao("url_type_broad");
    private static UrlTypeDao urlTypeDao = new UrlTypeDao();
    private static BasicInfoDao basicInfoDao = new BasicInfoDao();
    private static CatalogDao catalogDao = new CatalogDao();
    private static SummaryDao summaryDao = new SummaryDao();

    public static void main(String[] args) {
//        countNoSummary();
//        countNoBasicInfo();
//        cleanByInfoBox(1);
//        cleanByInfoBox(2);
//        cleanByInfoBox(4);
//        cleanByInfoBox(5);
//        cleanByInfoBox(6);
        cleanByInfoBox(7);
//        cleanByCatalog(3);
    }

    private static void countNoSummary() {
        List<UrlType> urlTypes = urlTypeDao.getAll();
        System.out.println(urlTypes.size());
        int num = 0;
        for (UrlType urlType : urlTypes) {
            Summary summary = summaryDao.getByUrl(urlType.getUrl());
            if (summary == null) {
                num++;
                System.out.println(num);
            } else {
                System.out.println(".");
            }
        }
        System.out.println(num);
    }

    private static void countNoBasicInfo() {
        List<UrlType> urlTypes = urlTypeDao.getAll();
        int num = 0;
        for (UrlType urlType : urlTypes) {
            List<BasicInfo> basicInfos = basicInfoDao.getByUrl(urlType.getUrl());
            if (ListUtils.isEmpty(basicInfos)) {
                num++;
            }
        }
        System.out.println(num);
    }

    private static void cleanByCatalog(int priority) {
        List<RemoveRule> removeRules = removeRuleDao.getByPriority(priority);
        System.out.println(removeRules.size());
        List<UrlType> urlTypes = urlTypeDao.getAll();
        System.out.println(urlTypes.size());
        for (UrlType urlType : urlTypes) {
            List<Catalog> catalogs = catalogDao.getByUrl(urlType.getUrl());
            if (!ListUtils.isEmpty(catalogs)) {
                List<String> keys = new ArrayList<>();
                for (Catalog catalog : catalogs) {
                    keys.add(CleanUtils.clean(catalog.getCatalog_item()));
                }
                for (RemoveRule removeRule : removeRules) {
                    //规则匹配，去除
                    if (isMatch(removeRule, keys, "{", "}")) {
                        System.out.println("cleanByCatalog");
                        System.out.println(removeRule);
                        System.out.println(urlType);
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
        int n = 0;
        for (UrlType urlType : urlTypes) {
            n++;
            System.out.println(n);
            List<BasicInfo> basicInfos = basicInfoDao.getByUrl(urlType.getUrl());
            if (!ListUtils.isEmpty(basicInfos)) {
                List<String> basicKeys = new ArrayList<>();
                for (BasicInfo basicInfo : basicInfos) {
                    basicKeys.add(CleanUtils.clean(basicInfo.getKey()));
                }
                for (RemoveRule removeRule : removeRules) {
                    //规则匹配，去除
                    if (isMatch(removeRule, basicKeys, "[", "]")) {
                        System.out.println("cleanByInfoBox");
                        System.out.println(removeRule);
                        System.out.println(urlType);
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
