package com.sunzequn.geo.data.alignment.sameas;

import com.sunzequn.geo.data.alignment.bean.*;
import com.sunzequn.geo.data.alignment.dao.ClassLinkDao;
import com.sunzequn.geo.data.alignment.dao.ClassRelDao;
import com.sunzequn.geo.data.geonames.bean.FeatureCodes;
import com.sunzequn.geo.data.geonames.dao.FeatureCodesDao;
import com.sunzequn.geo.data.utils.DoubleUtils;
import com.sunzequn.geo.data.utils.ListUtils;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Sloriac on 16/2/28.
 */
public class InstanceBasedHandler {

    private static final double THRESHOLD = 0.6;
    private String[] fclass = {"A", "H", "L", "P", "R", "T", "U", "V"};
    private ClassLinkDao classLinkDao = new ClassLinkDao();
    private ClassRelDao classRelDao = new ClassRelDao();
    private FeatureCodesDao featureCodesDao = new FeatureCodesDao();

    //dbpedia的非叶子节点的节点
    List<UpperClass> dbpediaUpperClasses = classRelDao.getDbpediaUpperClass();
    //dbpedia所有有直接关系的节点
    private List<DbpediaClass> dbpediaClasses = classLinkDao.getAllDbpediaClasses();
    //dbpedia所有叶子节点
    private List<DbpediaClass> dbpediaLeafClasses = handleDbpediaLeafs();

    //geonames所有有直接关系的节点
    private List<GeonamesClass> geonamesClasses = classLinkDao.getAllGeonamesClasses();
    //geonames的非叶子节点的节点
    List<UpperClass> geonamesUpperClasses = classRelDao.getGeonamesUpperClass();

    private List<FeatureCodes> featureCodes = featureCodesDao.getAllCodeWithoutClass();

    @Test
    public void getAllDbpeidaClassNum() {
        Set<String> classes = new HashSet<>();
        for (DbpediaClass dbpediaClass : dbpediaClasses) {
            classes.add(dbpediaClass.getUri1());
        }
        for (UpperClass upperClass : dbpediaUpperClasses) {
            classes.add(upperClass.getSuperuri());
        }
        System.out.println(classes.size());
    }

    private List<DbpediaClass> handleDbpediaLeafs() {
        List<DbpediaClass> dbpediaLeafs = new ArrayList<>();
        for (DbpediaClass dbpediaClass : dbpediaClasses) {
            boolean isLeaf = true;
            for (UpperClass upperClass : dbpediaUpperClasses) {
                if (upperClass.getSuperuri().equals(dbpediaClass.getUri1())) {
                    isLeaf = false;
                    break;
                }
            }
            if (isLeaf) {
                dbpediaLeafs.add(dbpediaClass);
            }
        }
        return dbpediaLeafs;
    }

    /**
     * dbpedia叶子类和geonames叶子类之间的关系
     */
    @Test
    public void handleSingleToSingle() {
        for (DbpediaClass dbpediaClass : dbpediaClasses) {
            int weight = dbpediaClass.getWeight();
            List<Relation> relatedGeonames = dbpediaClass.getRelatedGeonames();
            for (Relation geoname : relatedGeonames) {
                double dbp = (double) geoname.getWeight() / (double) weight;
                double geop = (double) geoname.getWeight() / (double) geoname.getAllWeight();
                if (dbp > THRESHOLD && geop > THRESHOLD) {
                    System.out.println(dbpediaClass.getUri1() + "/ " + DoubleUtils.m4(dbp) + " 等价于 " + geoname.getUri() + " (" + getNameByCode(geoname.getUri()) + ")" + "  / " + DoubleUtils.m4(geop));
                }
            }
        }
    }

    /**
     * dbpedia叶子类和geonames多个类之间的关系
     */
    @Test
    public void handleSingleDbpediaToSet() {
        for (DbpediaClass dbpediaClass : dbpediaLeafClasses) {
            handleDbpediaToSet(dbpediaClass, 0.5, 1.0);
        }
    }

