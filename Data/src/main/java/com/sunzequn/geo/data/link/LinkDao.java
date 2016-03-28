package com.sunzequn.geo.data.link;

import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sloriac on 16-2-17.
 */
public class LinkDao extends BaseDao {

    private static final String DATABASE = "geonames_climate_link";
    private String table;
    private Connection connection;

    public LinkDao(String table) {
        this.table = table;
        connection = getConnection(DATABASE);
    }

    public int save(LinkBean linkBean) {
        String sql = "insert into " + table + " values (?, ?, ?)";
        Object[] params = {linkBean.getGeonameid(), linkBean.getClimateid(), linkBean.getConfidence()};
        return execute(connection, sql, params);
    }

    public List<LinkBean> getAll() {
        String sql = "select * from " + table;
        return query(connection, sql, null, LinkBean.class);
    }

    public List<LinkBean> getByGeonameid(int geonameid) {
        String sql = "select * from " + table + " where geonameid = ?";
        Object[] params = {geonameid};
        return query(connection, sql, params, LinkBean.class);
    }

    public List<LinkBean> getByClimateid(int climateid) {
        String sql = "select *  from " + table + " where climateid = ?";
        Object[] params = {climateid};
        return query(connection, sql, params, LinkBean.class);
    }

    public List<LinkBean> getAbove1() {
        String sql = "select * from " + table + " where confidence > 1.0";
        return query(connection, sql, null, LinkBean.class);
    }

    public int deleteLine(int geonameid, int climateid) {
        String sql = "delete from " + table + " where geonameid = ? and climateid = ?";
        Object[] params = {geonameid, climateid};
        return execute(connection, sql, params);
    }

    public static void main(String[] args) {
        LinkDao linkDao = new LinkDao("country_link");
        linkDao.save(new LinkBean(1, 1, 1));
    }
}
