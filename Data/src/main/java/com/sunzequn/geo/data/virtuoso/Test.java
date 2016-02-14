package com.sunzequn.geo.data.virtuoso;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

/**
 * Created by Sloriac on 16/2/14.
 */
public class Test {

    public static void main(String[] args) {


        VirtGraphLoader virtGraphLoader = VirtGraphLoader.getInstance();
        VirtGraph set = virtGraphLoader.getVirtGraph();

        Query sparql = QueryFactory.create("SELECT * WHERE { GRAPH ?graph { ?s ?p ?o } } limit 1000");
        VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, set);

        ResultSet results = vqe.execSelect();
        while (results.hasNext()) {
            QuerySolution result = results.nextSolution();
            RDFNode s = result.get("s");
            RDFNode p = result.get("p");
            RDFNode o = result.get("o");
            System.out.println(s + " " + p + " " + o + " . ");
        }
    }
}
