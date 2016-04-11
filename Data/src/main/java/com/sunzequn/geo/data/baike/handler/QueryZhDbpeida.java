package com.sunzequn.geo.data.baike.handler;

import com.sunzequn.geo.data.virtuoso.VirtGraphLoader;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sloriac on 16/3/16.
 */
public class QueryZhDbpeida {

    private VirtGraphLoader virtGraphLoader = VirtGraphLoader.getInstance();
    private VirtGraph virtGraph = virtGraphLoader.getDbpediaVirtGraph();
    private static final String DBPEDIA_ORG = "<http://dbpedia.org>";
    private static final String DBPEDIA_ZH = "<http://zh.dbpedia.org>";
    private static final String TYPE = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
    private static final String SAMEAS = "http://www.w3.org/2002/07/owl#sameAs";
    private static final String LABEL = "http://www.w3.org/2000/01/rdf-schema#label";
    private static final String THING = "http://www.w3.org/2002/07/owl#Thing";
    private static final String DBO = "http://dbpedia.org/ontology/";
    private static final String DBR_ZH = "http://zh.dbpedia.org/resource/";
    private static final String DBR = "http://dbpedia.org/resource/";

    /**
     * 根据localname查询中文DBpedia,
     *
     * @param localNameZh
     * @return
     */
    public String getEnSameAs(String localNameZh) {

        ResultSet resultSet = queryByLocalName(localNameZh);
        //根据localName查不到,就根据label查
        if (!resultSet.hasNext()) {
            String uri = queryUriByLabel(localNameZh);
            if (uri == null) {
                return null;
            }
            resultSet = queryByUri(uri);
        }
        List<String> sameas = new ArrayList<>();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.nextSolution();
            RDFNode p = solution.get("p");
            RDFNode o = solution.get("o");
            if (p.toString().equals(SAMEAS) && o.toString().contains(DBR)) {
                sameas.add(o.toString());
            }
        }
        if (sameas.size() == 1) {
            return sameas.get(0);
        } else if (sameas.size() > 1) {
            System.out.println("根据中文获取same as 出错  :  " + sameas);
        }
        return null;
    }

    private ResultSet queryByLocalName(String localNameZh) {
        String uri = DBR_ZH + localNameZh;
        String sparql = "SELECT * FROM " + DBPEDIA_ZH + " WHERE { <" + uri + "> ?p ?o }";
        return query(sparql);
    }

    private ResultSet queryByUri(String uri) {
        String sparql = "SELECT * FROM " + DBPEDIA_ZH + " WHERE { <" + uri + "> ?p ?o }";
        return query(sparql);
    }

    private String queryUriByLabel(String label) {
        String sparql = "SELECT * FROM " + DBPEDIA_ZH + " WHERE {?s <" + LABEL + "> '" + label + "'@zh }";
        System.out.println(sparql);
        ResultSet resultSet = query(sparql);
        List<String> res = new ArrayList<>();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.nextSolution();
            String s = solution.get("s").toString();
            if (s.startsWith(DBR_ZH) && !StringUtils.removeStart(s, DBR_ZH).contains("Category:")) {
                res.add(s);
            }
        }
        if (res.size() == 1) {
            return res.get(0);
        } else if (res.size() > 1) {
            System.out.println("-- 根究Label查询结果有问题 -- " + res);
        }
        return null;
    }

    private ResultSet query(String sparql) {
        VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, virtGraph);
        return vqe.execSelect();
    }

    public static void main(String[] args) {
        QueryZhDbpeida queryZhDbpeida = new QueryZhDbpeida();
        System.out.println(queryZhDbpeida.getEnSameAs("长江"));
        System.out.println(queryZhDbpeida.queryUriByLabel("长江"));
    }
}
