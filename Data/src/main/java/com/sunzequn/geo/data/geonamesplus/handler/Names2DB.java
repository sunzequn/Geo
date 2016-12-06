package com.sunzequn.geo.data.geonamesplus.handler;

import com.sunzequn.geo.data.utils.ReadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sloriac on 16-12-5.
 */
public class Names2DB {

    public static void main(String[] args) {
//        zh2Db();
        name2db();
    }

    private static void name2db() {
        NamesMappingDao dao = new NamesMappingDao("geoname_names");
        ReadUtils reader = new ReadUtils(GeoNamesConf.GEONAMES_NAME_FILE);
        List<String> lines = reader.readByLine();
        System.out.println(lines.size());
        List<NamesMapping> mappings = new ArrayList<>();
        int n = 200000;
        for (String line : lines) {
            String[] params = line.split(GeoNamesConf.SPLIT);
            if (params.length == 2){
                int id = Integer.parseInt(params[0]);
                String name = params[1];
                NamesMapping mapping = new NamesMapping(id, name);
                mappings.add(mapping);
                if (mappings.size() == n){
                    System.out.println(n);
                    dao.addBatch(mappings);
                    mappings = new ArrayList<>();
                }
            }
        }
        dao.addBatch(mappings);
    }

    private static void zh2Db() {

        ReadUtils reader = new ReadUtils(GeoNamesConf.GEONAMES_ZH_ALL);
        List<String> lines = reader.readByLine();
        System.out.println(lines.size());
        List<NamesMapping> mappings = new ArrayList<>();
        for (String line : lines) {
            String[] params = line.split(GeoNamesConf.SPLIT);
            if (params.length == 2){
                int id = Integer.parseInt(params[0]);
                String name = params[1];
                NamesMapping mapping = new NamesMapping(id, name);
                mappings.add(mapping);
            }
        }
        NamesMappingDao dao = new NamesMappingDao("geoname_names_zh");
        dao.addBatch(mappings);
    }
}
