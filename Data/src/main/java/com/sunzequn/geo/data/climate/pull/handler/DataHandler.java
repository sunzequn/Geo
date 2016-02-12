package com.sunzequn.geo.data.climate.pull.handler;

import com.sunzequn.geo.data.climate.pull.bean.PageUrls;
import com.sunzequn.geo.data.climate.pull.dao.PageUrlsDao;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sloriac on 16-2-12.
 */
public class DataHandler {

    private static final String PREFIX = "http://en.climate-data.org/region/";
    private static final String SUFFIX = "/?page=";
    private static PageUrlsDao pageUrlsDao = new PageUrlsDao();

    public static void main(String[] args) {

        Map<Integer, RegionPageHandler> regionPageHandlerMap = new HashMap<>();
        List<PageUrls> pageUrlses = pageUrlsDao.getAll();
        for (PageUrls pageUrls : pageUrlses) {
            String url = pageUrls.getUrl();
            int id = parseId(url);
            int pageNumber = parsePageNumber(url);
            RegionPageHandler regionPageHandler = regionPageHandlerMap.get(id);
            if (regionPageHandler == null) {
                regionPageHandler = new RegionPageHandler(id);
                regionPageHandlerMap.put(id, regionPageHandler);
            }
            regionPageHandler.addPageNumber(pageNumber);
        }


    }

    private static void addPageUrls(String url) {
        pageUrlsDao.save(new PageUrls(url, 0));
    }

    private static int parseId(String url) {
        url = StringUtils.removeStart(url, PREFIX);
        String[] params = StringUtils.split(url, "/");
        return Integer.parseInt(params[0]);
    }

    private static int parsePageNumber(String url) {
        url = StringUtils.removeStart(url, PREFIX);
        String[] params = StringUtils.split(url, "/");
        url = StringUtils.removeStart(params[1], "?page=");
        return Integer.parseInt(url);
    }
}