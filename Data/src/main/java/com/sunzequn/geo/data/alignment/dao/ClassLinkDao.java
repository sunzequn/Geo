package com.sunzequn.geo.data.alignment.dao;

import com.sunzequn.geo.data.alignment.bean.*;
import com.sunzequn.geo.data.dao.BaseDao;
import com.sunzequn.geo.data.utils.ListUtils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sloriac on 16/2/27.
 */
public class ClassLinkDao extends BaseDao {

    private static final String DATABASE = "alignment";
    private static final String TABLE = "class_link";
    private Connection connection;

    public ClassLinkDao() {
        connection = getConnection(DATABASE);
    }

    public int save(ClassLink classLink) {
        if (getById(classLink.getUri1(), classLink.getUri2()) == null) {
            String sql = "insert into " + TABLE + " values(?, ?, ?)";
            Object[] params = {classLink.getUri1(), classLink.getUri2(), classLink.getWeight()};
            return execute(connection, sql, params);
        } else {
            String sql = "update " + TABLE + " set weight = weight + 1 where uri1 = ? and uri2 = ?";
            Object[] params = {classLink.getUri1(), classLink.getUri2()};
            return execute(connection, sql, params);
        }
    }

    public ClassLink getById(String uri1, String uri2) {
        String sql = "select * from " + TABLE + " where uri1 = ? and uri2 = ?";
        Object[] params = {uri1, uri2};
        List<ClassLink> classLinks = query(connection, sql, params, ClassLink.class);
        if (ListUtils.isEmpty(classLinks))
            return null;
        return classLinks.get(0);
    }

    public List<DbpediaClass> getAllDbpediaClasses() {
        String sql = "select distinct uri1 from " + TABLE;
        List<DbpediaClass> classes = query(connection, sql, null, DbpediaClass.class);
        if (ListUtils.isEmpty(classes)) {
            return null;
        }
        for (DbpediaClass dbpediaClass : classes) {
            List<Relation> relatedGeonames = new ArrayList<>();
            List<ClassLink> clazzs = getByUri(dbpediaClass.getUri1(), 1);
            int weight = 0;
            if (!ListUtils.isEmpty(clazzs)) {
                for (ClassLink clazz : clazzs) {
                    weight += clazz.getWeight();
                    int allweight = getWeightCount(clazz.getUri2(), 2);
                    relatedGeonames.add(new Relation(clazz.getUri2(), clazz.getWeight(), allweight));
                }
            }
            dbpediaClass.setWeight(weight);
            //排序
            Collections.sort(relatedGeonames);
            dbpediaClass.setRelatedGeonames(relatedGeonames);
        }
        //排序
        Collections.sort(classes);
        return classes;
    }

    public List<GeonamesClass> getAllGeonamesClasses() {
        String sql = "select distinct uri2 from " + TABLE;
        List<GeonamesClass> classes = query(connection, sql, null, GeonamesClass.class);
        if (ListUtils.isEmpty(classes)) {
            return null;
        }
        for (GeonamesClass geonamesClass : classes) {
            List<Relation> relatedDbpedias = new ArrayList<>();
            List<ClassLink> clazzs = getByUri(geonamesClass.getUri2(), 2);
            int weight = 0;
            if (!ListUtils.isEmpty(clazzs)) {
                for (ClassLink clazz : clazzs) {
                    weight += clazz.getWeight();
                    int allweight = getWeightCount(clazz.getUri1(), 1);
                    relatedDbpedias.add(new Relation(clazz.getUri1(), clazz.getWeight(), allweight));
                }
            }
            geonamesClass.setWeight(weight);
            //排序
            Collections.sort(relatedDbpedias);
            geonamesClass.setRelatedDbpedias(relatedDbpedias);
        }
        Collections.sort(classes);
        return classes;
    }

    /**
     * 查询某个uri的全部边的权重
     *
     * @param uri
     * @param i   为1表示按照uri1来查询
     * @return
     */
    private int getWeightCount(String uri, int i) {
        List<ClassLink> classLinks = getByUri(uri, i);
        if (ListUtils.isEmpty(classLinks)) {
            return -1;
        }
        int weight = 0;
        for (ClassLink classLink : classLinks) {
            weight += classLink.getWeight();
        }
        return weight;
    }

    /**
     * 按照uri来查询
     *
     * @param uri
     * @param i   为1表示按照uri1来查询
     * @return
     */
    public List<ClassLink> getByUri(String uri, int i) {
        String sql;
        if (i == 1) {
            sql = "select * from " + TABLE + " where uri1 = ?";
        } else {
            sql = "select * from " + TABLE + " where uri2 = ?";
        }
        Object[] params = {uri};
        return query(connection, sql, params, ClassLink.class);
    }

    public static void main(String[] args) {
        ClassLinkDao dao = new ClassLinkDao();
        dao.save(new ClassLink("qqq", "sss", 1));
    }
}
