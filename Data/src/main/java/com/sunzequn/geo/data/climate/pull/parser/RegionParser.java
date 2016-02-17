package com.sunzequn.geo.data.climate.pull.parser;

import com.sunzequn.geo.data.climate.bean.Place;
import com.sunzequn.geo.data.climate.bean.PlaceWebWrapper;
import com.sunzequn.geo.data.crawler.parser.HttpMethod;
import com.sunzequn.geo.data.crawler.parser.PullText;
import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.MyStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloriac on 16/1/7.
 * <p>
 * 解析http://en.climate-data.org/网站的Region或部分Country详情页面
 * 抽取Place信息
 */
public class RegionParser extends PullText {


    private static final String PREFIX = "http://en.climate-data.org";
    private static final int TIMEOUT = 5000;
    private static final String FROM_COUNTRY_TABLE_NAME = "climate_seed_place_from_country";
    private static final String TABLE_NAME = "climate_seed_place";

    public PlaceWebWrapper parser(String url, int parentid, Document document) {
        if (document == null) {
            return null;
        }

        Elements as = document.select("a");
        List<Place> places = new ArrayList<>();
        for (Element a : as) {
            Elements datas = a.select("div.data");
            if (ListUtils.isEmpty(datas)) {
                continue;
            } else {
                Element link = a.select("a[href]").first();
                if (link == null) {
                    return null;
                }
                String place_url = link.attr("href");
                if (!place_url.contains("location"))
                    return null;

                String[] parts = org.apache.commons.lang3.StringUtils.split(place_url, "/");
                if (parts.length == 0) {
                    return null;
                }
                int id = Integer.parseInt(parts[parts.length - 1]);

                Element data = datas.first();
                List<String> strings = new ArrayList<>();
                Elements spans = data.select("span");
                for (Element span : spans) {
                    strings.add(span.text());
                }
                if (strings.size() < 4) {
                    continue;
                }
                String name = strings.get(0).trim();
                String climate = StringUtils.removeStart(strings.get(1), "Climate:");
                String temperature = MyStringUtils.remove(strings.get(2), "Average temperature:", "°C");
                String precipitation = MyStringUtils.remove(strings.get(3), "Precipitation:", "mm");

                Place place = new Place(id, name, place_url, parentid, 0, climate, Double.parseDouble(temperature), Double.parseDouble(precipitation));
                places.add(place);
            }
        }

        List<String> nexts = new ArrayList<>();
        Elements elements = document.select("div.pagination");
        System.out.println(elements);
        if ((elements != null ? elements.size() : 0) > 0) {
            Element page = elements.first();
            Elements nextPages = page.select("a[href]");
            if (nextPages.size() > 0) {
                for (Element nextPage : nextPages) {
                    String pageUrl = nextPage.attr("href");
                    pageUrl = url + StringUtils.removeStart(pageUrl, "./");
                    if (!nexts.contains(pageUrl)) {
                        nexts.add(pageUrl);
                    }
                }
            }
        }

        PlaceWebWrapper placeWebWrapper = new PlaceWebWrapper(places, nexts);
        return placeWebWrapper;
    }

//    private void getFromCountry() {
//        CountryDao countryDao = new CountryDao();
//        List<Country> countries = countryDao.getUnvisited();
//        PlaceDao dao = new PlaceDao();
//        for (Country country : countries) {
//            String url = PREFIX + country.getUrl();
//            List<Place> places = parser(url, country.getId());
//            if (places != null) {
//                for (Place place : places) {
//                    dao.save(place, FROM_COUNTRY_TABLE_NAME);
//                }
//                countryDao.update(country.getId(), 1);
//            }
//        }
//    }

    private void getFromRegion() {
//        RegionDao regionDao = new RegionDao();
//        List<Region> regions = regionDao.getUnvisited();
//        PlaceDao dao = new PlaceDao();
//        for (Region region : regions) {
//            String url = PREFIX + region.getUrl();
//            List<Place> places = parser(url, region.getId());
//            if (places != null) {
//                for (Place place : places) {
//                    dao.save(place, TABLE_NAME);
//                }
//                regionDao.update(region.getId(), 1);
//            }
//        }

    }

    public static void main(String[] args) {

//        RegionParser regionParser = new RegionParser();
//        regionParser.getFromCountry();
        //这个方法还没有测试,有空跑一下有没有问题,后面再修改,因为有反爬
//        regionParser.getFromRegion();

        RegionParser regionParser = new RegionParser();
        String url = "http://en.climate-data.org/region/1/?page=2";
        Document document = regionParser.pullFromUrl(url, 5000, HttpMethod.Get);
        PlaceWebWrapper placeWebWrapper = regionParser.parser(url, 2, document);
        System.out.println(placeWebWrapper.getNexts());

    }

}
