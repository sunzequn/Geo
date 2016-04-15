package com.sunzequn.geo.data.baike.count;

import com.spatial4j.core.io.GeohashUtils;
import com.sunzequn.geo.data.geonames.GeoNameUtil;
import com.sunzequn.geo.data.geonames.bean.Geoname;
import com.sunzequn.geo.data.geonames.dao.GeonameDao;
import com.sunzequn.geo.data.utils.ListUtils;
import org.neo4j.cypher.internal.compiler.v2_2.ast.IntegerLiteral;
import org.neo4j.cypher.internal.compiler.v2_2.functions.Str;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sunzequn on 2016/4/15.
 */
public class CountLinkHandler {

    private static GeonameDao geonameDao = new GeonameDao();
    private static GeonamesLinkDao geonamesLinkDao = new GeonamesLinkDao();

    public static void main(String[] args) {
        getNotLink("地级市", "ADM2");
    }

    private static void getNotLink(String type, String fcode) {
        List<Geoname> geonames = geonameDao.countryChildrenByFcode("CN", fcode);
        List<GeonamesLink> geonamesLinks = geonamesLinkDao.getByType(type);
        Set<Integer> ids = new HashSet<>();
        if (!ListUtils.isEmpty(geonamesLinks) && !ListUtils.isEmpty(geonames)) {
            for (GeonamesLink geonamesLink : geonamesLinks) {
                int id = GeoNameUtil.parseId(geonamesLink.getLink());
                ids.add(id);
            }
            for (Geoname geoname : geonames) {
                if (!ids.contains(geoname.getGeonameid())) {
                    System.out.println(geoname);
                }
            }
        }
    }
}
