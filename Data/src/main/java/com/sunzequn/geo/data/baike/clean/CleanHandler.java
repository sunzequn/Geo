package com.sunzequn.geo.data.baike.clean;

import com.sunzequn.geo.data.baike.bdbk.*;
import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.MyStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.riot.thrift.TRDF;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/13.
 */
public class CleanHandler {

    //    private static RemoveRuleDao removeRuleDao = new RemoveRuleDao("rules_remove");
    private static RemoveRuleDao removeRuleDao = new RemoveRuleDao("rules_remove_linshi");
    //    private static UrlTypeDao urlTypeDao = new UrlTypeDao("url_type_broad");
    private static UrlTypeDao urlTypeDao = new UrlTypeDao();
    private static BasicInfoDao basicInfoDao = new BasicInfoDao();
    private static CatalogDao catalogDao = new CatalogDao();
    private static SummaryDao summaryDao = new SummaryDao();
    private static TagDao tagDao = new TagDao();

    public static void main(String[] args) {
//        cleanTypeByTag(5, "岛" , 0, false);
//        cleanAllByTag(5, 0, false);
//        cleanAllByTag(5, 0, false);
//        cleanTypeByInfoBox(0, "学校", 0, true);
        cleanTypeByCatalog(0, "学校", 0, true);
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

    private static void cleanByCatalog(int priority, List<UrlType> urlTypes, int confidence, boolean ifUpdate) {
        List<RemoveRule> removeRules = removeRuleDao.getByPriority(priority);
        System.out.println(removeRules.size());
        int num = 0;
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
                        num++;
                        System.out.println("cleanAllByCatalog");
                        System.out.println(removeRule);
                        System.out.println(urlType);
                        System.out.println("===================================");
                        if (ifUpdate) {
                            urlTypeDao.updateConfidence(urlType.getUrl(), confidence);
//                            urlTypeDao.updateType(urlType.getUrl(), "楼盘");
                        }
                        break;
                    }
                }
            }

        }
        System.out.println(num);
    }

    private static void cleanAllByCatalog(int priority, int confidence, boolean ifUpdate) {
        List<UrlType> urlTypes = urlTypeDao.getAll();
        System.out.println(urlTypes.size());
        cleanByCatalog(priority, urlTypes, confidence, ifUpdate);
    }

    private static void cleanTypeByCatalog(int priority, String type, int confidence, boolean ifUpdate) {
        List<UrlType> urlTypes = urlTypeDao.getByTypeConfidence(type, 1);
        System.out.println(urlTypes.size());
        cleanByCatalog(priority, urlTypes, confidence, ifUpdate);
    }

    private static void cleanByInfoBox(int priority, List<UrlType> urlTypes, int confidence, boolean ifUpdate) {
        List<RemoveRule> removeRules = removeRuleDao.getByPriority(priority);
        System.out.println(removeRules.size());
        int num = 0;
        for (UrlType urlType : urlTypes) {
            List<BasicInfo> basicInfos = basicInfoDao.getByUrl(urlType.getUrl());
            if (!ListUtils.isEmpty(basicInfos)) {
                List<String> basicKeys = new ArrayList<>();
                for (BasicInfo basicInfo : basicInfos) {
                    basicKeys.add(CleanUtils.clean(basicInfo.getKey()));
                }
                for (RemoveRule removeRule : removeRules) {
                    //规则匹配，去除
                    if (isMatch(removeRule, basicKeys, "[", "]")) {
                        num++;
                        System.out.println("cleanAllByInfoBox");
                        System.out.println(removeRule);
                        System.out.println(urlType);
                        System.out.println("===================================");
                        if (ifUpdate) {
                            urlTypeDao.updateConfidence(urlType.getUrl(), confidence);
//                            urlTypeDao.updateType(urlType.getUrl(), "楼盘");
                        }

                        break;
                    }
                }
            }

        }
        System.out.println(num);
    }

    private static void cleanTypeByInfoBox(int priority, String type, int confidence, boolean ifUpdate) {
        List<UrlType> urlTypes = urlTypeDao.getByTypeConfidence(type, 1);
        System.out.println(urlTypes.size());
        cleanByInfoBox(priority, urlTypes, confidence, ifUpdate);
    }

    private static void cleanAllByInfoBox(int priority, int confidence, boolean ifUpdate) {
        List<UrlType> urlTypes = urlTypeDao.getAll();
        System.out.println(urlTypes.size());
        cleanByInfoBox(priority, urlTypes, confidence, ifUpdate);
    }

    private static void cleanTypeByTag(int priority, String type, int confidence, boolean ifUpdate) {
        List<UrlType> urlTypes = urlTypeDao.getByTypeConfidence(type, 1);
        System.out.println(urlTypes.size());
        cleanByTag(priority, urlTypes, confidence, ifUpdate);
    }

    private static void cleanAllByTag(int priority, int confidence, boolean ifUpdate) {
        List<UrlType> urlTypes = urlTypeDao.getAll();
        System.out.println(urlTypes.size());
        cleanByTag(priority, urlTypes, confidence, ifUpdate);
    }

    private static void cleanByTag(int priority, List<UrlType> urlTypes, int confidence, boolean ifUpdate) {
        List<RemoveRule> removeRules = removeRuleDao.getByPriority(priority);
        System.out.println(removeRules.size());
        int num = 0;
        for (UrlType urlType : urlTypes) {
            List<Tag> tags = tagDao.getByUrl(urlType.getUrl());
            if (!ListUtils.isEmpty(tags)) {
                List<String> basicKeys = new ArrayList<>();
                for (Tag tag : tags) {
                    basicKeys.add(CleanUtils.clean(tag.getOpen_tag()));
                }
                for (RemoveRule removeRule : removeRules) {
                    //规则匹配，去除
                    if (isMatch(removeRule, basicKeys, "(", ")")) {
                        num++;
                        System.out.println("cleanByTag");
                        System.out.println(removeRule);
                        System.out.println(urlType);
                        System.out.println("===================================");
                        if (ifUpdate) {
                            urlTypeDao.updateConfidence(urlType.getUrl(), confidence);
//                            urlTypeDao.updateType(urlType.getUrl(), "楼盘");
                        }
                        break;
                    }
                }
            }
        }
        System.out.println(num);
    }

    private static boolean isMatch(RemoveRule removeRule, List<String> basicKeys, String prefix, String suffix) {
        String rule = getKeyOfRule(removeRule, prefix, suffix);
        String temp = "";
        for (String key : basicKeys) {
            temp = temp + "/" + key;
        }
        return temp.contains(rule);
    }


    private static String getKeyOfRule(RemoveRule removeRule, String prefix, String suffix) {
        if (removeRule.getRule().startsWith(prefix) && removeRule.getRule().endsWith(suffix)) {
            return MyStringUtils.remove(removeRule.getRule(), prefix, suffix);
        }
        return removeRule.getRule();
    }

}
