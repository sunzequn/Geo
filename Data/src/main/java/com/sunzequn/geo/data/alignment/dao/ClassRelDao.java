package com.sunzequn.geo.data.alignment.dao;

import com.sunzequn.geo.data.alignment.bean.ClassRel;
import com.sunzequn.geo.data.alignment.bean.UpperClass;
import com.sunzequn.geo.data.dao.BaseDao;
import com.sunzequn.geo.data.utils.ListUtils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloriac on 16/2/27.
 */
public class ClassRelDao extends BaseDao {

    private static final String DATABASE = DataBaseName.database;
    private static final String TABLE = "class_rel";
    private Connection connection;

    public ClassRelDao() {
        connection = getConnection(DATABASE);
    }

    public int save(ClassRel classRel) {
        if (getById(classRel.getUri(), classRel.getSuperuri()) == null) {
            String sql = "insert into " + TABLE + " values(?, ?)";
            Object[] params = {classRel.getUri(), classRel.getSuperuri()};
            return execute(connection, sql, params);
        }
        return -1;
    }

    public ClassRel getById(String uri, String superUri) {
        String sql = "select * from " + TABLE + " where uri = ? and superuri = ?";
        Object[] params = {uri, superUri};
        List<ClassRel> classRels = query(connection, sql, params, ClassRel.class);
        if (ListUtils.isEmpty(classRels))
            return null;
        return classRels.get(0);
    }

    public List<String> getSuperClasses(String uri) {
        String sql = "select * from " + TABLE + " where uri = ?";
        Object[] params = {uri};
        List<ClassRel> classRels = query(connection, sql, params, ClassRel.class);
        if (!ListUtils.isEmpty(classRels)) {
            List<String> superClasses = new ArrayList<>();
            for (ClassRel classRel : classRels) {
                superClasses.add(classRel.getSuperuri());
            }
            return superClasses;
        }
        return null;
    }

    /**
     * 得到直接的子节点
     *
     * @param superuri
     * @return
     */
    public List<String> getSubClasses(String superuri) {
        String sql = "select * from " + TABLE + " where superuri = ?";
        Object[] params = {superuri};
        List<ClassRel> classRels = query(connection, sql, params, ClassRel.class);
        if (!ListUtils.isEmpty(classRels)) {
            List<String> subClasses = new ArrayList<>();
            for (ClassRel classRel : classRels) {
                subClasses.add(classRel.getUri());
            }
            return subClasses;
        }
        return null;
    }

    /**
     * 得到所有子节点
     *
     * @param uri
     * @return
     */
    public List<String> getAllSubClasses(String uri) {
        List<String> subClasses = getSubClasses(uri);
        List<String> allSubClasses = new ArrayList<>();
        //子节点为空,说明该节点是叶子节点
        if (ListUtils.isEmpty(subClasses)) {
            return null;
        } else {
            allSubClasses.addAll(subClasses);
            for (String subClass : subClasses) {
                List<String> sub = getAllSubClasses(subClass);
                if (sub != null)
                    allSubClasses.addAll(sub);
            }
            return allSubClasses;
        }

    }

    public List<UpperClass> getUpperClass() {
        String sql = "select distinct superuri from " + TABLE;
        return query(connection, sql, null, UpperClass.class);
    }

    public List<UpperClass> getDbpediaUpperClass() {
        List<UpperClass> upperClasses = getUpperClass();
        List<UpperClass> dbpeidaUpperClasses = new ArrayList<>();
        for (UpperClass upperClass : upperClasses) {
            if (upperClass.getSuperuri().toCharArray().length > 1) {
                dbpeidaUpperClasses.add(upperClass);
            }
        }
        return dbpeidaUpperClasses;
    }

    public List<UpperClass> getGeonamesUpperClass() {
        List<UpperClass> upperClasses = getUpperClass();
        List<UpperClass> geonamesUpperClasses = new ArrayList<>();
        for (UpperClass upperClass : upperClasses) {
            if (upperClass.getSuperuri().toCharArray().length == 1) {
                geonamesUpperClasses.add(upperClass);
            }
        }
        return geonamesUpperClasses;
    }


    public static void main(String[] args) {
        ClassRelDao dao = new ClassRelDao();
//        dao.save(new ClassRel("ddd", "sss"));
//        ListUtils.print(dao.getUpperClass());
        ListUtils.print(dao.getGeonamesUpperClass());
    }

}
