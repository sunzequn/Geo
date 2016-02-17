package com.sunzequn.geo.data.climate.dao;

import com.sunzequn.geo.data.climate.bean.PageUrls;
import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Sloriac on 16/2/11.
 */
public class PageUrlsDao extends BaseDao {
    private static final String DATABASE = "geocities";
    private String table = "climate_seed_region_page_urls";
    private Connection connection;

    public PageUrlsDao() {
        connection = getConnection(DATABASE);
    }

    public int save(PageUrls pageUrls) {
        String sql = "insert into " + table + " values (?, ?)";
        Object[] params = {pageUrls.getUrl(), pageUrls.getIfvisited()};
        return execute(connection, sql, params);
    }

    public List<PageUrls> getAll() {
        String sql = "select * from " + table;
        return query(connection, sql, null, PageUrls.class);
    }

    public List<PageUrls> getUnvisited() {
        String sql = "select * from " + table + " where ifvisited <> 2";
        return query(connection, sql, null, PageUrls.class);
    }

    public int update(String url, int ifvisited) {
        String sql = "update " + table + " set ifvisited = ? where url = ?";
        Object[] params = {ifvisited, url};
        return execute(connection, sql, params);
    }

    public static void main(String[] args) {
        PageUrls pageUrls = new PageUrls("s/s", 0);
        PageUrlsDao pageUrlsDao = new PageUrlsDao();
        pageUrlsDao.save(pageUrls);
        System.out.println(pageUrlsDao.getAll());
        System.out.println(pageUrlsDao.getUnvisited());
        pageUrlsDao.update("s/s", 1);

    }
}
