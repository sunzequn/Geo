package com.sunzequn.geo.data.baike.bdbk;

import com.sunzequn.geo.data.utils.ListUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sunzequn on 2016/4/27.
 */
public class RedirctHandler {

    private static UrlTypeDao urlTypeDao = new UrlTypeDao();
    private static RedirectDao redirectDao = new RedirectDao();
    private static RedirectUrlDao redirectUrlDao = new RedirectUrlDao();

    public static void main(String[] args) {
//        handle();
        handle2();
    }

    private static void handle2() {
        List<RedirectUrl> redirectUrls = redirectUrlDao.getAll();
        System.out.println(redirectUrls.size());
        for (RedirectUrl redirectUrl : redirectUrls) {
            urlTypeDao.addType(redirectUrl.getRedirect_url(), redirectUrl.getType(), 1);
        }
    }

    private static void handle() {
        List<UrlType> urlTypes = urlTypeDao.getAll1();
        System.out.println(urlTypes.size());
        Set<String> urls = new HashSet<>();
//        List<RedirectUrl> res = new ArrayList<>();
        for (UrlType urlType : urlTypes) {
            urls.add(urlType.getUrl());
        }
        List<Redirect> redirects = redirectDao.getAll();
        for (Redirect redirect : redirects) {
            if (urls.contains(redirect.getSrc()) && !urls.contains(redirect.getDest())) {
                UrlType urlType = urlTypeDao.getByUrl(redirect.getSrc());
                RedirectUrl redirectUrl = new RedirectUrl(redirect.getSrc(), redirect.getDest(), urlType.getType());
                redirectUrlDao.add(redirectUrl);
            }
        }
    }

    private static List<RedirectUrl> getRedirects(UrlType urlType) {
        Set<String> redirectUrls = new HashSet<>();
        String url = urlType.getUrl();
        List<Redirect> redirectSrc = redirectDao.getBySrc(url);
        if (!ListUtils.isEmpty(redirectSrc)) {
            for (Redirect redirect : redirectSrc) {
                redirectUrls.add(redirect.getDest());
            }
        }

        List<Redirect> redirectDest = redirectDao.getByDest(url);
        if (!ListUtils.isEmpty(redirectDest)) {
            for (Redirect redirect : redirectDest) {
                redirectUrls.add(redirect.getSrc());
            }
        }

        if (redirectUrls.size() > 0) {
            redirectUrls.remove(url);
            List<RedirectUrl> res = new ArrayList<>();
            for (String redirectUrl : redirectUrls) {
                RedirectUrl redirectUrl1 = new RedirectUrl(url, redirectUrl, urlType.getType());
                res.add(redirectUrl1);
            }
            return res;
        }
        return null;
    }

}
