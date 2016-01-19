package com.sunzequn.geo.data;

import com.sunzequn.geo.data.utils.TimeUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;

/**
 * Created by Sloriac on 16/1/19.
 */
public class TestGeoNames {

    public static void main(String[] args) throws IOException {
        String path = "/Volumes/Sloriac/DataSet/Geo/all-geonames-rdf.txt";
        TimeUtils.start();
        File file = new File(path);
        LineIterator it = FileUtils.lineIterator(file, "UTF-8");
        int count = 0;
        String param = "gn:nearby rdf:resource";
        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                if (line.contains(param)) {
                    System.out.println(line);
                }
            }
        } finally {
            LineIterator.closeQuietly(it);
        }
        System.out.println(count);
        TimeUtils.end();
        TimeUtils.print();
    }
}
