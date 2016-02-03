package com.sunzequn.geo.data.geonames.crawler;

import com.sunzequn.geo.data.crawler.simple.parser.HttpMethod;
import com.sunzequn.geo.data.crawler.simple.parser.PullText;
import com.sunzequn.geo.data.utils.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.omg.CORBA.INTERNAL;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sloriac on 16/1/21.
 */
public class GetProxy extends PullText {

    private HttpConnector httpConnector = new HttpConnector();

    public ProxyBean get() {
        while (true) {
            try {
                String url = "http://dev.kuaidaili.com/api/getproxy/?orderid=975338302816480&num=1&b_pcchrome=1&b_pcie=1&b_pcff=1&protocol=1&method=1&an_an=1&an_ha=1&sp1=1&sp2=1&sp3=1&quality=1&sort=1&sep=1";
                Document document = pullFromUrl(url, 10000, HttpMethod.Get);
                Element body = document.select("body").first();
                String text = body.text();
                String[] value = StringUtils.split(text, ":");
                String host = value[0];
                int port = Integer.parseInt(value[1]);
                return new ProxyBean(host, port);
            } catch (Exception e) {
                System.out.println("get proxy failed , try again");
                e.printStackTrace();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public synchronized LinkedList<ProxyBean> get666() {

        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("------ : " + new Date());
        try {
            //all
//            String url ="http://qsdrk.daili666api.com/ip/?tid=558067874820839&num=5&delay=5&category=2";

            //foreign
//            String url = "http://xvre.daili666api.com/ip/?tid=558067874820839&num=5&delay=5&category=2&foreign=only&filter=on";

            //foreign no filter
//            String url = "http://qsdrk.daili666api.com/ip/?tid=558067874820839&num=5&delay=5&category=2&foreign=only";


//            String url = "http://xvre.daili666api.com/ip/?tid=558067874820839&num=5&category=2&foreign=none&filter=on";

            //kuai dai li
            String url = "http://dev.kuaidaili.com/api/getproxy/?orderid=985438635875134&num=50&b_pcchrome=1&b_pcie=1&b_pcff=1&protocol=1&method=1&an_an=1&an_ha=1&sp1=1&sp2=1&sep=3";
            HttpConnector httpConnector = new HttpConnector();
            Response response = httpConnector.setUrl(url).getConnection().setTimeout(8000).getContent();
            String text = response.getContent();
            String[] ips = StringUtils.split(text, " ");
            List<ProxyBean> proxyBeans = new ArrayList<>();
            for (String ip : ips) {
                String[] strings = StringUtils.split(ip, ":");
                ProxyBean proxyBean = new ProxyBean(strings[0], Integer.parseInt(strings[1]));
                proxyBeans.add(proxyBean);
            }
            if (proxyBeans.size() > 0) {
                return new LinkedList<>(proxyBeans);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Test
    public void test() throws Exception {
//        System.out.println(get666());
        System.out.println(get());
    }
}
