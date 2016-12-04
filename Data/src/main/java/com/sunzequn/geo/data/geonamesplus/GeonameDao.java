package com.sunzequn.geo.data.geonamesplus;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;


public class GeonameDao extends BaseDao {

    private static final String TABLE = "geoname";

    public Geoname getById(int geonameId) {
        String sql = "select * from " + TABLE + " where geonameid = " + geonameId;
        List<Geoname> geonames = query(connection, sql, null, Geoname.class);
        if (geonames == null)
            return null;
        return geonames.get(0);
    }

}
