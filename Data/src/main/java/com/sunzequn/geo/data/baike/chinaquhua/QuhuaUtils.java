package com.sunzequn.geo.data.baike.chinaquhua;

import com.sunzequn.geo.data.baike.bdbk.BasicInfoDao;
import com.sunzequn.geo.data.baike.bdbk.TitleDao;
import com.sunzequn.geo.data.baike.bdbk.UrlType;
import com.sunzequn.geo.data.baike.bdbk.UrlTypeDao;
import com.sunzequn.geo.data.china.geo.ChinaCity;
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
    //    private static UrlTypeDao urlTypeDao = new UrlTypeDao("url_type_quhua");
    private static TitleDao titleDao = new TitleDao();
    private static BasicInfoDao basicInfoDao = new BasicInfoDao();
    private static String KEY = "行政区类别";
    private static int sub = 60;
    private static List<ChinaCity> chinaCities = cityDao.getAll();
    private static Set<String> chinaCitySets = new HashSet<>();
    private static Set<String> waiguos = new HashSet<>();

    public QuhuaUtils() {
        for (ChinaCity chinaCity : chinaCities) {
            chinaCitySets.add(chinaCity.getShortname());
        }
        System.out.println(chinaCities.size());
        waiguos = getGuo();
        waiguos.remove("中国");
    }

    public Set<String> getGuo() {
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

//    public static Set<String> getFromType(String type) {
//        List<UrlType> urlTypes = urlTypeDao.getByType(type);
//        Set<String> res = new HashSet<>();
//        for (UrlType urlType : urlTypes) {
//            res.add(urlType.getTitle());
//        }
//        return res;
//    }

//    public static Set<String> getShengShi() {
//        Set<String> res = new HashSet<>();
//        res.addAll(getFromType("省"));
//        res.addAll(getFromType("自治区"));
//        res.addAll(getFromType("直辖市"));
//        res.addAll(getFromType("特别行政区"));
//        res.addAll(getFromType("地级市"));
//        res.addAll(getFromType("自治州"));
//        res.addAll(getFromType("自治县"));
//        res.addAll(getFromType("地区"));
//        res.addAll(getFromType("盟"));
//        res.addAll(getFromType("县级市"));
//        res.addAll(getFromType("自治旗"));
//        res.addAll(getFromType("旗"));
//        res.addAll(getFromType("市辖区"));
//        res.addAll(getFromType("县"));
//        return res;
//    }

    /**
     * @param urlType
     * @return 2, 中国； 1 不确定； 0 外国
     */
    public int ifChina(UrlType urlType) {
        if (ifContain(chinaCitySets, urlType)) {
            return 2;
        } else if (ifGuoWai(urlType)) {
            return 0;
        } else {
            return 1;
        }
    }

    public boolean ifGuoWai(UrlType urlType) {
        return ifContain(waiguos, urlType);
    }

    private boolean ifContain(Set<String> strings, UrlType urlType) {
        for (String string : strings) {
            string = string.trim();
            if (urlType.getTitle().contains(string)) {
                return true;
            } else if (!com.sunzequn.geo.data.utils.StringUtils.isNullOrEmpty(urlType.getSubtitle()) && urlType.getSubtitle().contains(string)) {
                return true;
            } else {
                String summary = urlType.getSummary();

                if (!com.sunzequn.geo.data.utils.StringUtils.isNullOrEmpty(summary)) {
                    if (summary.length() > sub) {
                        summary = summary.substring(0, sub);
                    }
                    if (summary.contains(string)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        QuhuaUtils quhuaUtils = new QuhuaUtils();
        UrlType urlType = new UrlType();
        urlType.setTitle("ss");
//        urlType.setSummary("Ss");
        urlType.setSummary("织金县化处小学位于八步镇东北面的新化村新化组,距八步镇政府所在地6公里左右。学校从解放前的1949年6月创建以来经历了几次的变迁和修缮,直到解放后的1963年才搬迁于现在的学校所在地。");
        System.out.println(quhuaUtils.ifChina(urlType));
    }
}
