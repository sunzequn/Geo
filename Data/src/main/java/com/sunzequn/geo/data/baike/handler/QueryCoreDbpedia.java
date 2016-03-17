package com.sunzequn.geo.data.baike.handler;

import com.sunzequn.geo.data.virtuoso.VirtGraphLoader;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloriac on 16/3/16.
 */
public class QueryCoreDbpedia {

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
     * 查询Uri的type,没有经过过滤处理,返回是核心库里面定义的全部type.
     *
     * @param uri
     * @return
     */
    public List<String> queryTypes(String uri) {
        String sparql = "SELECT * FROM " + DBPEDIA_ORG + " WHERE { <" + uri + "> <" + TYPE + "> ?o }";
        ResultSet resultSet = query(sparql);
        List<String> types = new ArrayList<>();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.nextSolution();
            String o = solution.get("o").toString();
            types.add(o);
        }
        if (types.size() > 0) {
            return types;
        }
        return null;
    }

    public String queryUriByLabel(String label) {
        String sparql = "SELECT * FROM " + DBPEDIA_ORG + " WHERE {?s <" + LABEL + "> '" + label + "'@zh }";
        ResultSet resultSet = query(sparql);
        List<String> res = new ArrayList<>();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.nextSolution();
            String s = solution.get("s").toString();
            res.add(s);
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
        QueryCoreDbpedia queryCoreDbpedia = new QueryCoreDbpedia();
        System.out.println(queryCoreDbpedia.queryUriByLabel("毛泽东"));
    }
}
