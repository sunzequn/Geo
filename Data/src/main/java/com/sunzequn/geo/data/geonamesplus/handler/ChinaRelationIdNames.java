package com.sunzequn.geo.data.geonamesplus.handler;

import com.sunzequn.geo.data.utils.ReadUtils;
import com.sunzequn.geo.data.utils.WriteUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sloriac on 16-12-6.
 */
public class ChinaRelationIdNames {

    public static void main(String[] args) {
        ReadUtils reader = new ReadUtils(GeoNamesConf.GEONAMES_CHINA_RELATIONS);
        List<String> lines = reader.readByLine();
        Set<String> ids = new HashSet<>();
        for (String line : lines) {
            String[] params = line.split(" ");
            if (params.length == 3){
                ids.add(params[0].trim());
                ids.add(params[2].trim());
            } else {
                System.out.println(line);
            }
        }
        System.out.println(ids.size());
        relationZhs(ids);
    }

    private static void relationZhs(Set<String> idset) {
        ReadUtils readUtils = new ReadUtils(GeoNamesConf.GEONAMES_ZH_ALL);
        WriteUtils writeUtils = new WriteUtils(GeoNamesConf.GEONAMES_CHINA_RELARION_ZHS, false);
        List<String> lines = readUtils.readByLine();
        for (String line : lines) {
            String[] params = line.split(GeoNamesConf.SPLIT);
            String id = params[0];
            String name = params[1];
            if (idset.contains(params[0].trim())){
                writeUtils.write(id + " " + name);
            }
        }
        writeUtils.close();
    }
}
