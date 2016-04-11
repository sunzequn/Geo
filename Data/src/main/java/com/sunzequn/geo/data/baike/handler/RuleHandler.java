package com.sunzequn.geo.data.baike.handler;

import com.sunzequn.geo.data.baike.bdbk.*;
import com.sunzequn.geo.data.baike.bean.BaikePage;
import com.sunzequn.geo.data.baike.bean.Rule;
import com.sunzequn.geo.data.baike.dao.RuleDao;
import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.MyStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sunzequn on 2016/4/9.
 */
public class RuleHandler {

    private static RuleDao ruleDao = new RuleDao();
    private static BasicInfoDao basicInfoDao = new BasicInfoDao();
    private static SubTitleDao subTitleDao = new SubTitleDao();
    private static TitleDao titleDao = new TitleDao();
    private static UrlTypeDao urlTypeDao = new UrlTypeDao();

    public static void main(String[] args) {
        List<Rule> rules = ruleDao.getAll();
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
            }
            for (String s : res) {
                urlTypeDao.addType(s, rule.getType(), 1);
            }
        }
    }

    private static Set<String> getMatched(String rule) {
        rule = rule.trim();
        Set<String> res = new HashSet<>();
        //*xx
        if (rule.startsWith("*") && !rule.endsWith("*")) {
            String match = StringUtils.removeStart(rule, "*");
            List<Title> titles = titleDao.getTitleEnds(match);
            if (!ListUtils.isEmpty(titles)) {
                for (Title title : titles) {
                    res.add(title.getUrl());
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
            String[] strings = StringUtils.split(match, ",");
            String key = strings[0].trim();
            String value = strings[1].trim();
            List<BasicInfo> basicInfos = basicInfoDao.getKeyValue(key, value);
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
