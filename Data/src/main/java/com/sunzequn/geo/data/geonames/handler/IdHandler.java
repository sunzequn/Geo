package com.sunzequn.geo.data.geonames.handler;

import com.sunzequn.geo.data.geonames.bean.Id;
import com.sunzequn.geo.data.geonames.bean.IdDao;
import com.sunzequn.geo.data.utils.TimeUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * Created by sloriac on 16-2-2.
 */
public class IdHandler {

    private static final String FILE = "Data/src/main/resources/data/geonames/all-geonames-rdf.txt";
    private static final String PREFIX = "http://sws.geonames.org/";


    public static void main(String[] args) {
        TimeUtils timeUtils = new TimeUtils();
        timeUtils.start();
        int num = 0;
        try {
            IdDao idDao = new IdDao();
            File file = new File(FILE);
            LineIterator it = FileUtils.lineIterator(file, "UTF-8");
            while (it.hasNext()) {
                String line = it.nextLine().trim();
                if (line.startsWith("http")){
                    num++;
                    String string = StringUtils.removeEnd(StringUtils.removeStart(line, PREFIX), "/");
                    int idNum = Integer.parseInt(string);
                    Id id = new Id(idNum, 0);
                    idDao.save(id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(num);
        timeUtils.end();
        timeUtils.print();
    }
}
