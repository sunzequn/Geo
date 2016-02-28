package com.sunzequn.geo.data.geonames.dao;

import com.sunzequn.geo.data.dao.BaseDao;
import com.sunzequn.geo.data.geonames.bean.FeatureCodes;
import com.sunzequn.geo.data.utils.ListUtils;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Sloriac on 16/2/28.
 */
public class FeatureCodesDao extends BaseDao {

    private static final String DATABASE = "geonames";
    private static final String TABLE = "featureCodes";
    private Connection connection;

    public FeatureCodesDao() {
        connection = getConnection(DATABASE);
    }

    public List<FeatureCodes> getAll() {
        String sql = "select * from " + TABLE;
        return query(connection, sql, null, FeatureCodes.class);
    }

    public static void main(String[] args) {
        FeatureCodesDao dao = new FeatureCodesDao();
        ListUtils.print(dao.getAll());
    }
}
