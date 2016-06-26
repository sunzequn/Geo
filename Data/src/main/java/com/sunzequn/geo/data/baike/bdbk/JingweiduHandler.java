package com.sunzequn.geo.data.baike.bdbk;

import com.sunzequn.geo.data.china.geo.ChinaCity;
import com.sunzequn.geo.data.china.geo.ChinaCityDao;
import com.sunzequn.geo.data.china.geonames.China;
import com.sunzequn.geo.data.utils.StringUtils;
import org.apache.jena.ext.com.google.common.io.LittleEndianDataOutputStream;
import org.apache.jena.ext.com.google.common.util.concurrent.AbstractScheduledService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/27.
 */
public class JingweiduHandler {

    private static JingweiduDao jingweiduDao = new JingweiduDao();

    public static void main(String[] args) {
//        handleChinaQuhua();
//        handleOther("乡", "村庄");
//        handleOther("乡", "乡镇");
//        handleOther("镇", "村庄");
//        handleOther("镇", "乡镇");
//        handleOther("民族乡", "乡镇");
//        handleOther("民族乡", "村庄");
//        handleOther("街道", "乡镇");
//        handleOther("街道", "道路");
//        handleOther("行政村", "村庄");
//        handleOther("行政村", "乡镇");
//        handleOther("社区", "地产小区");
//        handleOther("社区", "村庄");
//
//        handleOther("机关", "政府机构");
//
//        handleOther("古迹景观", "旅游景点");
//        handleOther("文化艺术景观", "旅游景点");
//        handleOther("民俗景观", "旅游景点");
//        handleOther("民俗景观", "乡镇");
//        handleOther("地质地貌景观", "旅游景点");
//        handleOther("气候气象景观", "旅游景点");
//        handleOther("水文景观", "旅游景点");
//        handleOther("森林公园", "旅游景点");
//        handleOther("自然保护区", "旅游景点");
//
//        handleOther("机场", "飞机场");
//        handleOther("桥", "桥");
//        handleOther("桥", "道路");
//        handleOther("汽车站", "长途汽车站");
//        handleOther("港口", "港口/码头");
//        handleOther("火车站", "火车站");
//        handleOther("街区路", "道路");

        ///---------------------

//        handleOther("隧道", "道路");
//        handleOther("大坝", "区县");
//        handleOther("大坝", "村庄");
//        handleOther("水库", "区县");
//        handleOther("水库", "村庄");
//
//        handleOther("商场", "购物");
//        handleOther("商场", "商圈");

        //==========

//        handleOther("宾馆", "宾馆");
//        handleOther("楼盘", "地产小区");
//        handleOther("银行", "金融");
//        handleOther("餐厅", "餐饮");
//        handleOther("体育馆", "休闲娱乐");
//        handleOther("体育场", "休闲娱乐");
//        handleOther("公园", "区县");
//        handleOther("医院", "医疗");
//        handleOther("博物馆", "旅游景点");
//        handleOther("学校", "教育");
//        handleOther("广场", "购物");
//        handleOther("广场", "生活服务");
//        handleOther("广场", "休闲娱乐");

        //----------------

//        handleOther("湿地", "旅游景点");
//        handleOther("森林", "旅游景点");
//        handleOther("关口", "旅游景点");
//        handleOther("山峰", "旅游景点");

        out();
    }

    private static void out() {
        String prefix = "http://ws.nju.edu.cn/clinga/A";
        UrlIndexDao indexDao = new UrlIndexDao();
        List<Jingweidu> jingweidus = jingweiduDao.getAll();
        List<String> oldUrls = new ArrayList<>();
        for (Jingweidu jingweidu : jingweidus) {
            oldUrls.add(jingweidu.getUrl());
            int id = indexDao.getByUrl(jingweidu.getUrl()).getId();
            jingweidu.setUrl(prefix + id);
        }
        System.out.println(jingweidus.size());
        System.out.println(oldUrls.size());
        jingweiduDao.updateBatch(jingweidus, oldUrls);
    }

    private static void handleOther(String type, String level) {
        UrlTypeDao urlTypeDao = new UrlTypeDao("url_type_zhengli_all_ifchina");
        UrlTypeLocationDao locationDao = new UrlTypeLocationDao();
        List<UrlType> urlTypes = urlTypeDao.getByTypeConfidence(type, 2);
        System.out.println(urlTypes.size());
        int num = 0;
        List<Jingweidu> jingweidus = new ArrayList<>();
        for (UrlType urlType : urlTypes) {
            UrlTypeLocation location = locationDao.getByUrl(urlType.getUrl());
            int confidence = 25;
            if (!StringUtils.isNullOrEmpty(urlType.getSubtitle())) {
                confidence = 10;
            }
            if (location != null && location.getLevel().equals(level) && location.getConfidence() >= confidence) {
//                System.out.println(location);
                Jingweidu jingweidu = new Jingweidu(urlType.getUrl(), location.getLng(), location.getLat());
//                jingweiduDao.add(jingweidu);
                jingweidus.add(jingweidu);
                num++;
            }
        }
        System.out.println(num);
        if (jingweidus.size() > 0)
            jingweiduDao.addBatch(jingweidus);
    }

    private static void handleChinaQuhua() {
        UrlTypeDao urlTypeDao = new UrlTypeDao("url_type_quhua");
        ChinaCityDao chinaCityDao = new ChinaCityDao();
        List<UrlType> quhuas = urlTypeDao.getAbove(7);
        System.out.println(quhuas.size());
        List<Jingweidu> jingweidus = new ArrayList<>();
        for (UrlType quhua : quhuas) {
            List<ChinaCity> chinaCities = chinaCityDao.getByName(quhua.getTitle());
            if (chinaCities != null && chinaCities.size() == 1) {
                ChinaCity chinaCity = chinaCities.get(0);
                Jingweidu jingweidu = new Jingweidu(quhua.getUrl(), chinaCity.getLng(), chinaCity.getLat());
//                jingweiduDao.add(jingweidu);
                jingweidus.add(jingweidu);
            }
        }
        jingweiduDao.addBatch(jingweidus);
    }


}
