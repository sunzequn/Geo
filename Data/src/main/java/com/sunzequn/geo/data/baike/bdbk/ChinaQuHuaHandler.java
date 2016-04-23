package com.sunzequn.geo.data.baike.bdbk;

import com.sunzequn.geo.data.china.geo.ChinaCityDao;
import com.sunzequn.geo.data.utils.MyStringUtils;
import com.sunzequn.geo.data.utils.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sunzequn on 2016/4/14.
 * <p>
 * 处理中国三级行政区划的问题
 */
public class ChinaQuHuaHandler {

    private static UrlTypeDao urlTypeDao = new UrlTypeDao();
    private static ChinaCityDao chinaCityDao = new ChinaCityDao();
    private static SubTitleDao subTitleDao = new SubTitleDao();
    private static String[] sanjiquhua = {"特别行政区", "直辖市", "省", "自治区",
            "地级市", "地区", "自治州", "盟",
            "县", "县级市", "市辖区", "旗", "林区", "特区", "自治县", "自治旗"};

    public static void main(String[] args) {
        countNum();
//        getQuHuaFromOther("山");
//        getQuHuaFromOther("河流");
//        getQuHuaFromOther("湖泊");
    }

    private static boolean isSanjiquhua(String type) {
        Set<String> quhuas = new HashSet<>();
        Collections.addAll(quhuas, sanjiquhua);
        return quhuas.contains(type);
    }

    private static void countNum() {
        List<UrlType> urlTypes = urlTypeDao.getAll1();
        System.out.println(urlTypes.size());
        Set<String> cityLongNames = chinaCityDao.getChinaCityLongName();
        System.out.println(cityLongNames.size());
        Set<String> cityShortNames = chinaCityDao.getChinaCityShortName();
        System.out.println(cityShortNames.size());

        int num = 0;
        for (UrlType urlType : urlTypes) {
            if (isSanjiquhua(urlType.getType())) {
                if (cityLongNames.contains(urlType.getTitle())) {
                    System.out.println("long name : " + urlType);
                    num++;
                } else if (cityShortNames.contains(urlType.getTitle())) {
                    System.out.println("short name : " + urlType);
                    num++;
                }
            }
        }
        System.out.println(num);
    }

    /**
     * 修正其他类别下的行政区划数据
     */
    private static void getQuHuaFromOther(String type) {
        List<UrlType> urlTypes = urlTypeDao.getByType(type);
        System.out.println(urlTypes.size());
        for (UrlType urlType : urlTypes) {
            String subTitle = urlType.getSubtitle();
            subTitle = MyStringUtils.remove(subTitle, "(", ")");
            if (!StringUtils.isNullOrEmpty(subTitle)) {
                if (subTitle.endsWith("自治县")) {
                    urlTypeDao.updateType(urlType.getUrl(), "自治县");
                    System.out.println("自治县");
                } else if (subTitle.endsWith("县")) {
                    urlTypeDao.updateType(urlType.getUrl(), "县");
                    System.out.println("县");
                } else if (subTitle.endsWith("县级市")) {
                    urlTypeDao.updateType(urlType.getUrl(), "县级市");
                    System.out.println("县级市");
                } else if (subTitle.endsWith("市")) {
                    urlTypeDao.updateType(urlType.getUrl(), "地级市");
                    System.out.println("地级市");
                } else if (subTitle.endsWith("镇")) {
                    urlTypeDao.updateType(urlType.getUrl(), "镇");
                    System.out.println("镇");
                } else if (subTitle.endsWith("乡")) {
                    urlTypeDao.updateType(urlType.getUrl(), "乡");
                    System.out.println("乡");
                } else if (subTitle.endsWith("村")) {
                    urlTypeDao.updateType(urlType.getUrl(), "行政村");
                    System.out.println("行政村");
                }
            }
        }
    }
}
