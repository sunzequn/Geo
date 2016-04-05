package com.sunzequn.geo.data.baike.dao;

import com.sunzequn.geo.data.baike.bean.BasicInfo;
import com.sunzequn.geo.data.dao.ServerDao;
import org.apache.jena.rdfxml.xmloutput.impl.Basic;

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

    public static void main(String[] args) {
        BasicInfoDao dao = new BasicInfoDao();
        System.out.println(dao.getValueByKey("所属专辑").size());
    }
}
