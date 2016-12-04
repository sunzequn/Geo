package com.sunzequn.geo.data.geonamesplus.handler;

import com.sunzequn.geo.data.utils.WriteUtils;
import com.sunzequn.geo.data.virtuoso.VirtGraphLoader;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sloriac on 16-12-2.
 */
public class IdHandler {

    private static final String FILE = "/home/sloriac/code/Geo/Data/src/main/resources/geonames_ids";
    private static VirtGraph virtGraph = VirtGraphLoader.getInstance().getGeonamesVirtGraph();
    private static WriteUtils writeUtils = new WriteUtils(FILE, false);


    public static void main(String[] args) throws Exception {
        id();
        writeUtils.close();
    }

    public static void id() throws Exception {

        String sparql = "select distinct * where {GRAPH ?graph {?s  a <http://www.geonames.org/ontology#Feature>}}";
//        System.out.println(sparql);
        VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, virtGraph);
        ResultSet results = vqe.execSelect();
        Set<String> res = new HashSet<>();
        while (results.hasNext()) {
            QuerySolution result = results.nextSolution();
            String s = result.get("s").toString();
            s = trim(s);
            if (!res.contains(s)){
                writeUtils.write(s);
                res.add(s);
            }
        }
        System.out.println("数量: " + res.size());
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
