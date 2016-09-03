//package com.sunzequn.geo.data.baike.chinaquhua;
//
//import com.sunzequn.geo.data.baike.bdbk.*;
//import com.sunzequn.geo.data.baike.clean.CleanUtils;
//import com.sunzequn.geo.data.china.geo.ChinaCity;
//import com.sunzequn.geo.data.china.geo.ChinaCityDao;
//import com.sunzequn.geo.data.utils.ListUtils;
//import com.sunzequn.geo.data.utils.ReadUtils;
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * Created by sunzequn on 2016/4/16.
// */
//public class QuhuaHandler {
//
//    private static String FILE = "D:/DevSpace/github/Geo/Data/src/main/resources/quhua/";
//    private static ChinaCityDao cityDao = new ChinaCityDao();
//    private static UrlTypeDao urlTypeDao = new UrlTypeDao("url_type_quhua");
//    private static TitleDao titleDao = new TitleDao();
//    private static BasicInfoDao basicInfoDao = new BasicInfoDao();
//    private static String KEY = "行政区类别";
//
//    public static void main(String[] args) {
////        queryByLevel("地级市", 2);
////        countDijishi();
////        queryByLevel("县级市", 3);
////        countXianjishi();
////        countQu();
////        countXian();
////        countGuo();
////        countZhen();
////        countXiang();
//        countJiedao();
//    }
//
//    /**
//     * 从自己的城市数据库找出城市名称，去百科查找名称符合的且basicinfo有 行政区类别 属性的词条
//     *
//     * @param type
//     * @param level
//     */
//    private static void queryByLevel(String type, int level) {
//        List<ChinaCity> chinaCities = cityDao.getByLevel(level);
//        if (!ListUtils.isEmpty(chinaCities)) {
//            for (ChinaCity chinaCity : chinaCities) {
//                if (!handleTitle(chinaCity.getShortname(), type)) {
//                    if (!handleTitle(chinaCity.getName(), type)) {
//                        System.out.println("没发现 ： " + chinaCity);
//                    }
//                }
//            }
//        }
//    }
//
//    private static boolean handleTitle(String name, String type) {
//        List<Title> titles = titleDao.getByTitle(name);
//        if (!ListUtils.isEmpty(titles)) {
//            for (Title title : titles) {
//                List<BasicInfo> basicInfos = basicInfoDao.getByUrl(title.getUrl());
//                if (!ListUtils.isEmpty(basicInfos)) {
//                    for (BasicInfo basicInfo : basicInfos) {
//                        if (basicInfo.getKey().trim().equals(KEY)) {
//                            String urltype = basicInfo.getValue().trim();
//                            urltype = CleanUtils.getPropValue(urltype);
//                            if (urltype.contains(type)) {
//                                urlTypeDao.addType(title.getUrl(), type, 12);
//                            } else {
//                                urlTypeDao.addType(title.getUrl(), urltype, 1);
//                            }
//                            break;
//                        }
//                    }
//                }
//            }
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//
////    private static boolean isContainSheng(String subtitle) {
////        Set<String> shengs = QuhuaUtils.getShengShi();
////        for (String sheng : shengs) {
////            if (subtitle.contains(sheng)) {
////                return true;
////            }
////        }
////        return false;
////    }
//
//
//    private static boolean isContainWaiGuo(String summary) {
////        System.out.println("--------------------------------");
////        System.out.println(summary);
//        Set<String> guos = QuhuaUtils.getGuo();
//        guos.remove("中国");
//        for (String guo : guos) {
//            if (summary.contains(guo.trim())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private static void countJiedao() {
//        List<UrlType> urlTypes = urlTypeDao.getByType("街道");
//        for (UrlType urlType : urlTypes) {
//            if (QuhuaUtils.ifGuoWai(urlType)) {
//                System.out.println(urlType);
////                urlTypeDao.updateConfidence(urlType.getUrl(), 1);
//            }
//        }
//    }
//
////    private static void countXiang() {
////        List<UrlType> urlTypes = urlTypeDao.getByType("乡");
////        for (UrlType urlType : urlTypes) {
////            String summary = urlType.getSummary();
////            if (summary.length() > 50) {
////                summary = summary.substring(0, 50);
////            }
////            if (!isContainSheng(urlType.getSubtitle()) && !isContainSheng(summary) && isContainWaiGuo(summary)) {
////                System.out.println(urlType);
//////                urlTypeDao.updateConfidence(urlType.getUrl(), 1);
////            }
////        }
////    }
//
////    private static void countZhen() {
////        List<UrlType> urlTypes = urlTypeDao.getByType("镇");
////        for (UrlType urlType : urlTypes) {
////            String summary = urlType.getSummary();
////            if (summary.length() > 100) {
////                summary = summary.substring(0, 50);
////            }
////            if (!isContainSheng(urlType.getSubtitle()) && !isContainSheng(summary) && isContainWaiGuo(summary)) {
////                System.out.println(urlType);
//////                urlTypeDao.updateConfidence(urlType.getUrl(), 1);
////            }
////        }
////    }
//
//    private static void countGuo() {
//        Set<String> guos = QuhuaUtils.getGuo();
//        System.out.println(guos.size());
//        List<UrlType> urlTypes = urlTypeDao.getByTypeConfidence("国家", 21);
//        System.out.println(urlTypes.size());
//        int num = 0;
//        for (UrlType urlType : urlTypes) {
//            if (guos.contains(urlType.getTitle())) {
//                guos.remove(urlType.getTitle());
//                num++;
////                urlTypeDao.deleteByUrl(urlType.getUrl());
//            } else {
//                System.out.println(urlType);
//            }
//        }
//        System.out.println(num);
//        System.out.println(guos);
//    }
//
//    private static void countXian() {
//        ChinaCityDao dao = new ChinaCityDao();
//        List<ChinaCity> chinaCities = dao.getEndWith("县");
//        List<String> xians = new ArrayList<>();
//        for (ChinaCity chinaCity : chinaCities) {
//            if (!chinaCity.getName().endsWith("自治县")) {
//                xians.add(chinaCity.getName());
//            }
//        }
//        System.out.println(xians.size());
//
//        List<UrlType> urlTypes = urlTypeDao.getByTypeConfidence("县", 5);
//        int num = 0;
//        for (UrlType urlType : urlTypes) {
//            if (urlType.getTitle().endsWith("自治县")) {
//                urlTypeDao.deleteByUrl(urlType.getUrl());
//            } else if (!xians.contains(urlType.getTitle())) {
//                urlTypeDao.updateConfidence(urlType.getUrl(), 4);
//            } else {
//                num++;
//            }
//        }
//        System.out.println(num);
//    }
//
//    private static void countQu() {
//        String quFile = FILE + "qu.txt";
//        ReadUtils readUtils = new ReadUtils(quFile);
//        List<String> lines = readUtils.readByLine();
//        List<String> qus = new ArrayList<>();
//        for (String line : lines) {
//            line = line.trim();
//            String[] strings = StringUtils.split(line, "、");
//            for (String s : strings) {
//                s = s.trim();
//                qus.add(s);
//            }
//        }
//        System.out.println(qus.size());
//
//        List<UrlType> urlTypes = urlTypeDao.getByTypeConfidence("市辖区", 6);
//        List<String> myqu = new ArrayList<>();
//        int num = 0;
//        for (UrlType urlType : urlTypes) {
//            if (qus.contains(urlType.getTitle())) {
//                num++;
////                urlTypeDao.updateConfidence(urlType.getUrl(), 6);
//                myqu.add(urlType.getTitle());
//            } else {
//                System.out.println(urlType);
//            }
//        }
//        System.out.println(num);
//
//        qus.removeAll(myqu);
//        System.out.println(qus);
//    }
//
//    private static void countXianjishi() {
//        String dijixianFile = FILE + "xianjishi";
//        ReadUtils readUtils = new ReadUtils(dijixianFile);
//        List<String> lines = readUtils.readByLine();
//        Set<String> xianji = new HashSet<>();
//        Set<String> xianjishi = new HashSet<>();
//        for (String line : lines) {
//            line = line.trim();
//            String[] strings = StringUtils.split(line, " ");
//            for (String s : strings) {
//                s = s.trim();
//                xianjishi.add(s);
//                s = StringUtils.removeEnd(s, "市");
//                xianji.add(s);
//            }
//        }
//        System.out.println(xianji.size());
//        System.out.println(xianjishi.size());
//
//        List<UrlType> urlTypes = urlTypeDao.getByType("县级市");
//        Set<String> myxianjishi = new HashSet<>();
//        System.out.println(urlTypes.size());
//        int num = 0;
//        for (UrlType urlType : urlTypes) {
//            myxianjishi.add(urlType.getTitle());
//            if (xianji.contains(urlType.getTitle()) || xianjishi.contains(urlType.getTitle())) {
//                num++;
//            } else {
//                System.out.println(urlType);
//            }
//        }
//        System.out.println(num);
//        xianji.removeAll(myxianjishi);
//        System.out.println(xianji);
//
//    }
//
//
//    private static void countDijishi() {
//        String dijishiFile = FILE + "dijishi.txt";
//        ReadUtils readUtils = new ReadUtils(dijishiFile);
//        List<String> lines = readUtils.readByLine();
//        Set<String> dijishi = new HashSet<>();
//        for (String line : lines) {
//            line = line.trim();
//
//            if (line.contains(" ")) {
//                String[] strings = StringUtils.split(line, " ");
//                for (String s : strings) {
//                    s = s.trim();
//                    dijishi.add(s);
//                }
//            } else {
//                dijishi.add(line);
//            }
//        }
//        System.out.println(dijishi.size());
//        List<UrlType> urlTypes = urlTypeDao.getByType("地级市");
//        System.out.println(urlTypes.size());
//        Set<String> mydijishi = new HashSet<>();
//        int num = 0;
//        for (UrlType urlType : urlTypes) {
//            mydijishi.add(urlType.getTitle());
//            if (dijishi.contains(urlType.getTitle().trim())) {
//                num++;
//            }
//        }
//        System.out.println(num);
//        dijishi.removeAll(mydijishi);
//        System.out.println(dijishi);
//    }
//}
