package com.sunzequn.geo.data.crawler.application.climate.pull.parser;

import com.sunzequn.geo.data.crawler.application.climate.pull.bean.Continent;
import com.sunzequn.geo.data.crawler.application.climate.pull.bean.Country;
import com.sunzequn.geo.data.crawler.simple.parser.HttpMethod;
import com.sunzequn.geo.data.crawler.simple.parser.PullText;
import com.sunzequn.geo.data.utils.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloriac on 16/1/6.
 * <p>
 * 解析http://en.climate-data.org/网站的Continent详情页面
 */
public class ContinentParser extends PullText {

    private static final String PREFIX = "http://en.climate-data.org";
    private static final int TIMEOUT = 5000;

    public List<Country> parser(Continent continent) {

        String url = PREFIX + continent.getUrl();
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

        List<Country> countries = new ArrayList<>();
        for (Element li : lis) {
            Country country = extract(li, continent.getId());
            if (country != null) {
                countries.add(country);
            }
        }

        if (countries.size() > 0) {
            return countries;
        }
        return null;
    }

    private Country extract(Element element, int parentid) {

        String name = element.text().trim();
        if (name.equals("")) {
            return null;
        }
        Element link = element.select("a[href]").first();
        if (link == null) {
            return null;
        }
        String url = link.attr("href");
        String[] parts = StringUtils.split(url, "/");
        if (parts.length == 0) {
            return null;
        }
        int id = Integer.parseInt(parts[parts.length - 1]);
        Country country = new Country(id, name, url, parentid);
        return country;
    }

    public static void main(String[] args) {
        ContinentParser continentParser = new ContinentParser();
        Continent continent = new Continent(1, "Africa", "/continent/africa/");
        System.out.println(continentParser.parser(continent));
    }

}
