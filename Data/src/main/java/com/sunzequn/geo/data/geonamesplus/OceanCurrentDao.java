package com.sunzequn.geo.data.geonamesplus;

import java.util.List;

public class OceanCurrentDao extends BaseDao {
	
    private static final String TABLE = "ext_ocean_current";
    
    public List<OceanCurrent> getAll(){
    	String sql = "select * from " + TABLE;
        return query(connection, sql, null, OceanCurrent.class);
    }

}
