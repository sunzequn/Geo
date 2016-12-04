package com.sunzequn.geo.data.geonamesplus;

import java.util.List;

public class FarmingDao extends BaseDao {
	
    private static final String TABLE = "ext_farming";
    
    public List<Farming> getAll(){
    	String sql = "select * from " + TABLE;
        return query(connection, sql, null, Farming.class);
    }

}
