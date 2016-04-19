package com.sunzequn.geo.data.baike.yuce;

import com.sunzequn.geo.data.baike.bdbk.*;
import com.sunzequn.geo.data.baike.clean.CleanUtils;
import com.sunzequn.geo.data.utils.ListUtils;
import org.neo4j.cypher.internal.compiler.v2_2.functions.Str;
import org.neo4j.graphalgo.impl.util.IntegerAdder;
import org.neo4j.register.Register;

import java.util.*;

/**
 * Created by sunzequn on 2016/4/18.
 */
public class TypeKey {

    private static final double yuzhi = 0.5;

    private UrlTypeDao urlTypeDao = new UrlTypeDao();
    private BasicInfoDao basicInfoDao = new BasicInfoDao();
    private CatalogDao catalogDao = new CatalogDao();

    private String type;
    private int positiveCaseNum;
    private int negativeCaseNum;

    private int positiveKeyNum;
    private int negativeKeyNum;
    private int positiveCatalogNum;
    private int negativeCatalogNum;

    private Map<String, Integer> positiveKey = new HashMap<>();
    private Map<String, Integer> negativeKey = new HashMap<>();
    private Map<String, Double> keyYuceMap = new HashMap<>();

    private Map<String, Integer> positiveCatalog = new HashMap<>();
    private Map<String, Integer> negativeCatalog = new HashMap<>();
    private Map<String, Double> catalogYuceMap = new HashMap<>();


    public TypeKey(String type, int which) {
        this.type = type;
        if (which == 1) {
            initBasic();
        } else if (which == 2) {
            initCatalog();
        }
    }

    private void initCatalog() {

        List<UrlType> positiveCases = urlTypeDao.getByTypeConfidence(type, 1);
        List<UrlType> negativeCases = urlTypeDao.getByTypeConfidence(type, 0);
        if (ListUtils.isEmpty(positiveCases)) {
            positiveCaseNum = 0;
        } else {
            positiveCaseNum = positiveCases.size();
        }
        if (ListUtils.isEmpty(negativeCases)) {
            negativeCaseNum = 0;
        } else {
            negativeCaseNum = negativeCases.size();
        }
        addCatalog(positiveCases, positiveCatalog);
        addCatalog(negativeCases, negativeCatalog);
        positiveCatalogNum = positiveCatalog.size();
        negativeCatalogNum = negativeCatalog.size();
        sortInteger(positiveCatalog);
        sortInteger(negativeCatalog);

        System.out.println("-------------预测 catalog----------------");
        yuce(positiveCatalog, negativeCatalog, positiveCatalogNum, negativeCatalogNum);
    }

    private void initBasic() {

        List<UrlType> positiveCases = urlTypeDao.getByTypeConfidence(type, 1);
        List<UrlType> negativeCases = urlTypeDao.getByTypeConfidence(type, 0);
        if (ListUtils.isEmpty(positiveCases)) {
            positiveCaseNum = 0;
        } else {
            positiveCaseNum = positiveCases.size();
        }
        if (ListUtils.isEmpty(negativeCases)) {
            negativeCaseNum = 0;
        } else {
            negativeCaseNum = negativeCases.size();
        }

        addPropKey(positiveCases, positiveKey);
        addPropKey(negativeCases, negativeKey);
        positiveKeyNum = positiveKey.size();
        negativeKeyNum = negativeKey.size();
        sortInteger(positiveKey);
        sortInteger(negativeKey);

        System.out.println("-------------预测 basicinfo----------------");
        yuce(positiveKey, negativeKey, positiveKeyNum, negativeKeyNum);
    }

    private void yuce(Map<String, Integer> positive, Map<String, Integer> negative, int posNum, int negNum) {
        for (Map.Entry<String, Integer> entry : positive.entrySet()) {
            String key = entry.getKey();
            if (negative.containsKey(key)) {
                int posValue = positive.get(key);
                int negValue = negative.get(key);
                if (((double) posValue) / posNum > yuzhi || ((double) negValue) / negNum > yuzhi) {
                    System.out.println("=============== " + key);
                } else {
                    keyYuceMap.put(key, ((double) (positive.get(key)) / posNum - ((double) negative.get(key)) / negNum));
                }
            }
        }
        sortDouble(keyYuceMap);
    }


    private void sortInteger(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> mappingList = null;
        //通过ArrayList构造函数把map.entrySet()转换成list
        mappingList = new ArrayList<>(map.entrySet());
        //通过比较器实现比较排序
        Collections.sort(mappingList, (mapping1, mapping2) -> mapping2.getValue().compareTo(mapping1.getValue()));
        System.out.println(mappingList);
    }

    private void sortDouble(Map<String, Double> map) {
        List<Map.Entry<String, Double>> mappingList = null;
        //通过ArrayList构造函数把map.entrySet()转换成list
        mappingList = new ArrayList<>(map.entrySet());
        //通过比较器实现比较排序
        Collections.sort(mappingList, (mapping1, mapping2) -> mapping1.getValue().compareTo(mapping2.getValue()));
        System.out.println(mappingList);
        for (Map.Entry<String, Double> entry : mappingList) {
            System.out.println(entry.getKey());
        }
    }


    private void addPropKey(List<UrlType> urlTypes, Map<String, Integer> maps) {
        for (UrlType urlType : urlTypes) {
            List<BasicInfo> basicInfos = basicInfoDao.getByUrl(urlType.getUrl());
            if (!ListUtils.isEmpty(basicInfos)) {
                for (BasicInfo basicInfo : basicInfos) {
                    String key = CleanUtils.clean(basicInfo.getKey());
                    addKey(maps, key);
                }
            }
        }
    }

    private void addCatalog(List<UrlType> urlTypes, Map<String, Integer> maps) {
        for (UrlType urlType : urlTypes) {
            List<Catalog> catalogs = catalogDao.getByUrl(urlType.getUrl());
            if (!ListUtils.isEmpty(catalogs)) {
                for (Catalog catalog : catalogs) {
                    String key = CleanUtils.clean(catalog.getCatalog_item());
                    addKey(maps, key);
                }
            }
        }
    }

    private void addKey(Map<String, Integer> maps, String key) {
        if (maps.containsKey(key)) {
            int value = maps.get(key);
            maps.put(key, value + 1);
        } else {
            maps.put(key, 1);
        }
    }


    @Override
    public String toString() {
        return "TypeKey{" +
                "type='" + type + '\'' +
                ", positiveCaseNum=" + positiveCaseNum +
                ", negativeCaseNum=" + negativeCaseNum +
                ", positiveKeyNum=" + positiveKeyNum +
                ", negativeKeyNum=" + negativeKeyNum;
    }
}
