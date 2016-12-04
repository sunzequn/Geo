package com.sunzequn.geo.data.geonamesplus;

import java.util.List;

public class ExtZhDao extends BaseDao{

    private static final String TABLE = "ext_zh";
    
    public ExtZh getById(String id) {
        String sql = "select * from " + TABLE + " where id = ?";
        Object[] params = {id};
        List<ExtZh> extZhs = query(connection, sql, params, ExtZh.class);
        if (extZhs == null)
        	return null;
        return extZhs.get(0);
    }
}
