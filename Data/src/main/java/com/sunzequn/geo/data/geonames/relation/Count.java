package com.sunzequn.geo.data.geonames.relation;

import com.sunzequn.geo.data.utils.ReadUtils;
import com.sunzequn.geo.data.utils.WriteUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sloriac on 16-12-2.
 */
public class Count {

    private static final String FILE1 = "/home/sloriac/code/Geo/Data/src/main/resources/geonames_a_relations";
    private static final String FILE2 = "/home/sloriac/code/Geo/Data/src/main/resources/geonames_china_relations";

    public static void main(String[] args) throws IOException {
        Set<String> res = new HashSet<>();
        ReadUtils reader  = new ReadUtils(FILE1);
        List<String> lines = reader.readByLine();
        System.out.println(lines.size());
        res.addAll(lines);

        ReadUtils reader2  = new ReadUtils(FILE2);
        List<String> lines2 = reader2.readByLine();
        System.out.println(lines2.size());
        res.addAll(lines2);

        System.out.println(res.size());

        WriteUtils writeUtils = new WriteUtils("/home/sloriac/code/Geo/Data/src/main/resources/geonames_290w_relations", false);
        for (String re : res) {
            writeUtils.write(re.trim());
        }
        writeUtils.close();

    }

    private static void count() throws IOException {
        LineIterator lineIterator = FileUtils.lineIterator(new File(FILE1));
        int minId = -1;
        while (lineIterator.hasNext()) {
            String line = lineIterator.nextLine();
            String[] params = line.trim().split(" ");
            if (params.length == 3 && !params[1].equals("featureClass") && !params[1].equals("featureCode")){
                int id1 = Integer.parseInt(params[0]);
                if(id1 > minId)
                    minId = id1;
                int id2 = Integer.parseInt(params[2]);
                if (id2 > minId)
                    minId = id2;
            }

        }
        System.out.println(minId);
    }
}
