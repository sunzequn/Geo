package com.sunzequn.geo.data.geonamesplus;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;


public class GeonameDao extends GnextBaseDao {

    private static final String DEFAULT_Gnext_CONF_FILE = "config/gnext.properties";
    private static final String TABLE = "geoname";
    private static final String GN_URI_PREFIX = "http://sws.geonames.org/";
    private static final String GN_ONTOLOGY_PREFIX = "http://www.geonames.org/ontology#";
    private static String PREFIX;
    private static String SUFFIX;
    private SparqlOperations geonamesOperations;
    
    public GeonameDao(){
    	Properties properties = new Properties();
		InputStream in;
		try {
			in = new FileInputStream(DEFAULT_Gnext_CONF_FILE);
			properties.load(in);
			PREFIX = properties.getProperty("geonames.prefix");
			SUFFIX = properties.getProperty("geonames.suffix");
			geonamesOperations = new SparqlOperations(PREFIX, SUFFIX);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Geoname getById(int geonameId) {
        String sql = "select * from " + TABLE + " where geonameid = " + geonameId;
        List<Geoname> geonames = query(sql, null, Geoname.class);
        if (geonames == null)
            return null;
        return geonames.get(0);
    }
    
    public List<Integer> getObjectNearby(int id){
    	String uri = GeoNameUtils.getUriWithBrackets(id);
    	String predicate = "<" + GN_ONTOLOGY_PREFIX + "nearby" + ">";
    	String sparql = "select * where {" + uri + " " + predicate + " ?o}";
    	return sparqlId(sparql, "o");
    }
    
    public List<Integer> getSubjectNearby(int id){
    	String uri = GeoNameUtils.getUriWithBrackets(id);
    	String predicate = "<" + GN_ONTOLOGY_PREFIX + "nearby" + ">";
    	String sparql = "select * where {?s " + predicate + " " + uri + "}";
    	return sparqlId(sparql, "s");
    }
    
    public List<Integer> getContains(int id) {
    	String uri = GeoNameUtils.getUriWithBrackets(id);
    	String predicate = "<" + GN_ONTOLOGY_PREFIX + "parentFeature" + ">";
    	String sparql = "select * where {?s " + predicate + " " + uri + "}";
        return sparqlId(sparql, "s");
	}
    
    private List<Integer> sparqlId(String sparql, String param){
    	ResultSet resultSet = geonamesOperations.parser(sparql);
        List<Integer> res = new ArrayList<>();
    	if (resultSet != null){
    		while(resultSet.hasNext()) {
            	QuerySolution querySolution = resultSet.next();
            	RDFNode child = querySolution.get(param);
            	int childId = GeoNameUtils.parseId(child.toString());
            	res.add(childId);
    		}
		}
    	return res.size() > 0 ? res : null;
    }

}
