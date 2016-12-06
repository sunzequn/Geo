package com.sunzequn.geo.data.geonamesplus.handler;

import com.sunzequn.geo.data.geonamesplus.GeoNameUtils;
import com.sunzequn.geo.data.utils.ReadUtils;
import com.sunzequn.geo.data.utils.WriteUtils;
import com.sunzequn.geo.data.virtuoso.VirtGraphLoader;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sloriac on 16-12-2.
 */
public class IdHandler {

    private static final String ID_NAME_FILE = "/home/sloriac/code/Geo/Data/src/main/resources/geonames_id_names_mysql.txt";
    private static VirtGraph virtGraph = VirtGraphLoader.getInstance().getGeonamesVirtGraph();


    public static void main(String[] args) throws Exception {
//        id();
        mysqlIdMappingvirtuosoId();
    }

    public static void id() throws Exception {
        WriteUtils writeUtils = new WriteUtils(GeoNamesConf.GEONAMES_ID, false);
        String sparql = "select distinct * where {GRAPH ?graph {?s  a <http://www.geonames.org/ontology#Feature>}}";
//        System.out.println(sparql);
        VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql, virtGraph);
        ResultSet results = vqe.execSelect();
        Set<String> res = new HashSet<>();
        while (results.hasNext()) {
            QuerySolution result = results.nextSolution();
            String s = result.get("s").toString();
            s = GeoNameUtils.trim(s);
            if (!res.contains(s)){
                writeUtils.write(s);
                res.add(s);
            }
        }
        System.out.println("数量: " + res.size());
        writeUtils.close();
    }

    private static void mysqlIdMappingvirtuosoId(){
        ReadUtils readUtils1 = new ReadUtils(GeoNamesConf.GEONAMES_ID);
        ReadUtils readUtils2 = new ReadUtils(ID_NAME_FILE);
        WriteUtils writeUtils = new WriteUtils(GeoNamesConf.GEONAMES_NAME_FILE, false);
        List<String> ids = readUtils1.readByLine();
        Set<String> idset = new HashSet<>();
        for (String id : ids) {
            idset.add(id.trim());
        }
        List<String> lines = readUtils2.readByLine();
        for (String line : lines) {
            if (line.trim().endsWith(";")){
                System.out.println("没有名字 " + line);
                continue;
            }
            String[] params = line.split(";");
            if (params.length == 2 && idset.contains(params[0])){
                writeUtils.write(params[0] + GeoNamesConf.SPLIT + params[1]);
            } else {
                System.out.println("不符合2 " + line);
            }
        }
        writeUtils.close();
    }

}
