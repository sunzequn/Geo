package com.sunzequn.geo.data.alignment.sameas;

import com.sunzequn.geo.data.alignment.bean.ClassLink;
import com.sunzequn.geo.data.alignment.bean.ClassRel;
import com.sunzequn.geo.data.alignment.bean.UnhandledSame;
import com.sunzequn.geo.data.alignment.dao.ClassLinkDao;
import com.sunzequn.geo.data.alignment.dao.ClassRelDao;
import com.sunzequn.geo.data.alignment.dao.UnhandleSameDao;
import com.sunzequn.geo.data.alignment.type.Clazz;
import com.sunzequn.geo.data.alignment.type.ClazzGraph;
import com.sunzequn.geo.data.geonames.GeoNameUtil;
import com.sunzequn.geo.data.geonames.bean.Geoname;
import com.sunzequn.geo.data.geonames.dao.GeonameDao;
import com.sunzequn.geo.data.jena.Rdf;
import com.sunzequn.geo.data.utils.TimeUtils;
import org.apache.jena.rdf.model.*;
import org.junit.Test;
import org.neo4j.cypher.internal.compiler.v2_2.functions.Str;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloriac on 16/2/27.
 */
public class SameModel {

//    private static final String GEONAMES_LINK = "Data/src/main/resources/data/sw/geonames_links.nt";
//    private static final String GEONAMES_LINK_EN = "Data/src/main/resources/data/sw/geonames_links_en.nt";

    private static final String NEW_LINK = "Data/src/main/resources/data/sw/geonames_new_links.nt";

    private EntityHandler entityHandler = new EntityHandler();
    private GeonameDao geonameDao = new GeonameDao();
    private ClassRelDao classRelDao = new ClassRelDao();
    private ClassLinkDao classLinkDao = new ClassLinkDao();
    private UnhandleSameDao unhandleSameDao = new UnhandleSameDao();

    public Model getModel(String file) {
        Rdf rdf = new Rdf();
        return rdf.getModel(file, "N-TRIPLE");
    }


    public ClazzGraph dbpediaGraphHandler(String uri) {
        return entityHandler.getGraph(uri);
    }

    /**
     * 实体直接类的关联
     *
     * @param directDbpediaClass
     * @param directGeoClass
     */
    public void saveLink(String directDbpediaClass, String directGeoClass) {
        directDbpediaClass = DbpediaUtils.dbo(directDbpediaClass);
        classLinkDao.save(new ClassLink(directDbpediaClass, directGeoClass, 1));
    }

    public void saveUnhandledSame(String dbpediaUri, String geoUri) {
        dbpediaUri = DbpediaUtils.dbr(dbpediaUri);
        geoUri = String.valueOf(GeoNameUtil.parseId(geoUri));
        unhandleSameDao.save(new UnhandledSame(dbpediaUri, geoUri));

    }

    public void saveGraph(ClazzGraph clazzGraph) {
        List<String> clazzs = clazzGraph.getSuperClassRels();

        for (int i = clazzs.size() - 1; i > 0; i--) {
            String clazz = DbpediaUtils.dbo(clazzs.get(i));
            String superClazz = DbpediaUtils.dbo(clazzs.get(i - 1));
            classRelDao.save(new ClassRel(clazz, superClazz));
        }
    }


    public ClazzGraph geonamesGraphHandler(String uri) {
        int id = GeoNameUtil.parseId(uri);
        Geoname geoname = geonameDao.getById(id);
        if (geoname == null) {
            return null;
        }
        List<String> geonameTypes = new ArrayList<>();
        geonameTypes.add(geoname.getFclass());
        geonameTypes.add(geoname.getFcode());
        return new ClazzGraph(geonameTypes);
    }

    public static void main(String[] args) {
        SameModel sameModel = new SameModel();

        Model model = sameModel.getModel(NEW_LINK);
        StmtIterator iter = model.listStatements();
        TimeUtils timeUtils = new TimeUtils();
        timeUtils.start();
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode object = stmt.getObject();
            //构造类的树状结构
            ClazzGraph dbpediaGraph = sameModel.dbpediaGraphHandler(subject.getURI());
            ClazzGraph geonamesGraph = sameModel.geonamesGraphHandler(object.toString());

            if (dbpediaGraph != null && geonamesGraph != null) {
                sameModel.saveGraph(dbpediaGraph);
                sameModel.saveGraph(geonamesGraph);
                //考虑类的Link关系
                List<String> dbpediaClass = dbpediaGraph.getSuperClassRels();
                String directDbpediaClass = dbpediaClass.get(dbpediaClass.size() - 1);
                List<String> geoClass = geonamesGraph.getSuperClassRels();
                String directGeoClass = geoClass.get(geoClass.size() - 1);
                sameModel.saveLink(directDbpediaClass, directGeoClass);
            } else {
                sameModel.saveUnhandledSame(subject.getURI(), object.toString());
            }
        }
        timeUtils.end();
        timeUtils.print();
    }
}