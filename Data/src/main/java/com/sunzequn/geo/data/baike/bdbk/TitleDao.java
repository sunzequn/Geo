package com.sunzequn.geo.data.baike.bdbk;

import com.sun.scenario.animation.shared.TimerReceiver;
import com.sunzequn.geo.data.dao.ServerDao;
import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.TimeUtils;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/10.
 */
public class TitleDao extends ServerDao {

    private static final String DATABASE = "geokb_bdbk";
    private static final String TABLE = "title";
    private Connection connection;

    public TitleDao() {
        connection = getConnection(DATABASE);
    }

    public Title getByUrl(String url) {
        String sql = "select * from " + TABLE + " where url = ?";
        Object[] params = {url};
        List<Title> titles = query(connection, sql, params, Title.class);
        if (ListUtils.isEmpty(titles)) {
            return null;
        }
        return titles.get(0);
    }

    public List<Title> getTitleEnds(String ends) {
        String sql = "select * from " + TABLE + " where title like '%" + ends + "'";
        return query(connection, sql, null, Title.class);
    }

    public boolean ifTitleEnds(String url, String ends) {
        String sql = "select * from " + TABLE + " where url = ? and title like '%" + ends + "'";
        Object[] params = {url};
        List<Title> titles = query(connection, sql, params, Title.class);
        if (ListUtils.isEmpty(titles)) {
            return false;
        }
        return true;
    }

    public List<Title> getTitleContains(String ends) {
        String sql = "select * from " + TABLE + " where title like '%" + ends + "%'";
        return query(connection, sql, null, Title.class);
    }

    public boolean ifTitleContains(String url, String ends) {
        String sql = "select * from " + TABLE + " where url = ? and title like '%" + ends + "%'";
        Object[] params = {url};
        List<Title> titles = query(connection, sql, params, Title.class);
        if (ListUtils.isEmpty(titles)) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        TitleDao dao = new TitleDao();
//        System.out.println(dao.getTitleEnds("省"));
        System.out.println(dao.ifTitleContains("/subview/1000117/16114997.htm", "巧配"));
        System.out.println(dao.getTitleContains("巧配"));
    }
}
