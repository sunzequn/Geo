package com.sunzequn.geo.data.baike.bdbk;

import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.MyStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sunzequn on 2016/4/9.
 */
public class RuleHandler {

    private static RuleDao ruleDao = new RuleDao("rules_all");
    //    private static RuleDao ruleDao = new RuleDao("rules_quhua");
    private static BasicInfoDao basicInfoDao = new BasicInfoDao();
    private static SubTitleDao subTitleDao = new SubTitleDao();
    private static TitleDao titleDao = new TitleDao();
    private static UrlTypeDao urlTypeDao = new UrlTypeDao("url_type_toli");
    //    private static UrlTypeDao urlTypeDao = new UrlTypeDao("url_type_quhua");
    private static SummaryDao summaryDao = new SummaryDao();

    public static void main(String[] args) {
//        extract(0, 1);
        completion();
    }

    private static void completion() {
        while (true) {
            List<UrlType> urlTypes = urlTypeDao.getAllUrlWithNull(5000);
            if (ListUtils.isEmpty(urlTypes)) {
                return;
            }
            System.out.println(urlTypes.size());
            for (UrlType urlType : urlTypes) {
                Title title_bean = titleDao.getByUrl(urlType.getUrl());
//                SubTitle subTitle_bean = subTitleDao.getByUrl(urlType.getUrl());
//                Summary summary_bean = summaryDao.getByUrl(urlType.getUrl());
                String title, subtitle, summary;
                if (title_bean == null) {
                    title = "";
                } else {
                    title = title_bean.getTitle();
                }
                urlType.setTitle(title);
//                if (subTitle_bean == null) {
//                    subtitle = "";
//                } else {
//                    subtitle = subTitle_bean.getSubtitle();
//                }
                urlType.setSubtitle("");
//                if (summary_bean == null) {
//                    summary = "";
//                } else {
//                    summary = summary_bean.getSummary();
//                }
                urlType.setSummary("");
            }
            urlTypeDao.updateBatch(urlTypes);
        }
    }

    private static void extract(int visit, int confidence) {
        List<Rule> rules = ruleDao.getAll(visit);
        for (Rule rule : rules) {
            rule.initRules();
            System.out.println("----------------------------- " + rule + "--------------------------------");
            Set<String> res = new HashSet<>();
            for (String srule : rule.getRuless()) {
                Set<String> matcheds = getMatched(srule);
                if (matcheds != null) {
                    if (res.size() == 0) {
                        res.addAll(matcheds);
                    } else {
                        res.retainAll(matcheds);
                    }
                }
                System.out.println("===   " + res.size());
            }
            List<UrlType> urlTypes = new ArrayList<>();
            for (String s : res) {
//                urlTypeDao.addType(s, rule.getType(), confidence);
                urlTypes.add(new UrlType(s, rule.getType(), confidence));
            }
            urlTypeDao.addTypeBatch(urlTypes);
        }
    }

    private static Set<String> getMatched(String rule) {
        System.out.println(rule);
        rule = rule.trim();
        Set<String> res = new HashSet<>();
        //*xx
        if (rule.startsWith("*") && !rule.endsWith("*")) {
            String match = StringUtils.removeStart(rule, "*");
            //不包含的处理
            List<String> notContains = new ArrayList<>();
            if (match.contains("!")) {
                String[] matchArr = StringUtils.split(match, "!");
                match = matchArr[0];
                for (int i = 1; i < matchArr.length; i++) {
                    notContains.add(matchArr[i]);
                }
            }
            List<Title> titles = titleDao.getTitleEnds(match);
            if (!ListUtils.isEmpty(titles)) {
                for (Title title : titles) {
                    if (notContains.size() > 0) {
                        boolean ifMatched = true;
                        for (String not : notContains) {
                            if (title.getTitle().endsWith(not)) {
                                ifMatched = false;
                            }
                        }
                        if (ifMatched) {
                            res.add(title.getUrl());
                        }
                    } else {
                        res.add(title.getUrl());
                    }

                }
            } else {
                return null;
            }
        }
        //*xx*
        else if (rule.startsWith("*") && rule.endsWith("*")) {
            String match = MyStringUtils.remove(rule, "*", "*");
            List<Title> titles = titleDao.getTitleContains(match);
            if (!ListUtils.isEmpty(titles)) {
                for (Title title : titles) {
                    res.add(title.getUrl());
                }
            } else {
                return null;
            }
        }
        //(*xx*)
        else if (rule.startsWith("(*") && rule.endsWith("*)")) {
            String match = MyStringUtils.remove(rule, "(*", "*)");
            List<SubTitle> subtitles = subTitleDao.getSubtitleContains(match);
            if (!ListUtils.isEmpty(subtitles)) {
                for (SubTitle subtitle : subtitles) {
                    res.add(subtitle.getUrl());
                }
            } else {
                return null;
            }
        }
        //(*xx)，和上面的顺序不能换
        else if (rule.startsWith("(*") && rule.endsWith(")")) {
            String match = MyStringUtils.remove(rule, "(*", ")");
            List<SubTitle> subtitles = subTitleDao.getSubtitleEnds(match);
            if (!ListUtils.isEmpty(subtitles)) {
                for (SubTitle subtitle : subtitles) {
                    res.add(subtitle.getUrl());
                }
            } else {
                return null;
            }
        }
        //[key,value]
        else if (rule.startsWith("[") && rule.endsWith("]")) {
            String match = MyStringUtils.remove(rule, "[", "]");
            List<BasicInfo> basicInfos;
            if (rule.contains(",")) {
                String[] strings = StringUtils.split(match, ",");
                String key = strings[0].trim();
                String value = strings[1].trim();
                basicInfos = basicInfoDao.getKeyValue(key, value);
            } else {
                basicInfos = basicInfoDao.getByPropKey(match);
            }
            if (!ListUtils.isEmpty(basicInfos)) {
                for (BasicInfo basicInfo : basicInfos) {
                    res.add(basicInfo.getUrl());
                }
            } else {
                return null;
            }

        } else {
            System.out.println("出错");
            return null;
        }
        return res;

    }

}