    /**
     * 处理dbpedia的一个类到geonames集合
     *
     * @param dbpediaClass
     * @param lowNum
     * @param upNum
     */
    private void handleDbpediaToSet(DbpediaClass dbpediaClass, double lowNum, double upNum) {
        List<Relation> relatedGeonames = dbpediaClass.getRelatedGeonames();
        double allWeight = dbpediaClass.getWeight();
        double linkedGeonamesWeight = 0.0;
        for (Relation geoname : relatedGeonames) {
            //单个geonames的权重占自己总权重的比例
            double gp = (double) geoname.getWeight() / (double) geoname.getAllWeight();
            //单个geonames的权重占dbpedia总权重的比例
            double dp = (double) geoname.getWeight() / allWeight;
            if (gp > lowNum && gp < upNum) {
                linkedGeonamesWeight += geoname.getWeight();
                System.out.print(dbpediaClass.getUri1() + " / " + DoubleUtils.m4(dp) + "  包含 " + geoname.getUri() + " (" + getNameByCode(geoname.getUri()) + ")");
                System.out.println("    / " + DoubleUtils.m4(gp));
            }
        }
        //dbpedia到这个集合的link权重和
        double selfP = linkedGeonamesWeight / allWeight;
        if (selfP > 0) {
            System.out.println(dbpediaClass.getUri1() + " : " + DoubleUtils.m4(selfP));
            System.out.println();
        }

    }

    /**
     * 处理geonames的一个类到dbpedia集合
     */
    private void handleGeonamesToSet(GeonamesClass geonamesClass, double lowNum, double upNum) {
        List<Relation> relations = geonamesClass.getRelatedDbpedias();
        double allWeight = geonamesClass.getWeight();
        double linkedGeonamesWeight = 0.0;
        for (Relation relation : relations) {
            //单个dbpedia的权重占自己总权重的比例
            double gp = (double) relation.getWeight() / (double) relation.getAllWeight();
            //单个dbpedia的权重占geonames总权重的比例
            double dp = (double) relation.getWeight() / allWeight;
            if (gp > lowNum && gp < upNum) {
                linkedGeonamesWeight += relation.getWeight();
                System.out.print(geonamesClass.getUri2() + " / " + DoubleUtils.m4(dp) + "  包含 " + relation.getUri() + " (" + getNameByCode(relation.getUri()) + ")");
                System.out.println("    / " + DoubleUtils.m4(gp));
            }
        }
        //dbpedia到这个集合的link权重和
        double selfP = linkedGeonamesWeight / allWeight;
        if (selfP > 0) {
            System.out.println(geonamesClass.getUri2() + " : " + DoubleUtils.m4(selfP));
            System.out.println();
        }

    }


    /**
     * dbpedia非叶子类和geonames多个类
     */
    @Test
    public void handleDbpeidaUpperClassToSet() {
        for (UpperClass upperClass : dbpediaUpperClasses) {
            DbpediaClass dbpediaClass = constructDbpediaClass(upperClass);
            System.out.println(dbpediaClass);
            handleDbpediaToSet(dbpediaClass, 0.5, 1.0);
        }
    }

    /**
     * dbpedia非叶子类和geonames多个类
     */
    @Test
    public void handleGeonamesUpperClassToSet() {
        for (UpperClass upperClass : geonamesUpperClasses) {
            GeonamesClass geonamesClass = constructGeonamesClass(upperClass);
            System.out.println(geonamesClass);
            handleGeonamesToSet(geonamesClass, 0.5, 1.0);
        }
    }

    private GeonamesClass constructGeonamesClass(UpperClass upperClass) {
        GeonamesClass geonamesClass = new GeonamesClass();
        geonamesClass.setUri2(upperClass.getSuperuri());
        List<String> subs = classRelDao.getAllSubClasses(upperClass.getSuperuri());
        List<GeonamesClass> subClasses = getGeonamesClassByUri(subs);


        //计算权重,等于子节点权重和 加上自己直接Link的权重
        int weight = 0;
        for (GeonamesClass leafClass : subClasses) {
            weight += leafClass.getWeight();
        }
        geonamesClass.setWeight(weight);

        //计算关联
        Map<String, Relation> relationMap = new HashMap<>();
        for (GeonamesClass subClass : subClasses) {
            List<Relation> relations = subClass.getRelatedDbpedias();
            if (ListUtils.isEmpty(relations)) {
                continue;
            }
            for (Relation relation : relations) {
                Relation newRelation = relationMap.get(relation.getUri());
                if (newRelation == null) {
                    newRelation = new Relation(relation.getUri(), relation.getWeight(), relation.getAllWeight());
                    relationMap.put(relation.getUri(), newRelation);
                } else {
                    newRelation.setWeight(newRelation.getWeight() + relation.getWeight());
                }
            }
        }

        List<Relation> relations = new ArrayList<>();
        for (Map.Entry<String, Relation> entry : relationMap.entrySet()) {
            relations.add(entry.getValue());
        }
        Collections.sort(relations);

        geonamesClass.setRelatedDbpedias(relations);

        return geonamesClass;
    }

