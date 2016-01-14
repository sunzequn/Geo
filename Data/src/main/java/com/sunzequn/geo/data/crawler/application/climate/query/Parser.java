package com.sunzequn.geo.data.crawler.application.climate.query;

import com.sunzequn.geo.data.crawler.simple.parser.PullText;
import com.sunzequn.geo.data.utils.MyStringUtils;
import com.sunzequn.geo.data.crawler.simple.parser.HttpMethod;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloriac on 15/12/20.
 */
public class Parser extends PullText {

    private static final String PREFIX = "http://en.climate-data.org/search/?q=";

    public Parser() {
    }

    public CityWithClimate parse(City city) {
        String url = PREFIX + city.getCity();
        Document document = pullFromUrl(url, 150000, HttpMethod.Get);
        if (document == null) {
            return null;
        }
        Elements elements = document.select("div.data");
        if (elements == null) {
            return null;
        }
        for (Element element : elements) {
            List<String> strings = new ArrayList<>();
            Elements spans = element.select("span");
            for (Element span : spans) {
                strings.add(span.text());
            }
            if (city.getCountry().equals(strings.get(1).trim())) {
                int climateIndex = 3;
                int temperatureIndex = 4;
                int precipitationIndex = 5;

                if (strings.size() == 5) {
                    climateIndex -= 1;
                    temperatureIndex -= 1;
                    precipitationIndex -= 1;
                } else if (!city.getProvince().equals(strings.get(2).trim())) {
                    continue;
                }
                CityWithClimate cityWithClimate = new CityWithClimate(city);
                cityWithClimate.setClimate(MyStringUtils.removePrefix(strings.get(climateIndex), "Climate:"));
                cityWithClimate.setTemperature(MyStringUtils.remove(strings.get(temperatureIndex), "Average temperature:", "°C"));
                cityWithClimate.setPrecipitation(MyStringUtils.remove(strings.get(precipitationIndex), "Precipitation:", "mm"));
                return cityWithClimate;
            }
        }
        return null;
    }
}
