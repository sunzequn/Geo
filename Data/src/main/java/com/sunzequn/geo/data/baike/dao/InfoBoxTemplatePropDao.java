package com.sunzequn.geo.data.baike.dao;

import com.sunzequn.geo.data.baike.bean.InfoBoxTemplateProp;
import com.sunzequn.geo.data.dao.BaseDao;
import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.StringUtils;

import java.sql.Connection;
import java.util.List;

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
        String sql = "insert into " + TABLE + " (name, domain1, range1, type) values (?, ?, ?, ?)";
        Object[] params = {prop.getName(), prop.getDomain1(), prop.getRange1(), prop.getType()};
        return execute(connection, sql, params);
    }

    public List<InfoBoxTemplateProp> getAll() {
        String sql = "select * from " + TABLE;
        return query(connection, sql, null, InfoBoxTemplateProp.class);
    }

    public InfoBoxTemplateProp getByName(String name) {
        String sql = "select * from " + TABLE + " where name = ?";
        Object[] parmas = {name};
        List<InfoBoxTemplateProp> props = query(connection, sql, parmas, InfoBoxTemplateProp.class);
        if (ListUtils.isEmpty(props)) {
            return null;
        }
        return props.get(0);
    }

    public int updateType(String name, int type) {
        String sql = "update " + TABLE + " set type = ? where name = ?";
        Object[] params = {type, name};
        return execute(connection, sql, params);
    }

    public int updateDomain(String name, String domain) {
        String sql = "update " + TABLE + " set domain1 = ? where name = ?";
        Object[] params = {domain, name};
        return execute(connection, sql, params);
    }

    public int updateRange(String name, String range) {
        String sql = "update " + TABLE + " set range1 = ? where name = ?";
        Object[] params = {range, name};
        return execute(connection, sql, params);
    }

    public int addDomain(String name, String domain) {
        InfoBoxTemplateProp prop = getByName(name);
        if (prop == null) {
            prop = new InfoBoxTemplateProp(domain, name, "", 0);
            save(prop);
            return 1;
        } else {
            if (StringUtils.isNullOrEmpty(prop.getDomain1())) {
                return updateDomain(name, domain);
            } else {
                return updateDomain(name, prop.getDomain1() + "/" + domain);
            }
        }
    }

    public int addRange(String name, String range) {
        InfoBoxTemplateProp prop = getByName(name);
        if (prop == null) {
            System.out.println(name + " 出错");
            return -1;
        } else {
            if (StringUtils.isNullOrEmpty(prop.getRange1())) {
                return updateRange(name, range);
            } else {
                return updateRange(name, prop.getRange1() + "/" + range);

            }
        }
    }

    public static void main(String[] args) {
        InfoBoxTemplateProp prop = new InfoBoxTemplateProp("domain", "name", "range", 1);
        InfoBoxTemplatePropDao dao = new InfoBoxTemplatePropDao();
        dao.save(prop);
        dao.addDomain("name", "dd");
        dao.addDomain("name", "ss");

        dao.addRange("name", "dd");
        dao.addRange("name", "ss");
    }
}
