package com.sunzequn.geo.data.crawler.application.climate.pull.parser;

import com.sunzequn.geo.data.crawler.application.climate.pull.bean.Country;
import com.sunzequn.geo.data.crawler.application.climate.pull.bean.Place;
import com.sunzequn.geo.data.crawler.application.climate.pull.bean.Region;
import com.sunzequn.geo.data.crawler.application.climate.pull.dao.CountryDao;
import com.sunzequn.geo.data.crawler.application.climate.pull.dao.PlaceFromCountryDao;
import com.sunzequn.geo.data.crawler.simple.parser.HttpMethod;
import com.sunzequn.geo.data.crawler.simple.parser.PullText;
import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.StringUtil;
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

    public List<Place> parser(String url, int parentid) {

        Document document = pullFromUrl(url, TIMEOUT, HttpMethod.Get);
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

                String[] parts = StringUtils.split(place_url, "/");
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
                String climate = StringUtil.removePrefix(strings.get(1), "Climate:");
                String temperature = StringUtil.remove(strings.get(2), "Average temperature:", "°C");
                String precipitation = StringUtil.remove(strings.get(3), "Precipitation:", "mm");

                Place place = new Place(id, name, place_url, parentid, 0, climate, Double.parseDouble(temperature), Double.parseDouble(precipitation));
                places.add(place);
            }
        }
        if (places.size() > 0)
            return places;
        return null;
    }

    public void getFromCountry() {
        CountryDao countryDao = new CountryDao();
        List<Country> countries = countryDao.getUnvisited();
        PlaceFromCountryDao dao = new PlaceFromCountryDao();
        for (Country country : countries) {
            String url = PREFIX + country.getUrl();
            List<Place> places = parser(url, country.getId());
            if (places != null) {
                countryDao.update(country.getId(), 1);
                for (Place place : places) {
                    dao.save(place);
                }
            }
        }
    }

    public static void main(String[] args) {

        RegionParser regionParser = new RegionParser();
        regionParser.getFromCountry();

    }


}
