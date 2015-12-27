package com.sunzequn.geocities.data.crawler.application.climate.data;

import com.sunzequn.geocities.data.crawler.simple.parser.PullText;
import org.jsoup.nodes.Document;

/**
 * Created by Sloriac on 15/12/27.
 */
public class CityAmount {

    private static final String PREFIX = "http://en.climate-data.org/continent/";
    private static final String[] SUFFIX = {"africa/", "north-america/",
            "south-america/", "asia/", "europe/", "oceania/"};

    public static void main(String[] args) {
        int num = 0;
        String url;
        AmountParser amountParser = new AmountParser();
        for (int i = 0; i < SUFFIX.length; i++) {
            url = PREFIX + SUFFIX[i];
            int n = amountParser.amount(url);
            num += n;
        }
        System.out.println(num);
    }

}
