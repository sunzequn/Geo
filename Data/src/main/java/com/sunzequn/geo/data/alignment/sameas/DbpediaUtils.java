package com.sunzequn.geo.data.alignment.sameas;

/**
 * Created by sloriac on 16-2-28.
 */
public class DbpediaUtils {

    private static final String DBO = "http://dbpedia.org/ontology/";

    private static final String DBR = "http://dbpedia.org/resource/";

    public static String dbo(String uri) {
        if (uri.startsWith(DBO)) {
            uri = uri.replace(DBO, "dbo:");
        }
        return uri;
    }

    public static String dbr(String uri) {
        if (uri.startsWith(DBR)) {
            uri = uri.replace(DBR, "dbr:");
        }
        return uri;
    }
}
