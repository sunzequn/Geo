package com.sunzequn.geo.data.virtuoso;

import com.sunzequn.geo.data.geonames.GeoNameUtil;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Sloriac on 16/2/14.
 */
public class QueryContains {

    private VirtGraphLoader virtGraphLoader = VirtGraphLoader.getInstance();
    private VirtGraph virtGraph = virtGraphLoader.getVirtGraph();

    /**
     * 根据id查询直接包含的地区.
     *
     * @param id 地点在GeoNames中的id
     * @return 该地点所包含地区的uri
     */
    public List<String> directContains(int id) {
        String uri = GeoNameUtil.getUri(id);
        Query sparql = QueryFactory.create("SELECT * WHERE { GRAPH ?graph { ?s <http://www.geonames.org/ontology#parentFeature> <" + uri + "> } } ");
        VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, virtGraph);
        ResultSet results = vqe.execSelect();
        Set<String> containUris = new HashSet<>();
        while (results.hasNext()) {
            QuerySolution result = results.nextSolution();
            RDFNode s = result.get("s");
            containUris.add(s.toString());
        }
        if (containUris.size() > 0) {
            return new ArrayList<>(containUris);
        }
        return null;
    }

    /**
     * 根据id查询直接以及间接(传递)包含的地区.
     *
     * @param id 地点在GeoNames中的id
     * @return 该地点直接和间接包含的地区的uri
     */
    public List<String> transContains(int id) {
        List<String> tempContainUris = directContains(id);
        List<String> containsUris = new ArrayList<>();
        if (tempContainUris == null) {
            return null;
        } else {
            for (String uri : tempContainUris) {
                List<String> transContainsUris = transContains(GeoNameUtil.parseId(uri));
                if (transContainsUris != null) {
                    containsUris.addAll(transContainsUris);
                }
            }
            containsUris.addAll(tempContainUris);
            return containsUris;
        }
    }

    public static void main(String[] args) {
        QueryContains queryContains = new QueryContains();
        System.out.println(queryContains.directContains(1806260).size());//江苏省
        System.out.println(queryContains.transContains(1806260).size());
    }
}
