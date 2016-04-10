package com.sunzequn.geo.data.baike.bdbk;

import com.sunzequn.geo.data.dao.ServerDao;
import com.sunzequn.geo.data.utils.ListUtils;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/10.
 */
public class SubTitleDao extends ServerDao {

    private static final String DATABASE = "geokb_bdbk";
    private static final String TABLE = "subtitle";
    private Connection connection;

    public SubTitleDao() {
        connection = getConnection(DATABASE);
    }

    public List<SubTitle> getByUrl(String url) {
        String sql = "select * from " + TABLE + " where url = ?";
        Object[] params = {url};
        return query(connection, sql, params, SubTitle.class);
    }

    public List<SubTitle> getSubtitleEnds(String ends) {
        String sql = "select * from " + TABLE + " where subtitle like '%" + ends + "）'";
        return query(connection, sql, null, SubTitle.class);
    }

    public boolean ifSubtitleEnds(String url, String ends) {
        String sql = "select * from " + TABLE + " where url = ? and subtitle like '%" + ends + "）'";
        Object[] params = {url};
        List<SubTitle> titles = query(connection, sql, params, SubTitle.class);
        if (ListUtils.isEmpty(titles)) {
            return false;
        }
        return true;
    }

    public List<SubTitle> getSubtitleContains(String ends) {
        String sql = "select * from " + TABLE + " where subtitle like '%" + ends + "%'";
        return query(connection, sql, null, SubTitle.class);
    }

    public boolean ifSubtitleContains(String url, String ends) {
        String sql = "select * from " + TABLE + " where url = ? and subtitle like '%" + ends + "%'";
        Object[] params = {url};
        List<SubTitle> titles = query(connection, sql, params, SubTitle.class);
        if (ListUtils.isEmpty(titles)) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        SubTitleDao dao = new SubTitleDao();
//        System.out.println(dao.getByUrl("/subview/2505873/10957279.htm"));
//        System.out.println(dao.getSubtitleEnds("红河学院人文学院副教授"));
//        System.out.println(dao.ifSubtitleContains("/subview/2505873/10957279.htm", "红河学院人文学院副教授"));
        System.out.println(dao.getSubtitleContains("学院人文学院"));
        System.out.println(dao.ifSubtitleContains("/subview/2505873/10957279.htm", "红河学院人文学院副教授"));
    }
}
