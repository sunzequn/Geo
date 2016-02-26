package com.sunzequn.geo.data.alignment.neo4j;

import com.sunzequn.geo.data.utils.PropertiesUtils;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

/**
 * Created by Sloriac on 16/2/26.
 */
public class NeoService {

    private static final String NEO4J_CONF_FILE = "Data/src/main/resources/conf/neo4j.properties";

    private static final NeoService instance = new NeoService();

    private static final String NEO_PATH_KEY = "path";
    private GraphDatabaseService graphDb;

    private NeoService() {
        PropertiesUtils propertiesUtils = new PropertiesUtils(NEO4J_CONF_FILE);
        String path = propertiesUtils.getValue(NEO_PATH_KEY);
        if (!StringUtils.isEmpty(path)) {
            graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(path);
            registerShutdownHook(graphDb);
        }
    }

    public static NeoService instance() {
        return instance;
    }

    private static void registerShutdownHook(final GraphDatabaseService graphDb) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }

    public GraphDatabaseService getGraphDb() {
        return graphDb;
    }


}
