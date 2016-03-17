package com.sunzequn.geo.data.baike.handler;

import com.sunzequn.geo.data.alignment.sameas.EntityHandler;
import com.sunzequn.geo.data.alignment.type.ClazzGraph;
import com.sunzequn.geo.data.baike.dao.InfoBoxDao;

/**
 * Created by Sloriac on 16/3/16.
 */
public class QueryHandler {

    private QueryZhDbpeida queryZhDbpeida = new QueryZhDbpeida();
    private QueryCoreDbpedia queryCoreDbpedia = new QueryCoreDbpedia();
    private EntityHandler entityHandler = new EntityHandler();
    private InfoBoxDao infoBoxDao = new InfoBoxDao();

    public ClazzGraph getTypes(String localNameZh) {
        String uri = queryZhDbpeida.getEnSameAs(localNameZh);
        //中文dbpedia没有就去核心库里面根据label查
        if (uri == null) {
            uri = queryCoreDbpedia.queryUriByLabel(localNameZh);
        }
        return entityHandler.getGraph(uri);
    }

    public static void main(String[] args) {
        QueryHandler queryHandler = new QueryHandler();
        System.out.println(queryHandler.getTypes("毛泽东"));
    }
}
