package com.sunzequn.geo.data.baike.chinaquhua;

import com.sunzequn.geo.data.baike.bdbk.BasicInfoDao;
import com.sunzequn.geo.data.baike.bdbk.TitleDao;
import com.sunzequn.geo.data.baike.bdbk.UrlType;
import com.sunzequn.geo.data.baike.bdbk.UrlTypeDao;
import com.sunzequn.geo.data.china.geo.ChinaCityDao;
import com.sunzequn.geo.data.utils.ReadUtils;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.cypher.internal.compiler.v2_2.functions.Str;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sunzequn on 2016/4/17.
 */
public class QuhuaUtils {

    private static String FILE = "D:/DevSpace/github/Geo/Data/src/main/resources/quhua/";
    private static ChinaCityDao cityDao = new ChinaCityDao();
    private static UrlTypeDao urlTypeDao = new UrlTypeDao("url_type_quhua");
    private static TitleDao titleDao = new TitleDao();
    private static BasicInfoDao basicInfoDao = new BasicInfoDao();
    private static String KEY = "行政区类别";
    private static int sub = 50;


    public static Set<String> getGuo() {
        String quFile = FILE + "guo";
        ReadUtils readUtils = new ReadUtils(quFile);
        List<String> lines = readUtils.readByLine();
        Set<String> guos = new HashSet<>();
        for (String line : lines) {
            if (line.contains("★")) {
                String[] strings = StringUtils.split(line, "★");
                guos.add(strings[1].trim());
            }
        }
        return guos;
    }

    public static Set<String> getFromType(String type) {
        List<UrlType> urlTypes = urlTypeDao.getByType(type);
        Set<String> res = new HashSet<>();
        for (UrlType urlType : urlTypes) {
            res.add(urlType.getTitle());
        }
        return res;
    }

    public static Set<String> getShengShi() {
        Set<String> res = new HashSet<>();
        res.addAll(getFromType("省"));
        res.addAll(getFromType("自治区"));
        res.addAll(getFromType("直辖市"));
        res.addAll(getFromType("特别行政区"));
        res.addAll(getFromType("地级市"));
        res.addAll(getFromType("自治州"));
        res.addAll(getFromType("自治县"));
        res.addAll(getFromType("地区"));
        res.addAll(getFromType("盟"));
        res.addAll(getFromType("县级市"));
        res.addAll(getFromType("自治旗"));
        res.addAll(getFromType("旗"));
        res.addAll(getFromType("市辖区"));
        res.addAll(getFromType("县"));
        return res;
    }

    public static boolean ifChina(UrlType urlType) {
        Set<String> shengshixian = getShengShi();
//        shengshixian.add("中国");
        return ifContain(shengshixian, urlType);
    }

    public static boolean ifGuoWai(UrlType urlType) {
        if (ifChina(urlType)) {
            return false;
        }
        Set<String> guos = getGuo();
        guos.remove("中国");
        return ifContain(guos, urlType);
    }

    private static boolean ifContain(Set<String> strings, UrlType urlType) {
        for (String string : strings) {
            if (urlType.getTitle().contains(string)) {
                return true;
            } else if (urlType.getSubtitle().contains(string)) {
                return true;
            } else {
                String summary = urlType.getSummary();
                if (summary.length() > sub) {
                    summary = summary.substring(0, sub);
                    if (summary.contains(string)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
