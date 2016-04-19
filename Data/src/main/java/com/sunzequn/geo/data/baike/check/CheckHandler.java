package com.sunzequn.geo.data.baike.check;

import com.sunzequn.geo.data.baike.bdbk.UrlType;
import com.sunzequn.geo.data.baike.bdbk.UrlTypeDao;
import com.sunzequn.geo.data.utils.ReadUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by sunzequn on 2016/4/19.
 */
public class CheckHandler {

    private static final String FILE_PATH = "D:/DevSpace/github/Geo/Data/src/main/resources/jiancha/";
    private static UrlTypeDao urlTypeDao = new UrlTypeDao();

    public static void main(String[] args) {
        checkSea();
    }

    private static void checkSea() {
        String seaFile = FILE_PATH + "sea.txt";
        ReadUtils readUtils = new ReadUtils(seaFile);
        List<String> lines = readUtils.readByLine();
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("1")) {
                String[] strings = StringUtils.split(line, "\t");
                System.out.println(strings[1]);
                String url = StringUtils.removeStart(strings[1], "http://baike.baidu.com");
                urlTypeDao.updateConfidence(url, 2);
            }
        }
    }
}
