package com.sunzequn.geo.data.baike.dao;

import com.sunzequn.geo.data.baike.bean.InfoBoxTemplateProp;
import com.sunzequn.geo.data.dao.BaseDao;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;

/**
 * Created by sloriac on 16-3-29.
 */
public class InfoBoxTemplatePropDao extends BaseDao {


    private static final String DATABASE = "baidubaike";
    private static final String TABLE = "infobox_template_prop";
    private Connection connection;

    public InfoBoxTemplatePropDao() {
        this.connection = getConnection(DATABASE);
    }

    public int save(InfoBoxTemplateProp prop) {
        String sql = "insert into " + TABLE + "(name, templateid, type, comment) values (?, ?, ?, ?)";
        Object[] params = {prop.getName(), prop.getTemplateid(), prop.getType(), prop.getComment()};
        return execute(connection, sql, params);
    }

    public List<InfoBoxTemplateProp> getAll() {
        String sql = "select * from " + TABLE;
        return query(connection, sql, null, InfoBoxTemplateProp.class);
    }

    public int updateType(int id, int type) {
        String sql = "update " + TABLE + " set type = ? where id = ?";
        Object[] params = {type, id};
        return execute(connection, sql, params);
    }

    public int updateComment(int id, String comment) {
        String sql = "update " + TABLE + " set comment = ? where id = ?";
        Object[] params = {comment, id};
        return execute(connection, sql, params);
    }

}
