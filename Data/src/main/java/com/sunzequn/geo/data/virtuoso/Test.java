package com.sunzequn.geo.data.virtuoso;

import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import java.util.List;

/**
 * Created by Sloriac on 16/2/14.
 */
public class Test {


    private static final String RULE = "Data/src/main/resources/rules/contains.rule";

    public static void main(String[] args) {

        VirtGraphLoader virtGraphLoader = VirtGraphLoader.getInstance();
        VirtGraph set = virtGraphLoader.getGeonamesVirtGraph();
        String construct = "SELECT * WHERE { GRAPH ?graph { ?s <http://www.geonames.org/ontology#parentFeature> ?o } } limit 1000";
        VirtuosoQueryExecution vqe2 = VirtuosoQueryExecutionFactory.create(construct, set);
        Model m = vqe2.execConstruct();
        System.out.println(m.size());

//        Query sparql = QueryFactory.create("SELECT * WHERE { GRAPH ?graph { ?s <http://www.geonames.org/ontology#parentFeature> <http://sws.geonames.org/3017382/> } } limit 1000");
//        VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, set);

//        ResultSet results = vqe.execSelect();
//        while (results.hasNext()) {
//            QuerySolution result = results.nextSolution();
//            RDFNode s = result.get("s");
//            RDFNode p = result.get("p");
//            RDFNode o = result.get("o");
//            System.out.println(s + " " + p + " " + o + " . ");
//        }
//    }

//        Model virtModel = new VirtModel(set);
//        System.out.println(virtModel.size());
//
//
        List<Rule> rules = Rule.rulesFromURL(RULE);
        for (Rule rule : rules) {
            System.out.println(rule.toString());
        }

        Reasoner reasoner = new GenericRuleReasoner(rules);
        reasoner = reasoner.bindSchema(m);
        reasoner.setDerivationLogging(true);
        InfModel infModel = ModelFactory.createInfModel(reasoner, m);
//        infModel.commit();

        Resource c = m.createResource("http://sws.geonames.org/1814991/");//china
        Resource a = m.createResource("http://sws.geonames.org/10630003/");
        Property property = m.createProperty("http://www.geonames.org/ontology#parentFeature");

        StmtIterator stmtIter = infModel.listStatements(a, property, c);
        if (!stmtIter.hasNext()) {
            System.out.println("there is no relation");
        }
        while (stmtIter.hasNext()) {
            Statement s = stmtIter.nextStatement();
            System.out.println("Relation between " + c.getLocalName() + " and "
                    + a.getLocalName() + " is :");
            System.out.println(c.getLocalName() + " "
                    + s.getPredicate().getLocalName() + " " + a.getLocalName());
            System.out.println("\n-------------------\n");
        }
    }


    public static void Reasoner() {
        String rule = "";

    }
}
