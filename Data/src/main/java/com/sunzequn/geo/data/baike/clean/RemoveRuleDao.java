package com.sunzequn.geo.data.baike.clean;

import com.sunzequn.geo.data.baike.bdbk.UrlType;
import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/13.
 */
public class RemoveRuleDao extends BaseDao {

    private static final String DATABASE = "baidubaike";
    private static final String TABLE = "rules_remove";
    private Connection connection;

    public RemoveRuleDao() {
        this.connection = getConnection(DATABASE);
    }

    public int save(RemoveRule removeRule) {
        String sql = "insert into " + TABLE + " (rule, type, priority, comment) values (?, ?, ?, ?)";
        Object[] parmas = {removeRule.getRule(), removeRule.getType(), removeRule.getPriority(), removeRule.getComment()};
        return execute(connection, sql, parmas);
    }

    public List<RemoveRule> getByPriority(int priority) {
        String sql = "select * from " + TABLE + " where priority = ?";
        Object[] params = {priority};
        return query(connection, sql, params, RemoveRule.class);
    }

    public static void main(String[] args) {
        RemoveRuleDao dao = new RemoveRuleDao();
        System.out.println(dao.getByPriority(1));
    }
}
