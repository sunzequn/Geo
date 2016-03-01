package com.sunzequn.geo.data.geonames.dao;

import com.sunzequn.geo.data.dao.BaseDao;
import com.sunzequn.geo.data.geonames.bean.FeatureCodes;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.cypher.internal.compiler.v2_2.functions.Str;

import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public List<FeatureCodes> getAllCodeWithoutClass() {
        List<FeatureCodes> featureCodes = getAll();
        for (FeatureCodes featureCode : featureCodes) {
            String code = StringUtils.split(featureCode.getCode(), ".")[1];
            featureCode.setCode(code);
        }
        return featureCodes;
    }

    public static void main(String[] args) {
        FeatureCodesDao dao = new FeatureCodesDao();
        List<FeatureCodes> featureCodes = dao.getAll();
        System.out.println(featureCodes.size());
        Set<String> strings = new HashSet<>();
        for (FeatureCodes featureCode : featureCodes) {
            String code = StringUtils.split(featureCode.getCode(), ".")[1];
            System.out.println(code);
            strings.add(code);
        }
        System.out.println(strings.size());
    }
}
