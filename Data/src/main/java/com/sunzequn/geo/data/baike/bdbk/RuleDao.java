package com.sunzequn.geo.data.baike.bdbk;

import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/9.
 */
public class RuleDao extends BaseDao {

    private static final String DATABASE = "baidubaike";
    private String table;
    private Connection connection;

    public RuleDao(String table) {
        this.table = table;
        connection = getConnection(DATABASE);
    }


    public List<Rule> getAll() {
        String sql = "select * from " + table + " order by type";
        return query(connection, sql, null, Rule.class);
    }

    public List<Rule> getAll(int visit) {
        String sql = "select * from " + table + " where ifvisit = ?";
        Object[] params = {visit};
        return query(connection, sql, params, Rule.class);
    }

    public static void main(String[] args) {
        RuleDao ruleDao = new RuleDao("rules_all_extract");
        System.out.println(ruleDao.getAll().size());
    }

}
