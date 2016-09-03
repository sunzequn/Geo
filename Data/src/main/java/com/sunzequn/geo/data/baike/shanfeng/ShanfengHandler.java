package com.sunzequn.geo.data.baike.shanfeng;

import com.sunzequn.geo.data.baike.bdbk.Title;
import com.sunzequn.geo.data.baike.bdbk.TitleDao;
import com.sunzequn.geo.data.baike.bdbk.UrlTypeDao;
import com.sunzequn.geo.data.china.geo.ChinaCityDao;
import com.sunzequn.geo.data.utils.ReadUtils;

import java.util.List;

/**
 * Created by sunzequn on 2016/4/24.
 */
public class ShanfengHandler {

    private static String FILE = "D:/DevSpace/github/Geo/Data/src/main/resources/shan/feng";
    private static UrlTypeDao urlTypeDao = new UrlTypeDao();
    private static TitleDao titleDao = new TitleDao();

    public static void main(String[] args) {
        feng();
    }

    private static void feng() {
        ReadUtils readUtils = new ReadUtils(FILE);
        List<String> lines = readUtils.readByLine();
        for (String line : lines) {
            if (line.contains("峰")) {
                List<Title> titles = titleDao.getByTitle(line.trim());
                if (titles != null) {
                    for (Title title : titles) {
                        urlTypeDao.addType(title.getUrl(), "山峰", 2);
                    }
                }
            }
        }
    }
}
