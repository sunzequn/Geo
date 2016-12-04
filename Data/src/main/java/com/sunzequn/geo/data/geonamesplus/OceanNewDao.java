package com.sunzequn.geo.data.geonamesplus;

import java.util.List;

/**
 * @author jjyu
 * Created on 2016/11/19
 * 新的洋流实例库查询类
 */

public class OceanNewDao extends BaseDao{

	private static final String TABLE = "ext_ocean_new";
    
    public List<OceanNew> getAll(){
    	String sql = "select * from " + TABLE;
        return query(connection, sql, null, OceanNew.class);
    }
}
