package com.sunzequn.geo.data.baike.bdbk;

import com.sunzequn.geo.data.algorithm.hanyu.Pinyin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/28.
 */
public class TypeUriHandler {

    private static final String CLINGA = "http://ws.nju.edu.cn/clinga/";
    private static String prefix = "http://ws.nju.edu.cn/clinga/A";
    private static UrlTypeDao urlTypeDao = new UrlTypeDao();
    private static TypeUriDao typeUriDao = new TypeUriDao();
    private static UrlIndexDao urlIndexDao = new UrlIndexDao();
    private static Pinyin pinyin = new Pinyin();

    public static void main(String[] args) {
        handle();
    }

    private static void handle() {
        List<UrlType> urlTypes = urlTypeDao.getAll();
        List<TypeUri> typeUris = new ArrayList<>();
        for (UrlType urlType : urlTypes) {
            int id = urlIndexDao.getByUrl(urlType.getUrl()).getId();
            String uri = prefix + id;
            String type = CLINGA + pinyin.getPinyinWithFirstOneUpper(urlType.getType());
            TypeUri typeUri = new TypeUri(uri, type);
            typeUris.add(typeUri);
            if (typeUris.size() % 1000 == 0) {
                typeUriDao.add(typeUris);
                typeUris = new ArrayList<>();
            }
        }
        if (typeUris.size() > 0) {
            typeUriDao.add(typeUris);
        }

    }
}
