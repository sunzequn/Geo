package com.sunzequn.geo.data.geonames.dao;

import com.sunzequn.geo.data.dao.BaseDao;
import com.sunzequn.geo.data.geonames.bean.Geoname;
import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.StringUtils;

import java.sql.Connection;
import java.util.List;

/**
 * Created by sloriac on 16-2-14.
 */
public class GeonameDao extends BaseDao {

    private static final String DATABASE = "geonames_new";
    private static final String TABLE = "geoname";
    private Connection connection;

    public GeonameDao() {
        connection = getConnection(DATABASE);
    }

    public Geoname getById(int geonameId) {
        String sql = "select * from " + TABLE + " where geonameid = " + geonameId;
        List<Geoname> geonames = query(connection, sql, null, Geoname.class);
        if (ListUtils.isEmpty(geonames))
            return null;
        return geonames.get(0);
    }

    public List<Geoname> fuzzyMatching(double latUpper, double latLower, double lngUpper, double lngLower, String fcode) {
        String sql = "select * from " + TABLE + " where latitude < ? and latitude > ? and longitude < ? and longitude > ?";
        if (fcode != null) {
            sql += " and fcode = '" + fcode + "'";
        }
        Object[] params = {latUpper, latLower, lngUpper, lngLower};
        return query(connection, sql, params, Geoname.class);
    }

    public List<Geoname> countryChildrenByFcode(String country, String fcode) {
        String sql = "select * from " + TABLE + " where country = ? and fcode = ?";
        Object[] params = {country, fcode};
        return query(connection, sql, params, Geoname.class);
    }

    public List<Geoname> countryChildrenByFcode(int countryId, String fcode) {
        Geoname country = getById(countryId);
        if (country == null) {
            return null;
        }
        String sql = "select * from " + TABLE + " where country = ? and fcode = ?";
        Object[] params = {country.getCountry(), fcode};
        return query(connection, sql, params, Geoname.class);
    }

    public List<Geoname> countryChildrenByFclass(String country, String fclass) {
        String sql = "select * from " + TABLE + " where country = ? and fclass = ?";
        Object[] params = {country, fclass};
        return query(connection, sql, params, Geoname.class);
    }

    /**
     * 查询id的满足指定fcode的子类（包含的地点）
     * 也就是要满足country和admin1一致
     *
     * @param id geonames中的ADM1地点的id
     * @return
     */
    public List<Geoname> childrenByFcode(int id, String fcode) {
        Geoname geoname = getById(id);
        if (geoname == null) {
            return null;
        }
        String country = geoname.getCountry();
        String admin1 = geoname.getAdmin1();
        if (StringUtils.isNullOrEmpty(country) || StringUtils.isNullOrEmpty(admin1)) {
            System.out.println("admin1 数据有错误");
            return null;
        }
        String sql = "select * from " + TABLE + " where country = ? and admin1 = ? and fcode = ?";
        Object[] params = {country, admin1, fcode};
        return query(connection, sql, params, Geoname.class);
    }

    public List<Geoname> getADM2ByCountry(int amd1id) {
        Geoname geoname = getById(amd1id);
        if (geoname == null) {
            return null;
        }
        String country = geoname.getCountry();
        System.out.println(country);
        if (StringUtils.isNullOrEmpty(country)) {
            System.out.println("数据有错误");
            return null;
        }
        String sql = "select * from " + TABLE + " where country = ? and fcode = ?";
        Object[] params = {country, "ADM2"};
        return query(connection, sql, params, Geoname.class);
    }

    public List<Geoname> getADM3ByCountry(int amd1id) {
        Geoname geoname = getById(amd1id);
        if (geoname == null) {
            return null;
        }
        String country = geoname.getCountry();
        if (StringUtils.isNullOrEmpty(country)) {
            System.out.println("数据有错误");
            return null;
        }
        String sql = "select * from " + TABLE + " where country = ? and fcode = ?";
        Object[] params = {country, "ADM3"};
        return query(connection, sql, params, Geoname.class);
    }


    public List<Geoname> getADM2(int amd1id) {
        Geoname geoname = getById(amd1id);
        if (geoname == null) {
            return null;
        }
        String country = geoname.getCountry();
        String admin1 = geoname.getAdmin1();
        if (StringUtils.isNullOrEmpty(country) || StringUtils.isNullOrEmpty(admin1)) {
            System.out.println("数据有错误");
            return null;
        }
        String sql = "select * from " + TABLE + " where country = ? and admin1 = ? and fcode = ?";
        Object[] params = {country, admin1, "ADM2"};
        return query(connection, sql, params, Geoname.class);
    }

    public List<Geoname> getADM3(int amd2id) {
        Geoname geoname = getById(amd2id);
        if (geoname == null) {
            return null;
        }
        String country = geoname.getCountry();
        String admin2 = geoname.getAdmin2();
        if (StringUtils.isNullOrEmpty(country) || StringUtils.isNullOrEmpty(admin2)) {
            System.out.println("数据有错误");
            return null;
        }
        String sql = "select * from " + TABLE + " where country = ? and admin2 = ? and fcode = ?";
        Object[] params = {country, admin2, "ADM3"};
        return query(connection, sql, params, Geoname.class);
    }

    public List<Geoname> getADM3ByADM1(int amd1id) {
        Geoname geoname = getById(amd1id);
        if (geoname == null) {
            return null;
        }
        String country = geoname.getCountry();
        String admin1 = geoname.getAdmin1();
        if (StringUtils.isNullOrEmpty(country) || StringUtils.isNullOrEmpty(admin1)) {
            System.out.println("数据有错误");
            return null;
        }
        String sql = "select * from " + TABLE + " where country = ? and admin1 = ? and fcode = ?";
        Object[] params = {country, admin1, "ADM3"};
        return query(connection, sql, params, Geoname.class);
    }


    public static void main(String[] args) {
        GeonameDao geonameDao = new GeonameDao();
        System.out.println(geonameDao.getADM3(1787823));

//        System.out.println(geonameDao.getById(10));
//        System.out.println(geonameDao.fuzzyMatching(32, 30, 120, 110, "ADM1").size());
//        System.out.println(geonameDao.countryChildrenByFcode("CN", "ADM3").size());
    }
}
