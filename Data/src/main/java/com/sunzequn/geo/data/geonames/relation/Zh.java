package com.sunzequn.geo.data.geonames.relation;

import com.sunzequn.geo.data.utils.WriteUtils;
import com.sunzequn.geo.data.virtuoso.VirtGraphLoader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sloriac on 16-12-3.
 */
public class Zh {

    private static VirtGraph virtGraph = VirtGraphLoader.getInstance().getGeonamesVirtGraph();
    private static final String FILE = "/home/sloriac/code/Geo/Data/src/main/resources/geonames_zh_ids";

    public static void main(String[] args) throws IOException {
        WriteUtils writeUtils = new WriteUtils(FILE, false);
        Set<String> res = new HashSet<>();
        String sparql = "SELECT distinct* WHERE{ GRAPH ?graph { ?s <http://www.geonames.org/ontology#alternateName> ?label. BIND (lang(?label) AS ?language). FILTER regex(?language, \"zh\")}}";
//        System.out.println(sparql);
        VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, virtGraph);
        ResultSet results = vqe.execSelect();
        while (results.hasNext()) {
            QuerySolution result = results.nextSolution();
            String s = result.get("s").toString();
            if (!res.contains(s)){
                writeUtils.write(s);
                res.add(s);
            }
        }
        System.out.println("中文数量: " + res.size());
        writeUtils.close();
    }

}
