package com.sunzequn.geo.data.alignment.dao;

import com.sunzequn.geo.data.alignment.bean.ClassRel;
import com.sunzequn.geo.data.dao.BaseDao;
import com.sunzequn.geo.data.geonames.bean.Geoname;
import com.sunzequn.geo.data.utils.ListUtils;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Sloriac on 16/2/27.
 */
public class ClassRelDao extends BaseDao {

    private static final String DATABASE = "alignment";
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

    public List<ClassRel> getSuperClasses(String uri) {
        String sql = "select * from " + TABLE + " where uri = ?";
        Object[] params = {uri};
        return query(connection, sql, params, ClassRel.class);
    }

    public List<ClassRel> getSubClasses(String superuri) {
        String sql = "select * from " + TABLE + " where superuri = ?";
        Object[] params = {superuri};
        return query(connection, sql, params, ClassRel.class);
    }

    public static void main(String[] args) {
        ClassRelDao dao = new ClassRelDao();
        dao.save(new ClassRel("ddd", "sss"));
    }

}
