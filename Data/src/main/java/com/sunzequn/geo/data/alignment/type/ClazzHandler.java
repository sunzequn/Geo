package com.sunzequn.geo.data.alignment.type;

import com.sunzequn.geo.data.alignment.query.QueryDbpedia;
import com.sunzequn.geo.data.crawler.wrapper.LimitedSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sloriac on 16/2/27.
 */
public class ClazzHandler {

    private QueryDbpedia queryDbpedia = new QueryDbpedia();

    public Clazz getEntity(String uri) {

        Clazz entity = new Clazz(uri);
        List<String> types = queryDbpedia.queryType(uri);
        Map<String, String> equivalentClassMap = new HashMap<>();
        for (String type : types) {

        }

        return null;
    }
}
