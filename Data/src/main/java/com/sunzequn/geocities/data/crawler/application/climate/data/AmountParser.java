package com.sunzequn.geocities.data.crawler.application.climate.data;

import com.sunzequn.geocities.data.crawler.simple.parser.HttpMethod;
import com.sunzequn.geocities.data.crawler.simple.parser.PullText;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Sloriac on 15/12/27.
 */
public class AmountParser extends PullText {

    private static final int TIMEOUT = 5000;

    public int amount(String url) {
        int num = 0;
        Document document = pullFromUrl(url, TIMEOUT, HttpMethod.Get);
        Element table;
        Elements tables = document.select("tbody");
        if (tables.size() > 0) {
            table = tables.first();
            Elements trs = table.select("tr");
            for (Element tr : trs) {
                Elements tds = tr.select("td");
                if (tds.size() > 2) {
                    Element td = tds.get(1);
                    int value = Integer.parseInt(td.text());
                    num += value;
                }
            }
            return num;
        }
        return 0;
    }
}
