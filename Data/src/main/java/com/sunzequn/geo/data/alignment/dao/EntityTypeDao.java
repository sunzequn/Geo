package com.sunzequn.geo.data.alignment.dao;

import com.sunzequn.geo.data.alignment.bean.EntityType;
import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;

/**
 * Created by Sloriac on 16/2/27.
 */
public class EntityTypeDao extends BaseDao {

    private static final String DATABASE = "alignment";
    private static final String TABLE = "entity_type";
    private Connection connection;

    public EntityTypeDao() {
        this.connection = getConnection(DATABASE);
    }

    public int save(EntityType entityType) {
        String sql = "insert into " + TABLE + " values(?, ?)";
        Object[] params = {entityType.getUri(), entityType.getTypeclass()};
        return execute(connection, sql, params);
    }

    public static void main(String[] args) {
        EntityTypeDao dao = new EntityTypeDao();
        dao.save(new EntityType("da", "sd"));
    }
}
