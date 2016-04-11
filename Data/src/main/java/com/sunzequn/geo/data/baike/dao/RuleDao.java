package com.sunzequn.geo.data.baike.dao;

import com.sunzequn.geo.data.baike.bean.BaikePage;
import com.sunzequn.geo.data.baike.bean.Rule;
import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/9.
 */
public class RuleDao extends BaseDao {

    private static final String DATABASE = "baidubaike";
    private static String TABLE = "rules";
    private Connection connection;

    public RuleDao() {
        connection = getConnection(DATABASE);
    }

    public RuleDao(String table) {
        TABLE = table;
        connection = getConnection(DATABASE);
    }

    public List<Rule> getAll() {
        String sql = "select * from " + TABLE;
        return query(connection, sql, null, Rule.class);
    }

    public static void main(String[] args) {
        RuleDao ruleDao = new RuleDao();
        Rule rule = ruleDao.getAll().get(0);
        rule.initRules();

    }
}