    /**
     * 对于非叶子节点,构造出他的全部link信息,包括子节点的和自己直接link的
     *
     * @param upperClass
     * @return
     */
    private DbpediaClass constructDbpediaClass(UpperClass upperClass) {
        DbpediaClass dbpediaClass = new DbpediaClass();
        //类的uri
        dbpediaClass.setUri1(upperClass.getSuperuri());

        List<String> subs = classRelDao.getAllSubClasses(upperClass.getSuperuri());
        //这个类的子类中有直接link的子类们
        List<DbpediaClass> subClasses = getDbpediaClassByUri(subs);
        //这个类的自己的直接link 类
        DbpediaClass directSelf = getDbpediaClassByUri(upperClass.getSuperuri());
        //为了后续计算方便,直接将自己的link类加入子类
        if (directSelf != null) {
            subClasses.add(directSelf);
        }

        //计算权重,等于子节点的直接权重和 加上自己直接Link的权重
        int weight = 0;
        for (DbpediaClass leafClass : subClasses) {
            weight += leafClass.getWeight();
        }
        dbpediaClass.setWeight(weight);

        //计算关联的geonames
        Map<String, Relation> relationMap = new HashMap<>();
        for (DbpediaClass subClass : subClasses) {
            List<Relation> relations = subClass.getRelatedGeonames();
            if (ListUtils.isEmpty(relations)) {
                continue;
            }
            for (Relation relation : relations) {
                Relation newRelation = relationMap.get(relation.getUri());
                if (newRelation == null) {
                    newRelation = new Relation(relation.getUri(), relation.getWeight(), relation.getAllWeight());
                    relationMap.put(relation.getUri(), newRelation);
                } else {
                    newRelation.setWeight(newRelation.getWeight() + relation.getWeight());
                }
            }
        }

        List<Relation> relatedGeonames = new ArrayList<>();
        for (Map.Entry<String, Relation> entry : relationMap.entrySet()) {
            relatedGeonames.add(entry.getValue());
        }
        Collections.sort(relatedGeonames);

        dbpediaClass.setRelatedGeonames(relatedGeonames);

        return dbpediaClass;
    }

    private List<DbpediaClass> getDbpediaClassByUri(List<String> uris) {
        List<DbpediaClass> newDbpediaClasses = new ArrayList<>();
        for (String uri : uris) {
            DbpediaClass dbpediaClass = getDbpediaClassByUri(uri);
            if (dbpediaClass != null) {
                newDbpediaClasses.add(dbpediaClass);
            }
        }
        return newDbpediaClasses;
    }

    private DbpediaClass getDbpediaClassByUri(String uri) {
        for (DbpediaClass dbpediaClass : this.dbpediaClasses) {
            if (dbpediaClass.getUri1().equals(uri)) {
                return dbpediaClass;
            }
        }
        return null;
    }

    private List<GeonamesClass> getGeonamesClassByUri(List<String> uris) {
        List<GeonamesClass> newGeonamesClasses = new ArrayList<>();
        for (String uri : uris) {
            GeonamesClass geonamesClass = getGeonamesClassByUri(uri);
            if (geonamesClass != null) {
                newGeonamesClasses.add(geonamesClass);
            }
        }
        return newGeonamesClasses;
    }

    private GeonamesClass getGeonamesClassByUri(String uri) {
        for (GeonamesClass geonamesClass : this.geonamesClasses) {
            if (geonamesClass.getUri2().equals(uri)) {
                return geonamesClass;
            }
        }
        return null;
    }

    /**
     * geonames叶子类和dbpedia多个类之间的关系
     */
    public void handleSingleGeonamesToSet() {
        for (GeonamesClass geonamesClass : geonamesClasses) {
            List<Relation> relatedDbpedias = geonamesClass.getRelatedDbpedias();
            for (Relation dbpedia : relatedDbpedias) {
                double p = (double) dbpedia.getWeight() / (double) dbpedia.getAllWeight();
                if (p > 0.6 && p < 1.0) {
                    System.out.print(geonamesClass.getUri2() + " 包含 " + dbpedia.getUri());
                    System.out.println(" / " + DoubleUtils.m4(p));
                }
            }
        }
    }

    /**
     * geonames的fclass和dbpedia的多个类
     */
    public void handleFclassToSet() {

    }

    private String getNameByCode(String code) {
        for (FeatureCodes featureCode : featureCodes) {
            if (featureCode.getCode().equals(code)) {
                return featureCode.getName();
            }
        }
        return null;
    }
}
