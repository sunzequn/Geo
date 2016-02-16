package com.sunzequn.geo.data.geonames.filebean;

import com.sunzequn.geo.data.dao.BaseDao;

import java.util.List;

/**
 * Created by sloriac on 16-2-4.
 */
public class NoIdDao extends BaseDao {

    private static final String TABLENAME = "no_id";

    public NoIdDao() {
        getConnection("geonames_file");
    }

    public int save(NoId noId) {
        String sql = "insert into " + TABLENAME + " values (?)";
        Object[] params = {noId.getId()};
        return execute(sql, params);
    }

    public List<NoId> getAll() {
        String sql = "select * from " + TABLENAME;
        return query(sql, null, NoId.class);
    }

    public static void main(String[] args) {
        NoIdDao noIdDao = new NoIdDao();
//        idDao.save(new Id(3));
        System.out.println(noIdDao.getAll());
    }
}
