//package com.sunzequn.geo.data.baike.chinaquhua;
//
//import com.sunzequn.geo.data.baike.bdbk.UrlType;
//import com.sunzequn.geo.data.baike.bdbk.UrlTypeDao;
//
//import java.util.List;
//
///**
// * Created by sunzequn on 2016/4/24.
// */
//public class GuowaiQuhuaHandler {
//
//    private static UrlTypeDao urlTypeDao = new UrlTypeDao("url_type_quhua_broad");
//
//    public static void main(String[] args) {
//        handleGuowai();
//    }
//
//    private static void handleGuowai(){
//        List<UrlType> urlTypes = urlTypeDao.getAll();
//        for (UrlType urlType : urlTypes) {
//            if (QuhuaUtils.ifGuoWai(urlType)){
//                System.out.println(urlType);
//                urlTypeDao.updateConfidence(urlType.getUrl(), 2);
//            }
//        }
//    }
//}
