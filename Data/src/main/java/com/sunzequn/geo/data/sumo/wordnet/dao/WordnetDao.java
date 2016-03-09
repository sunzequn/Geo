package com.sunzequn.geo.data.sumo.wordnet.dao;

import com.sunzequn.geo.data.dao.BaseDao;
import com.sunzequn.geo.data.sumo.wordnet.bean.WnZh;
import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.StringUtils;

import java.sql.Connection;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sloriac on 16/3/9.
 */
public class WordnetDao extends BaseDao {

    private static final String DATABASE = "wordnet";
    private Connection connection;

    public WordnetDao() {
        this.connection = getConnection(DATABASE);
    }

    public List<WnZh> getWordnetIdByZh(String zh) {
        if (StringUtils.isNullOrEmpty(zh)) {
            return null;
        }
        String sql = "select * from wn_chinese where chinese like '%" + zh + "%'";
        List<WnZh> wnZhs = query(connection, sql, null, WnZh.class);
        if (ListUtils.isEmpty(wnZhs)) {
            return null;
        } else {
            for (WnZh wnZh : wnZhs) {
                wnZh.setZhKey(zh);
            }
            Collections.sort(wnZhs);
            return wnZhs;
        }
    }

    public static void main(String[] args) {
        WordnetDao wordnetDao = new WordnetDao();
        System.out.println(wordnetDao.getWordnetIdByZh("河"));
    }
}
