package com.sunzequn.geo.data.geonamesplus;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;

/**
 * 
 * @author sunzequn
 *
 */
public class SparqlOperations {
	
	private String prefix;
    private String suffix;

    public SparqlOperations(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public ResultSet parser(String sparql){
        ParameterizedSparqlString sparqlstr = new ParameterizedSparqlString(sparql);
        //utf-8
        URL queryURL;
		try {
			queryURL = new URL(prefix + URLEncoder.encode(sparqlstr.toString(), "UTF-8") + suffix);
			URLConnection connAPI = queryURL.openConnection();
	        connAPI.setConnectTimeout(6000);
	        connAPI.connect();
	        return ResultSetFactory.fromXML(connAPI.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
        
    }

}
