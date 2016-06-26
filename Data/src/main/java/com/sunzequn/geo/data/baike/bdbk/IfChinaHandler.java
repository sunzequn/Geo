package com.sunzequn.geo.data.baike.bdbk;

import com.sunzequn.geo.data.baike.chinaquhua.QuhuaUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/27.
 */
public class IfChinaHandler {

    private static UrlTypeDao urlTypeDao = new UrlTypeDao("url_type_zhengli_all_ifchina_copy");
    private static QuhuaUtils quhuaUtils = new QuhuaUtils();

    public static void main(String[] args) {
        handle();
    }

    private static void handle() {
        List<UrlType> urlTypes = urlTypeDao.getAll(-1);
        System.out.println(urlTypes.size());
        List<UrlType> res = new ArrayList<>();
        for (int i = 0; i < urlTypes.size(); i++) {
            UrlType urlType = urlTypes.get(i);
            int status = quhuaUtils.ifChina(urlType);
            urlType.setConfidence(status);
            res.add(urlType);
            if (i > 0 && i % 1000 == 0) {
                System.out.println(i);
                urlTypeDao.updateConfidenceBatch(res);
                res = new ArrayList<>();
            }
        }
        if (res.size() > 0)
            urlTypeDao.updateConfidenceBatch(res);
    }
}
