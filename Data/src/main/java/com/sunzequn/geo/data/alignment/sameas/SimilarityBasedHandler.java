package com.sunzequn.geo.data.alignment.sameas;

import com.sunzequn.geo.data.algorithm.similarity.JaroWinklerDis;
import com.sunzequn.geo.data.alignment.bean.DbpediaClass;
import com.sunzequn.geo.data.alignment.dao.ClassLinkDao;
import com.sunzequn.geo.data.geonames.bean.FeatureCodes;
import com.sunzequn.geo.data.geonames.dao.FeatureCodesDao;
import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.StringUtils;
import com.wcohen.ss.JaroWinkler;

import java.util.List;

/**
 * Created by Sloriac on 16/2/28.
 */
public class SimilarityBasedHandler {

    private static final double THRESHOLD = 0.9;
    private ClassLinkDao classLinkDao = new ClassLinkDao();
    private List<DbpediaClass> dbpediaClasses = classLinkDao.getAllDbpediaClasses();
    private FeatureCodesDao featureCodesDao = new FeatureCodesDao();
    private List<FeatureCodes> featureCodesList = featureCodesDao.getAll();
    private JaroWinkler jaroWinkler = new JaroWinkler();


    public static void main(String[] args) {
        SimilarityBasedHandler handler = new SimilarityBasedHandler();
        handler.jaroWinklerSimilarity();
    }

    public void jaroWinklerSimilarity() {
        for (FeatureCodes featureCodes : featureCodesList) {
            for (DbpediaClass dbpediaClass : dbpediaClasses) {
                String localName = StringUtils.removeStart(dbpediaClass.getUri1(), "dbo:");
                double dis = jaroWinkler.score(featureCodes.getName(), localName);
                if (dis > THRESHOLD) {
                    System.out.println(featureCodes.getCode() + "(" + featureCodes.getName() + ") 近似于 " + dbpediaClass.getUri1() + "  / " + dis);
                }
            }
        }
    }
}
