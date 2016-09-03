package com.sunzequn.geo.data.baike.bdbk;

import com.sunzequn.geo.data.dao.ServerDao;
import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.TimeUtils;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/5.
 */
public class BasicInfoDao extends ServerDao {

    private static final String DATABASE = "geokb_bdbk";
    private static final String TABLE = "basic_info";
    private Connection connection;

    public BasicInfoDao() {
        connection = getConnection(DATABASE);
    }

    public List<BasicInfo> getValueByKey(String key) {
        //这个单引号好讨厌啊！ '''''''   ``````
        String sql = "select * from " + TABLE + " where `key` = ?";
        Object[] params = {key};
        return query(connection, sql, params, BasicInfo.class);
    }

    public List<BasicInfo> getKeyValue(String key, String value) {
        String sql = "select * from " + TABLE + " where `key` like '%" + key + "%'" + " and value like '%" + value + "%'";
        System.out.println(sql);
//        Object[] params = {key};
        return query(connection, sql, null, BasicInfo.class);
    }

    public boolean ifKeyValue(String url, String key, String value) {
        String sql = "select * from " + TABLE + " where url = ? and `key` = ? and value = ?";
        Object[] params = {url, key, value};
        List<BasicInfo> basicInfos = query(connection, sql, params, BasicInfo.class);
        if (ListUtils.isEmpty(basicInfos)) {
            return false;
        }
        return true;
    }

    public List<BasicInfo> getByUrl(String url) {
        String sql = "select * from " + TABLE + " where url = ?";
        Object[] params = {url};
        return query(connection, sql, params, BasicInfo.class);
    }

    public List<BasicInfo> getByPropKey(String key) {
        String sql = "select * from " + TABLE + " where `key` like '%" + key + "%'";
        System.out.println(sql);
        return query(connection, sql, null, BasicInfo.class);
    }


    public static void main(String[] args) {
        BasicInfoDao dao = new BasicInfoDao();
        System.out.println(dao.getKeyValue("行政区类别", "自治州"));
    }
}
