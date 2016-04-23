package com.sunzequn.geo.data.baike.extract;

import com.sunzequn.geo.data.baike.bdbk.UrlType;
import com.sunzequn.geo.data.baike.bdbk.UrlTypeDao;
import com.sunzequn.geo.data.baike.bdbk.UrlTypeDao2;

import java.util.*;

/**
 * Created by sunzequn on 2016/4/20.
 */
public class Chouqu100 {


    private static UrlTypeDao urlType100Dao = new UrlTypeDao("url_type_100");

    public static void main(String[] args) {

        UrlTypeDao2 urlTypeDao = new UrlTypeDao2();

        List<UrlType> types = urlTypeDao.getTypes();
        System.out.println(types);
        for (UrlType type : types) {
            String typeName = type.getType();
            System.out.println(typeName);
            List<UrlType> zhenglis = urlTypeDao.getByTypeConfidence(typeName, 1);
            List<UrlType> fanlis = urlTypeDao.getByTypeConfidence(typeName, 0);
            if (zhenglis != null) {
                chouqu(zhenglis);
            }
            if (fanlis != null) {
                chouqu(fanlis);
            }
        }
    }

    private static void chouqu(List<UrlType> urlTypes) {
        if (urlTypes == null) {
            return;
        }
        int size = urlTypes.size();
        if (size > 10000) {
            int num = size / 100;
            chouquByNum(urlTypes, num);
        } else if (size > 100) {
            chouquByNum(urlTypes, 100);
        } else {
            urlType100Dao.addTypeBatch(urlTypes);
        }
    }

    private static void chouquByNum(List<UrlType> urlTypes, int num) {

        int total = urlTypes.size();
        if (total < num) {
            return;
        }
        Random random = new Random();
        Set<UrlType> res = new HashSet<>();
        int n = 0;
        while (true) {
            int index = random.nextInt(total);
            n++;
            res.add(urlTypes.get(index));
            if (res.size() == num) {
                break;
            }
        }
        System.out.println("生成代价 " + num + "/" + n);
        if (res.size() == num) {
            List<UrlType> ls = new ArrayList<>(res);
            urlType100Dao.addTypeBatch(ls);
        } else {
            System.out.println("出错");
        }

    }

}
