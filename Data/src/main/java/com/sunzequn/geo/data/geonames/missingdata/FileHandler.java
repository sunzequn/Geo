package com.sunzequn.geo.data.geonames.missingdata;

import com.sunzequn.geo.data.jena.Rdf;
import com.sunzequn.geo.data.utils.StringUtils;
import com.sunzequn.geo.data.utils.TimeUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Sloriac on 16/1/21.
 * <p>
 * 从about.rdf中处理缺少的rdf数据链接
 */
public class FileHandler {

    private static final String FILE = "Data/src/main/resources/data/geonames/all-geonames-rdf.txt";
    private static final String GN = "http://www.geonames.org/ontology#";
    private static final String RDFS = "http://www.w3.org/2000/01/rdf-schema#";
    private static final String PREFIX = "http://sws.geonames.org/";
    private static final String CONTAINS_SUFFIX = "/contains.rdf";
    private static final String NEIGHBOURS_SUFFIX = "/neighbours.rdf";
    private static final String CONTAINS_TABLE = "contains_url";
    private static final String NEIGHBOURS_TABLE = "neighbours_url";
    private static final String NEARBY_TABLE = "nearby_url";
    private static ResourceDao nearbyDao = new ResourceDao(NEARBY_TABLE);
    private static ResourceDao containsDao = new ResourceDao(CONTAINS_TABLE);
    private static ResourceDao neighboursDao = new ResourceDao(NEIGHBOURS_TABLE);

    public static void main(String[] args) throws IOException {

        String nearby = "nearbyFeatures";
        String neighbours = "neighbouringFeatures";
        String contains = "childrenFeatures";
        int nearbyNum = 0;
        int neighboursNum = 0;
        int containsNum = 0;

        TimeUtils.start();

        File file = new File(FILE);
        LineIterator it = FileUtils.lineIterator(file, "UTF-8");
        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                if (!line.contains(nearby)) {
                    nearbyNum++;
                    handleNearby(line);
                }
                if (line.contains(neighbours)) {
                    neighboursNum++;
                    handleLine(neighboursDao, line, neighbours, NEIGHBOURS_SUFFIX);
                }
                if (line.contains(contains)) {
                    containsNum++;
                    handleLine(containsDao, line, contains, CONTAINS_SUFFIX);
                }
            }
        } finally {
            LineIterator.closeQuietly(it);
        }
        System.out.println("nearby : " + nearbyNum);
        System.out.println("neighbours : " + neighboursNum);
        System.out.println("contains : " + containsNum);
        nearbyDao.close();
        neighboursDao.close();
        containsDao.close();
        TimeUtils.end();
        TimeUtils.print();
    }

    private static void handleLine(ResourceDao resourceDao, String line, String type, String suffix) {
        Rdf rdf = new Rdf();
        List<String> strings = rdf.getObject(line, GN + type);
        if (strings.size() == 1) {
            String string = StringUtils.removeStart(strings.get(0), PREFIX);
            string = StringUtils.removeEnd(string, suffix);
            int id = Integer.parseInt(string);
//            Resource resource = new Resource(id, 0);
//            resourceDao.save(resource);
        }
    }

    private static void handleNearby(String line) {
        Rdf rdf = new Rdf();
        List<String> strings = rdf.getObject(line, RDFS + "isDefinedBy");
        if (strings.size() == 1) {
            String string = StringUtils.removeStart(strings.get(0), PREFIX);
            string = StringUtils.removeEnd(string, "/about.rdf");
            int id = Integer.parseInt(string);
            System.out.println(id);
//            Resource resource = new Resource(id, 0);
//            nearbyDao.save(resource);
        }
    }
}
