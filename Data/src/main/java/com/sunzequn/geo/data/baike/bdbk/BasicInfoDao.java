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
        String sql = "select * from " + TABLE + " where `key` = ? and value = ?";
        Object[] params = {key, value};
        return query(connection, sql, params, BasicInfo.class);
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


    public static void main(String[] args) {
        BasicInfoDao dao = new BasicInfoDao();
        TimeUtils timeUtils = new TimeUtils();
        timeUtils.start();
        System.out.println(dao.ifKeyValue("/subview/1000004/13868523.htm", "中文名", "尘根"));
        timeUtils.end();
        System.out.println(timeUtils.duration());
    }
}
