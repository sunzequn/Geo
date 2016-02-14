package com.sunzequn.geo.data.climate.pull;

import com.sunzequn.geo.data.climate.pull.bean.PageUrls;
import com.sunzequn.geo.data.climate.pull.bean.Place;
import com.sunzequn.geo.data.climate.pull.bean.PlaceWebWrapper;
import com.sunzequn.geo.data.climate.pull.bean.Region;
import com.sunzequn.geo.data.climate.pull.dao.PageUrlsDao;
import com.sunzequn.geo.data.climate.pull.dao.PlaceDao;
import com.sunzequn.geo.data.climate.pull.dao.RegionDao;
import com.sunzequn.geo.data.climate.pull.parser.RegionParser;
import com.sunzequn.geo.data.crawler.proxy.ProxyBean;
import com.sunzequn.geo.data.crawler.proxy.ProxyHandler;
import com.sunzequn.geo.data.geonames.crawler.HttpConnector;
import com.sunzequn.geo.data.geonames.crawler.Response;
import com.sunzequn.geo.data.utils.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sloriac on 16/2/11.
 */
public class PageUrlMian {

    private static final int THREAD_NUM = 60;
    private static final int TIMEOUT = 7000;
    private static final String PREFIX = "http://en.climate-data.org/region/";
    private static final String SUFFIX = "/";
    private static final String TABLE_NAME = "climate_seed_place";

    private static LinkedList<String> urls = new LinkedList<>();
    private static ProxyHandler proxyHandler = new ProxyHandler();
    private static PlaceDao placeDao = new PlaceDao(TABLE_NAME);
    private static PageUrlsDao pageUrlsDao = new PageUrlsDao();

    public static void main(String[] args) {
        initUrls();
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(() -> {
                ProxyBean proxy = getProxy();
                RegionParser regionParser = new RegionParser();
                while (true) {
                    try {
                        HttpConnector httpConnector = new HttpConnector();
                        String url = getUrl();
                        if (url == null) {
                            System.out.println(Thread.currentThread().getName() + "is over");
                            return;
                        }
                        Response response = httpConnector.setUrl(url)
                                .setProxy(proxy.getHost(), proxy.getPort())
                                .getConnection().setTimeout(TIMEOUT).getContent();

                        if (response.getCode() != 200) {
                            addUrl(url);
                            proxy = getProxy();
                            continue;
                        }
                        int id = parseId(url);
                        String string = response.getContent().trim();
                        Document document = Jsoup.parse(string);
                        PlaceWebWrapper placeWebWrapper = regionParser.parser(url, id, document);
                        if (placeWebWrapper != null) {
                            List<Place> places = placeWebWrapper.getPlaces();
                            if (!ListUtils.isEmpty(places)) {
                                addPlace(places);
                            }
                            updateId(url, 2);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }, "thread" + i).start();
        }
    }

    private static void initUrls() {
        List<PageUrls> pageUrlses = pageUrlsDao.getUnvisited();
        if (pageUrlses != null || pageUrlses.size() > 0) {
            for (PageUrls pageUrls : pageUrlses) {
                urls.add(pageUrls.getUrl());
            }
        }
        System.out.println("unvisited pageurl : " + urls.size());
    }

    private static synchronized void refreshProxy() {
        proxyHandler.refreshProxy();
    }

    private static synchronized ProxyBean getProxy() {
        return proxyHandler.getProxy();
    }


    private static synchronized String getUrl() {
        if (urls.size() > 0) {
            return urls.pop();
        }
        return null;
    }

    private static synchronized void addUrl(String url) {
        urls.add(url);
    }

    private static synchronized void addPlace(List<Place> places) {
        if (places == null || places.size() == 0) {
            return;
        }
        for (Place place : places) {
            placeDao.save(place);
        }
    }

    private static synchronized void updateId(String url, int status) {
        pageUrlsDao.update(url, status);
    }

    private static int parseId(String url) {
        url = StringUtils.removeStart(url, PREFIX);
        String[] params = StringUtils.split(url, "/");
        return Integer.parseInt(params[0]);
    }

}
