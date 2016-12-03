package com.sunzequn.geo.data.geonamesplus;

import java.util.List;

public class FarmingDao extends GnextBaseDao {
	
    private static final String TABLE = "ext_farming";
    
    public List<Farming> getAll(){
    	String sql = "select * from " + TABLE;
        return query(sql, null, Farming.class);
    }

}
