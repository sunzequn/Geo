package com.sunzequn.geo.data.crawler.parser;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Sloriac on 15/12/20.
 * <p>
 * The basic class for pull html text from a website.
 */
public abstract class PullText {

    /**
     * Get the document from a website
     *
     * @param url     the url of the website
     * @param timeout the maximum time of connecting
     * @param method  http method: get or post
     * @return the document of the website if no errors happen
     */
    public Document pullFromUrl(String url, int timeout, HttpMethod method) {

        try {
            Document document;
            Connection connection = Jsoup.connect(url).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36").timeout(timeout);
            if (method == HttpMethod.Get) {
                document = connection.get();
            } else {
                document = connection.post();
            }
            return document;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}

