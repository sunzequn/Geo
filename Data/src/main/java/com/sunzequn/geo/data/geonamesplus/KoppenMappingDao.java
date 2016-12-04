package com.sunzequn.geo.data.geonamesplus;


import java.util.List;

/**
 * 
 * @author sunzequn
 *
 */
public class KoppenMappingDao extends BaseDao {

    private static final String TABLE = "ext_koppen_mapping";

    public List<KoppenMapping> getByKoppenType(String koppenType) {
        String sql = "select * from " + TABLE + " where koppentype = ? order by latconstraint asc, lngconstraint asc";
        Object[] params = {koppenType};
        List<KoppenMapping> koppenMappings = query(connection, sql, params, KoppenMapping.class);
        return koppenMappings == null ? null : koppenMappings;
    }
    
    public static void main(String[] args) {
    	KoppenMappingDao koppenMappingDao = new KoppenMappingDao();
    	System.out.println(koppenMappingDao.getByKoppenType("Cwb"));
	}
}
