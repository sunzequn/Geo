package com.sunzequn.geo.data.climate.china;

import com.sunzequn.geo.data.utils.ReadUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunzequn on 2016/7/22.
 */
public class Handler {

    private static final String FOLDER = "Data/src/main/resources/climate/";
    private static ChinaClimateFactorDao dao = new ChinaClimateFactorDao();

    public static void main(String[] args) {
        File folder = new File(FOLDER);
        for (File file : folder.listFiles()) {
            String filePath = FOLDER + file.getName();
            System.out.println(filePath);
            ReadUtils readUtils = new ReadUtils(filePath);
            List<String> lines = readUtils.readByLine();
            for (String line : lines) {
                if (line.equals("V01000 V04001 V04002 V04003 V11002 V11042 V11212 V11041 V11043 V14032 V10004 V10201 V10202 V12001 V12052 V12053 V13004 V13003 V13007 V13201"))
                    continue;
                String[] items = line.split(" ");
                List<Integer> values = new ArrayList<>();
                for (String item : items) {
                    values.add(Integer.valueOf(item));
                }
                ChinaClimateFactor factor = new ChinaClimateFactor(values);
                dao.save(factor);
            }
        }
    }
}
