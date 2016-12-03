package com.sunzequn.geo.data.geonamesplus;

import java.util.List;

public class OceanCurrentDao extends GnextBaseDao {
	
    private static final String TABLE = "ext_ocean_current";
    
    public List<OceanCurrent> getAll(){
    	String sql = "select * from " + TABLE;
        return query(sql, null, OceanCurrent.class);
    }

}
