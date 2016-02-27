package com.sunzequn.geo.data.alignment;

import com.sunzequn.geo.data.alignment.query.QueryDbpedia;
import org.junit.Test;

/**
 * Created by Sloriac on 16/2/27.
 */
public class QueryDbpediaTest {

    public static void main(String[] args) {
        QueryDbpedia queryDbpedia = new QueryDbpedia();
//        String uri = "http://dbpedia.org/resource/France";
//        System.out.println(queryDbpedia.queryType(uri));
        String uri = "http://dbpedia.org/ontology/Place";
        System.out.println(queryDbpedia.queryEquivalentClass(uri));
    }
}
