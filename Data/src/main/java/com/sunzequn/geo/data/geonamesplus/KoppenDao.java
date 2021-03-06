package com.sunzequn.geo.data.geonamesplus;

import java.util.List;

/**
 * 
 * @author sunzequn
 *
 */
public class KoppenDao extends BaseDao {

    private static final String TABLE = "ext_koppen";

    public KoppenDao() {
    }

    public List<Koppen> getAll() {
        String sql = "select * from " + TABLE + " order by latitude desc, longitude";
        return query(connection, sql, null, Koppen.class);
    }

    public Koppen getByLngLat(double lng, double lat) {
        String sql = "select * from " + TABLE + " where longitude = ? and latitude = ?";
        Object[] params = {lng, lat};
        List<Koppen> koppens = query(connection, sql, params, Koppen.class);
        return koppens == null ? null : koppens.get(0);
    }


}
