package com.sunzequn.geo.data.geonames;

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

    private static final String FILE = "/Volumes/Sloriac/DataSet/Geo/all-geonames-rdf.txt";
    private static final String GN = "http://www.geonames.org/ontology#";
    private static final String PREFIX = "http://sws.geonames.org/";
    private static final String NEARBY_SUFFIX = "/nearby.rdf";
    private static final String CONTAINS_SUFFIX = "/contains.rdf";
    private static final String NEIGHBOURS_SUFFIX = "/neighbours.rdf";
    private static final String CONTAINS_TABLE = "contains_url";
    private static final String NEIGHBOURS_TABLE = "neighbours_url";
    private static final String NEARBY_TABLE = "nearby_url";

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
                if (line.contains(nearby)) {
                    nearbyNum++;
                    handleLine(line, nearby, NEARBY_TABLE, NEARBY_SUFFIX);
                }
                if (line.contains(neighbours)) {
                    neighboursNum++;
                    handleLine(line, neighbours, NEIGHBOURS_TABLE, NEIGHBOURS_SUFFIX);
                }
                if (line.contains(contains)) {
                    containsNum++;
                    handleLine(line, contains, CONTAINS_TABLE, CONTAINS_SUFFIX);
                }
            }
        } finally {
            LineIterator.closeQuietly(it);
        }
        System.out.println("nearby : " + nearbyNum);
        System.out.println("neighbours : " + neighboursNum);
        System.out.println("contains : " + containsNum);
        TimeUtils.end();
        TimeUtils.print();
    }

    private static void handleLine(String line, String type, String suffix, String table) {
        Rdf rdf = new Rdf();
        List<String> strings = rdf.getObject(line, GN + type);
        if (strings.size() == 1) {
            String string = StringUtils.removeStart(strings.get(0), PREFIX);
            string = StringUtils.removeEnd(string, suffix);
            int id = Integer.parseInt(string);
            ResourceDao resourceDao = new ResourceDao(table);
            Resource resource = new Resource(id, 0);
            resourceDao.save(resource);
        }
    }
}
