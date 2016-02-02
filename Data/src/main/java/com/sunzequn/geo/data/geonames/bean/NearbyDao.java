package com.sunzequn.geo.data.geonames.bean;

import com.sunzequn.geo.data.dao.BaseDao;

import java.nio.channels.NonReadableChannelException;
import java.util.List;
import java.util.Objects;

/**
 * Created by sloriac on 16-2-2.
 */
public class NearbyDao extends BaseDao{

    private static final String TABLENAME = "nearby";

    public NearbyDao() {
        getConnection("geonames_file");
    }

    public int save(Nearby nearby) {
        String sql = "insert into " + TABLENAME + " values (?, ?, ?)";
        Object[] params = {nearby.getId(), nearby.getContent(), nearby.getIfhandled()};
        return execute(sql, params);
    }

    public List<Nearby> getAll(int start, int limit) {
        String sql = "select * from " + TABLENAME  + " where id > ? and ifhandled = 0 order by id limit " + limit;
        Object[] params = {start};
        return query(sql, params, Nearby.class);
    }

    public int update(int id, int ifhandled){
        String sql = "update " + TABLENAME + " set ifhandled = ? where id = ?";
        Object[] params = {ifhandled, id};
        return execute(sql, params);
    }

    public void close() {
        closeConnection();
    }

    public static void main(String[] args) {
        NearbyDao nearbyDao = new NearbyDao();
//        List<NearbyCrawler> nearbies = nearbyDao.getAll(20000);
//        System.out.println(nearbies.size());
//        Nearby nearby = new Nearby(0, "ss", 0);
//        nearbyDao.save(nearby);
        List<Nearby> nearbies = nearbyDao.getAll(3000, 10);
        for (Nearby nearby : nearbies){
            System.out.println(nearby.getId());
        }
    }
}
