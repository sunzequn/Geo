package com.sunzequn.geo.data.geonamesplus;

import java.util.List;


/**
 * Created by sunzequn on 2016/7/22.
 */
public class ChinaClimateFactorDao extends GnextBaseDao {

    private static final String TABLE = "ext_china_climate_factor";

    public List<ChinaClimateFactor> getByName(int id){
    	String sql = "select * from " + TABLE + " where V01000 = ?";
        Object[] params = {id};
        List<ChinaClimateFactor> chinaClimateFactors = query(sql, params, ChinaClimateFactor.class);
    	return chinaClimateFactors != null ? chinaClimateFactors : null;
    }
}
