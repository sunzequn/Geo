package com.sunzequn.geo.data.geonames.statistic;

import com.sunzequn.geo.data.crawler.parser.HttpMethod;
import com.sunzequn.geo.data.crawler.parser.PullText;
import com.sunzequn.geo.data.utils.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Sloriac on 16/3/23.
 */
public class StatisticHandler extends PullText {

    private static final String URL = "http://www.geonames.org/statistics/china.html";
    private static StatisticInfoDao statisticInfoDao = new StatisticInfoDao();


    public static void main(String[] args) {
        StatisticHandler handler = new StatisticHandler();
        handler.pull();
    }

    private void pull() {
        Document document = pullFromUrl(URL, 5000, HttpMethod.Get);
        Elements trs = document.select("tr");
        for (Element tr : trs) {
            Elements tds = tr.select("td");
            if (tds.size() == 4) {
                String num = tds.get(0).text();
                String code = tds.get(1).text();
                String name = tds.get(2).text();
                String description = tds.get(3).text();
                if (StringUtils.isNullOrEmpty(num) || StringUtils.isNullOrEmpty(code)
                        || StringUtils.isNullOrEmpty(name)) {
                    System.out.println(num + " " + code + " " + name + " " + description);
                    continue;
                }
                if (num.contains(".")) {
                    num = num.replace(".", "");
                }
//                int number = Integer.valueOf(num);
//                StatisticInfo info = new StatisticInfo(code, number, name, description);
//                statisticInfoDao.save(info);
            }
        }
    }

}
