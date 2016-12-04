package com.sunzequn.geo.data.geonamesplus.handler;

import com.sunzequn.geo.data.geonamesplus.GeoNameUtils;
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
 * Created by sloriac on 16-12-4.
 */
public class ZhHandler {

    private static VirtGraph virtGraph = VirtGraphLoader.getInstance().getGeonamesVirtGraph();
    private static WriteUtils writeUtils = new WriteUtils(GeoNamesConf.GEONAMES_ZH, false);


    public static void main(String[] args) throws Exception {
        id();
        writeUtils.close();
    }

    public static void id() throws Exception {
        Set<String> res = new HashSet<>();
        String sparql = "SELECT ?s ?label WHERE{ GRAPH ?graph { ?s <http://www.geonames.org/ontology#alternateName> ?label. BIND (lang(?label) AS ?language). FILTER regex(?language, \"zh\")}}";
//        System.out.println(sparql);
        VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, virtGraph);
        ResultSet results = vqe.execSelect();
        while (results.hasNext()) {
            QuerySolution result = results.nextSolution();
            String s = result.get("s").toString();
            s = GeoNameUtils.trim(s);
            String l = result.get("label").toString();
            l = StringUtils.removeEnd(l, "@zh");
            String line = s.trim() + GeoNamesConf.SPLIT + l.trim();
            if (!res.contains(line)){
                writeUtils.write(line);
                res.add(line);
            }
        }
        System.out.println("中文数量: " + res.size());
    }


}
