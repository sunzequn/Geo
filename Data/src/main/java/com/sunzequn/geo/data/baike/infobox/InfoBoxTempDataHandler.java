package com.sunzequn.geo.data.baike.infobox;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Sloriac on 16/3/17.
 */
public class InfoBoxTempDataHandler {

    private static InfoBoxTempDao infoBoxTempDao = new InfoBoxTempDao();

    public static void main(String[] args) {
        count();
    }

    private static void count() {
        List<InfoBoxTemplate> infoBoxTemplates = infoBoxTempDao.getAll();
        for (InfoBoxTemplate infoBoxTemplate : infoBoxTemplates) {
            infoBoxTemplate.initProps();
        }
        System.out.println("目录一共有: " + infoBoxTemplates.size());
        Set<String> categories = new HashSet<>();
        Set<String> props = new HashSet<>();
        int propNum = 0;
        for (InfoBoxTemplate infoBoxTemplate : infoBoxTemplates) {
            categories.add(infoBoxTemplate.getTitle());
            List<Prop> props1 = infoBoxTemplate.getProps();
            propNum += props1.size();
            for (Prop p : props1) {
                props.add(p.getName());
            }
        }
        System.out.println("去重后目录: " + categories.size());
        System.out.println("属性一共有: " + propNum);
        System.out.println("去重后属性有: " + props.size());
    }
}
