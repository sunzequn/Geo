package com.sunzequn.geo.data.baike.handler;

import com.sunzequn.geo.data.baike.bdbk.UrlType;
import com.sunzequn.geo.data.baike.bdbk.UrlTypeDao;
import com.sunzequn.geo.data.utils.ReadUtils;
import com.sunzequn.geo.data.utils.WriteUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sunzequn on 2016/4/19.
 */
public class UrlToFile {

    private static final String FILE = "D:/DevSpace/github/Geo/Data/src/main/resources/file/";
    private static UrlTypeDao urlTypeDao = new UrlTypeDao();
    private static UrlTypeDao quhuaDao = new UrlTypeDao("url_type_quhua");

    public static void main(String[] args) {
        String file = FILE + "url.txt";
        ReadUtils readUtils = new ReadUtils(file);

        String tofile = FILE + "tofile.txt";
        WriteUtils writeUtils = new WriteUtils(tofile, false);

        List<String> lines = readUtils.readByLine();
        System.out.println(lines.size());

        Set<String> sets = new HashSet<>();
        for (String line : lines) {
            sets.add(line.trim());
        }

        List<UrlType> quhuas = quhuaDao.getAll();
        System.out.println(quhuas.size());

        Set<String> res = new HashSet<>();

        for (UrlType quhua : quhuas) {
            String url = quhua.getUrl().trim();
            if (!sets.contains(url)) {
                res.add(url);
            }
        }

        System.out.println(res.size());

        for (String url : res) {
            writeUtils.write(url);
        }
        writeUtils.close();
    }

}
