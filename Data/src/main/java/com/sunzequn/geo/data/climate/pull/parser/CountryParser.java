package com.sunzequn.geo.data.climate.pull.parser;

import com.sunzequn.geo.data.climate.bean.Country;
import com.sunzequn.geo.data.climate.bean.Region;
import com.sunzequn.geo.data.climate.dao.CountryDao;
import com.sunzequn.geo.data.climate.dao.RegionDao;
import com.sunzequn.geo.data.crawler.parser.HttpMethod;
import com.sunzequn.geo.data.crawler.parser.PullText;
import com.sunzequn.geo.data.utils.DateUtils;
import com.sunzequn.geo.data.utils.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloriac on 16/1/7.
 * <p>
 * 解析http://en.climate-data.org/网站的Country详情页面
 * 抽取Region信息
 */
public class CountryParser extends PullText {

    private static final String PREFIX = "http://en.climate-data.org";
    private static final int TIMEOUT = 5000;

    public List<Region> parser(Country country) {
        String url = PREFIX + country.getUrl();
        Document document = pullFromUrl(url, TIMEOUT, HttpMethod.Get);
        if (document == null) {
            return null;
        }
        Elements uls = document.select("ul");
        if (ListUtils.isEmpty(uls)) {
            return null;
        }
        Element ul = uls.last();
        Elements lis = ul.select("li");
        if (ListUtils.isEmpty(lis)) {
            return null;
        }

        List<Region> regions = new ArrayList<>();
        for (Element li : lis) {
            Region region = extract(li, country.getId());
            if (region != null) {
                regions.add(region);
            }
        }
        if (regions.size() > 0) {
            return regions;
        }
        return null;
    }

    private Region extract(Element element, int parentid) {
        String name = element.text().trim();
        if (name.equals("")) {
            return null;
        }
        Element link = element.select("a[href]").first();
        if (link == null) {
            return null;
        }
        String url = link.attr("href");
        if (!url.contains("region"))
            return null;
        String[] parts = StringUtils.split(url, "/");
        if (parts.length == 0) {
            return null;
        }
        int id = Integer.parseInt(parts[parts.length - 1]);
        Region region = new Region(id, name, url, parentid, 0);
        return region;
    }

    public static void main(String[] args) {
        CountryParser countryParser = new CountryParser();
        CountryDao countryDao = new CountryDao();
        RegionDao regionDao = new RegionDao();
        List<Country> countries = countryDao.getUnvisited();
        for (Country country : countries) {
            List<Region> regions = countryParser.parser(country);
            if (regions != null) {
                countryDao.update(country.getId(), 1);
                for (Region region : regions) {
                    regionDao.save(region);
                    System.out.println(region);
                }
            }
            try {
                Thread.sleep(DateUtils.randomTimeMilli());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
