package com.sunzequn.geo.data.geonames.bean;

import com.sunzequn.geo.data.dao.BaseDao;

/**
 * Created by sloriac on 16-2-2.
 */
public class IdDao extends BaseDao{

    private static final String TABLENAME = "id";

    public IdDao() {
        getConnection("geonames_file");
    }

    public int save(Id id) {
        String sql = "insert into " + TABLENAME + " values (?, ?)";
        Object[] params = {id.getId(), id.getIfvisited()};
        return execute(sql, params);
    }

    public static void main(String[] args) {
        IdDao idDao = new IdDao();
        idDao.save(new Id(1, 1));
    }
}
