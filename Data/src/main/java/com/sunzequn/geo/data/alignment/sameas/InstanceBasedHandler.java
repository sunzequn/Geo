package com.sunzequn.geo.data.alignment.sameas;

import com.sunzequn.geo.data.alignment.bean.DbpediaClass;
import com.sunzequn.geo.data.alignment.bean.GeonamesClass;
import com.sunzequn.geo.data.alignment.bean.Relation;
import com.sunzequn.geo.data.alignment.dao.ClassLinkDao;

import java.util.List;

/**
 * Created by Sloriac on 16/2/28.
 */
public class InstanceBasedHandler {

    private static final double THRESHOLD = 0.6;
    private String[] fclass = {"A", "H", "L", "P", "R", "T", "U", "V"};
    private ClassLinkDao classLinkDao = new ClassLinkDao();
    private List<DbpediaClass> dbpediaClasses = classLinkDao.getAllDbpediaClasses();
    private List<GeonamesClass> geonamesClasses = classLinkDao.getAllGeonamesClasses();

    public static void main(String[] args) {
        InstanceBasedHandler instanceBasedHandler = new InstanceBasedHandler();
//        handler.handleSingleToSingle();
//        handler.handleSingleDbpediaToSet();
        instanceBasedHandler.handleSingleGeonamesToSet();
    }

    public void handleSingleToSingle() {
        for (DbpediaClass dbpediaClass : dbpediaClasses) {
            int weight = dbpediaClass.getWeight();
            List<Relation> relatedGeonames = dbpediaClass.getRelatedGeonames();
            for (Relation geoname : relatedGeonames) {
                if ((double) geoname.getWeight() / (double) weight > THRESHOLD && (double) geoname.getWeight() / (double) geoname.getAllWeight() > THRESHOLD) {
                    System.out.println(dbpediaClass.getUri1() + " 等价于 " + geoname.getUri());
                }
            }
        }
    }

    public void handleSingleDbpediaToSet() {
        for (DbpediaClass dbpediaClass : dbpediaClasses) {
            int weight = dbpediaClass.getWeight();
            List<Relation> relatedGeonames = dbpediaClass.getRelatedGeonames();
            for (Relation geoname : relatedGeonames) {
                double p = (double) geoname.getWeight() / (double) geoname.getAllWeight();
                if (p > 0.6 && p < 1.0) {
                    System.out.print(dbpediaClass.getUri1() + " 包含 " + geoname.getUri());
                    System.out.println(" / " + p);
                }
            }
        }
    }

    public void handleSingleGeonamesToSet() {
        for (GeonamesClass geonamesClass : geonamesClasses) {
            List<Relation> relatedDbpedias = geonamesClass.getRelatedDbpedias();
            for (Relation dbpedia : relatedDbpedias) {
                double p = (double) dbpedia.getWeight() / (double) dbpedia.getAllWeight();
                if (p > 0.6 && p < 1.0) {
                    System.out.print(geonamesClass.getUri2() + " 包含 " + dbpedia.getUri());
                    System.out.println(" / " + p);
                }
            }
        }
    }

    public void handleFclassToSet() {

    }

}
