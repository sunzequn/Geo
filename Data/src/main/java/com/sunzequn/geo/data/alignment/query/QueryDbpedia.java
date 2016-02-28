package com.sunzequn.geo.data.alignment.query;

import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.virtuoso.VirtGraphLoader;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.vocabulary.DB;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Sloriac on 16/2/27.
 */
public class QueryDbpedia {

    private VirtGraphLoader virtGraphLoader = VirtGraphLoader.getInstance();
    private VirtGraph virtGraph = virtGraphLoader.getDbpediaVirtGraph();
    private static final String DBPEDIA_ORG = "<http://dbpedia.org>";
    private static final String TYPE = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>";
    private static final String SUPPER = "<http://www.w3.org/2000/01/rdf-schema#subClassOf>";
    private static final String EQUIVALENT = "<http://www.w3.org/2002/07/owl#equivalentClass>";
    private static final String THING = "http://www.w3.org/2002/07/owl#Thing";
    private static final String DBO = "http://dbpedia.org/ontology/";

    public List<String> queryType(String uri) {
        String sparql = "SELECT * WHERE { GRAPH ?graph { <" + uri + "> " + TYPE + " ?o } }";
        return typeFilter(queryO(sparql));
    }

    public List<String> querySuperClass(String uri) {
        String sparql = "SELECT * WHERE { GRAPH ?graph { <" + uri + "> " + SUPPER + " ?o } }";
        return typeFilter(queryO(sparql));
    }

    public List<String> queryEquivalentClass(String uri) {
        String sparql = "SELECT * WHERE { GRAPH ?graph { <" + uri + "> " + EQUIVALENT + " ?o } }";
        return typeFilter(queryO(sparql));
    }

    private List<String> queryO(String sparql) {
        ResultSet results = query(sparql);
        Set<String> res = new HashSet<>();
        while (results.hasNext()) {
            QuerySolution result = results.nextSolution();
            RDFNode o = result.get("o");
            res.add(o.toString());
        }
        if (res.size() > 0) {
            return new ArrayList<>(res);
        }
        return null;
    }

    private ResultSet query(String sparql) {
        VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, virtGraph);
        return vqe.execSelect();
    }

    private List<String> typeFilter(List<String> types) {

        String exclusion = "http://dbpedia.org/ontology/Article";
        if (!ListUtils.isEmpty(types)) {
            List<String> newTypes = new ArrayList<>();
            for (String type : types) {
                type = type.trim();
                if (type.equals(exclusion)) {
                    continue;
                }
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
