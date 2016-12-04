package com.sunzequn.geo.data.geonamesplus;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author sunzequn
 *
 */
public class GeoNameUtils {

    private static final String URI_PREFIX = "http://sws.geonames.org/";

    public static String getUri(int id) {
        return URI_PREFIX + id + "/";
    }
    
    public static String getUriWithBrackets(int id) {
        return "<" + URI_PREFIX + id + "/>";
    }
    
    public static int parseIdWithBrackets(String uri) {
        uri = StringUtils.removeStart(uri, "<" + URI_PREFIX);
        uri = StringUtils.removeEnd(uri, "/>");
        return Integer.parseInt(uri);
    }

    public static int parseId(String uri) {
        uri = StringUtils.removeStart(uri, URI_PREFIX);
        uri = StringUtils.removeEnd(uri, "/");
        return Integer.parseInt(uri);
    }
    
    public static boolean lngLatCheck(double lng, double lat) {
		return (lng >= -180.0) && (lng <= 180.0) && (lat >= -90) && (lat <= 90);
	}

    public static String trim(String uri){
        uri = uri.trim();
        uri = StringUtils.removeStart(uri, "http://sws.geonames.org/");
        uri = StringUtils.removeStart(uri, "http://www.geonames.org/ontology#");
        uri = StringUtils.removeEnd(uri, "/");
        return uri.trim();
    }

    public static String geneRelationUri(String uri){
        return "http://www.geonames.org/ontology#" + uri.trim();
    }
}
