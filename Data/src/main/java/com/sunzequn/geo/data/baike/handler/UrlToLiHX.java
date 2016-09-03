package com.sunzequn.geo.data.baike.handler;

import com.sunzequn.geo.data.baike.bdbk.UrlType;
import com.sunzequn.geo.data.baike.bdbk.UrlTypeDao;
import com.sunzequn.geo.data.utils.ReadUtils;
import com.sunzequn.geo.data.utils.WriteUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sunzequn on 2016/4/19.
 */
public class UrlToLiHX {

    private static final String FILE = "D:/DevSpace/github/Geo/Data/src/main/resources/file/";

    private static UrlTypeDao quhuaDao = new UrlTypeDao("url_type_quhua");

    public static void main(String[] args) {
        String file = FILE + "url.txt";
        ReadUtils readUtils = new ReadUtils(file);

        List<String> lines = readUtils.readByLine();
        System.out.println(lines.size());

        Set<String> sets = new HashSet<>();
        for (String line : lines) {
            sets.add(line.trim());
        }

        List<UrlType> quhuas = quhuaDao.getAll();
        System.out.println(" quhua " + quhuas.size());

        for (UrlType quhua : quhuas) {
            String url = quhua.getUrl().trim();
            sets.add(url);
        }
        System.out.println("li all " + sets.size());

        UrlTypeDao toLiDao = new UrlTypeDao("url_type_toli");

        List<UrlType> newTypes = toLiDao.getAll();
        System.out.println(" li now " + newTypes.size());

        Set<String> newSet = new HashSet<>();
        for (UrlType newType : newTypes) {
            newSet.add(newType.getUrl().trim());
            if (!sets.contains(newType.getUrl().trim())) {
                toLiDao.deleteByUrl(newType.getUrl());
            }
        }

//        ReadUtils readUtils1 = new ReadUtils(FILE + "broad");
//        List<String> broads = readUtils1.readByLine();
//        System.out.println(broads.size());
//        int num = 0;
//        List<UrlType> urlTypes = new ArrayList<>();
//        for (String broad : broads) {
//            String[] strings = StringUtils.split(broad.trim(), " ");
//            String url = strings[0];
//            String type = strings[1];
//            if (!newSet.contains(url)){
//                urlTypes.add(new UrlType(url, type, 1));
//                num++;
//            }
//        }
//        toLiDao.addTypeBatch(urlTypes);
//        System.out.println(num);


    }
}
