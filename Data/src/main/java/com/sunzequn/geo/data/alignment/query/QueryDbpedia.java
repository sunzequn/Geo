package com.sunzequn.geo.data.alignment.query;

import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.virtuoso.VirtGraphLoader;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloriac on 16/2/27.
 */
public class QueryDbpedia {

    private VirtGraphLoader virtGraphLoader = VirtGraphLoader.getInstance();
    private VirtGraph virtGraph = virtGraphLoader.getDbpediaVirtGraph();
    private static final String DBPEDIA_ORG = "<http://dbpedia.org>";
    private static final String TYPE = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>";
    private static final String EQUIVALENT = "<http://www.w3.org/2002/07/owl#equivalentClass>";
    private static final String THING = "http://www.w3.org/2002/07/owl#Thing";
    private static final String DBO = "http://dbpedia.org/ontology/";

    public List<String> queryType(String uri) {
        String sparql = "SELECT * FROM " + DBPEDIA_ORG + " WHERE { <" + uri + "> " + TYPE + " ?o }";
        return typeFilter(queryO(sparql));
    }

    public List<String> queryEquivalentClass(String uri) {
        String sparql = "SELECT * WHERE { GRAPH ?graph { <" + uri + "> " + EQUIVALENT + " ?o } }";
        return typeFilter(queryO(sparql));
    }

    private List<String> queryO(String sparql) {
        ResultSet results = query(sparql);
        List<String> res = new ArrayList<>();
        while (results.hasNext()) {
            QuerySolution result = results.nextSolution();
            RDFNode o = result.get("o");
            res.add(o.toString());
        }
        if (res.size() > 0) {
            return res;

        }
        return null;
    }

    private ResultSet query(String sparql) {
        System.out.println(sparql);
        VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, virtGraph);
        return vqe.execSelect();
    }

    private List<String> typeFilter(List<String> types) {
        if (!ListUtils.isEmpty(types)) {
            List<String> newTypes = new ArrayList<>();
            for (String type : types) {
                type = type.trim();
                if (type.equals(THING) || type.startsWith(DBO)) {
                    newTypes.add(type);
                }
            }
            if (newTypes.size() > 0) {
                return newTypes;
            }
        }
        return null;
    }


}
