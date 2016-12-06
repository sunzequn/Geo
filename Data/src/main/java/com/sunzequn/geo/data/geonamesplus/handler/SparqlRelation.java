package com.sunzequn.geo.data.geonamesplus.handler;

import com.sunzequn.geo.data.utils.WriteUtils;
import com.sunzequn.geo.data.virtuoso.VirtGraphLoader;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.rdf.model.RDFNode;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sloriac on 16-12-2.
 */
public class SparqlRelation {


    private static final String PREFIX = "http://210.28.132.62:8891/sparql?default-graph-uri=&query=";
    private static final String SUFFIX = "&format=xml%2Fhtml&timeout=0&debug=on";
    private static final String FILE = "/home/sloriac/code/Geo/Data/src/main/resources/geonames_a_relations";
    private static VirtGraph virtGraph = VirtGraphLoader.getInstance().getGeonamesVirtGraph();
    private static WriteUtils writeUtils = new WriteUtils(FILE, false);


    public static void main(String[] args) throws Exception {
        String[] relations = {"parentCountry",
                "parentADM1", "parentADM2", "parentADM3", "parentADM4",
                "parentFeature", "nearby", "neighbour"};
        for (String relation : relations) {
            queryRelation(relation);
        }
        writeUtils.close();
    }

    public static void queryRelation(String relation) throws Exception {
        String relationUri = geneRelationUri(relation);
//        System.out.println(relationUri);
        Set<String> res = new HashSet<>();
//        String sparql = "select * where { GRAPH ?graph {?s <" + relationUri + "> ?o . ?s <http://www.geonames.org/ontology#countryCode> \"CN\"}}";
        String sparql = "select * where { GRAPH ?graph {?s <" + relationUri + "> ?o . ?s <http://www.geonames.org/ontology#featureClass> <http://www.geonames.org/ontology#A>}}";
//        System.out.println(sparql);
        VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, virtGraph);
        ResultSet results = vqe.execSelect();
        while (results.hasNext()) {
            QuerySolution result = results.nextSolution();
            String s = result.get("s").toString();
            String o = result.get("o").toString();
            String line = trim(s) + " " + relation + " " + trim(o);
            if (!res.contains(line)){
                writeUtils.write(line);
                res.add(line);
            }
        }
        System.out.println("关系: " + relation + "; 数量: " + res.size());
        writeUtils.flush();
    }


    private static String trim(String uri){
        uri = uri.trim();
        uri = StringUtils.removeStart(uri, "http://sws.geonames.org/");
        uri = StringUtils.removeStart(uri, "http://www.geonames.org/ontology#");
        uri = StringUtils.removeEnd(uri, "/");
        return uri.trim();
    }

    private static String geneRelationUri(String uri){
        return "http://www.geonames.org/ontology#" + uri.trim();
    }


}
