package com.sunzequn.geo.data.baike.handler;

import com.sunzequn.geo.data.baike.bdbk.UrlType;
import com.sunzequn.geo.data.baike.bdbk.UrlTypeDao;

import java.net.URLDecoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sunzequn on 2016/4/19.
 */
public class MiBuDelete0 {
    private static UrlTypeDao urlTypeDao = new UrlTypeDao();

    public static void main(String[] args) {

        List<UrlType> news = urlTypeDao.getAll();
        Set<String> urls = new HashSet<>();
        for (UrlType aNew : news) {
            urls.add(aNew.getUrl().trim());
        }
        System.out.println(urls.size());
        UrlTypeDao oldDao = new UrlTypeDao("url_type_before_rule2");
        List<UrlType> oldUrls = oldDao.getAll(0);
        int num = 0;
        for (UrlType oldUrl : oldUrls) {
            if (!urls.contains(oldUrl.getUrl().trim())) {
                num++;
                urlTypeDao.addType(oldUrl);
            }
        }
        System.out.println(num);
    }
}
