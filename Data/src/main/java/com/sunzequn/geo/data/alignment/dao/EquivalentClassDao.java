package com.sunzequn.geo.data.alignment.dao;

import com.sunzequn.geo.data.alignment.bean.ClassRel;
import com.sunzequn.geo.data.alignment.bean.EquivalentClass;
import com.sunzequn.geo.data.dao.BaseDao;
import com.sunzequn.geo.data.utils.ListUtils;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Sloriac on 16/2/27.
 */
public class EquivalentClassDao extends BaseDao {

    private static final String DATABASE = "alignment";
    private static final String TABLE = "equivalent_class";
    private Connection connection;

    public EquivalentClassDao() {
        connection = getConnection(DATABASE);
    }

    public int save(EquivalentClass equivalentClass) {
        if (getById(equivalentClass.getUri1(), equivalentClass.getUri2()) == null) {
            String sql = "insert into " + TABLE + " values(?, ?)";
            Object[] params = {equivalentClass.getUri1(), equivalentClass.getUri2()};
            return execute(connection, sql, params);
        }
        return -1;
    }

    public EquivalentClass getById(String uri1, String uri2) {
        String sql = "select * from " + TABLE + " where uri1 = ? and uri2 = ?";
        Object[] params = {uri1, uri2};
        List<EquivalentClass> equivalentClasses = query(connection, sql, params, EquivalentClass.class);
        if (ListUtils.isEmpty(equivalentClasses))
            return null;
        return equivalentClasses.get(0);
    }

    public static void main(String[] args) {
        EquivalentClassDao dao = new EquivalentClassDao();
        dao.save(new EquivalentClass("qqq", "sss"));
    }
}
