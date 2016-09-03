package com.sunzequn.geo.data.geonames.cn;

import com.sunzequn.geo.data.utils.ReadUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunzequn on 2016/6/27.
 */
public class Handler {

    private static final String FOLDER = "Data/src/main/resources/geonamescn/";
    private static GeonamecnDao dao = new GeonamecnDao();

    public static void main(String[] args) {
        File folder = new File(FOLDER);
        for (File file : folder.listFiles()) {
            String filePath = FOLDER + file.getName();
            System.out.println(filePath);
            ReadUtils readUtils = new ReadUtils(filePath);
            List<String> lines = readUtils.readByLine();
            List<Geonamecn> geonamecns = new ArrayList<>();
            for (String line : lines) {
                String[] items = line.split("\t");
                int id = Integer.valueOf(items[0]);
                Geonamecn geonamecn = new Geonamecn(id, items[1]);
                geonamecns.add(geonamecn);
                if (geonamecns.size() > 10000) {
                    dao.saveBatch(geonamecns);
                    geonamecns = new ArrayList<>();
                }
            }
            if (geonamecns.size() > 0)
                dao.saveBatch(geonamecns);
        }
    }
}
