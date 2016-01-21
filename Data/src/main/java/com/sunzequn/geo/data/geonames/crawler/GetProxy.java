package com.sunzequn.geo.data.geonames.crawler;

import com.sunzequn.geo.data.crawler.simple.parser.HttpMethod;
import com.sunzequn.geo.data.crawler.simple.parser.PullText;
import com.sunzequn.geo.data.utils.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.omg.CORBA.INTERNAL;

/**
 * Created by Sloriac on 16/1/21.
 */
public class GetProxy extends PullText {

    public ProxyBean get() {
        String url = "http://dev.kuaidaili.com/api/getproxy/?orderid=975338302816480&num=1&area=%E5%A4%A7%E9%99%86&b_pcchrome=1&b_pcie=1&b_pcff=1&protocol=1&method=1&an_an=1&an_ha=1&sp1=1&sep=1";
        Document document = pullFromUrl(url, 5000, HttpMethod.Get);
        Element body = document.select("body").first();
        String text = body.text();
        String[] value = StringUtils.split(text, ":");
        String host = value[0];
        int port = Integer.parseInt(value[1]);
        return new ProxyBean(host, port);
    }

    @Test
    public void test() {
        System.out.println(get());
    }
}
