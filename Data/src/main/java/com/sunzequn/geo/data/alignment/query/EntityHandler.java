package com.sunzequn.geo.data.alignment.query;

import com.sunzequn.geo.data.alignment.type.Clazz;
import com.sunzequn.geo.data.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloriac on 16/2/27.
 */
public class EntityHandler {

    private QueryDbpedia query = new QueryDbpedia();

    public List<Clazz> getTypes(String uri) {
        List<String> typeUris = query.queryType(uri);
        if (!ListUtils.isEmpty(typeUris)) {
            List<Clazz> clazzs = new ArrayList<>();
            for (String typeUri : typeUris) {
                Clazz clazz = new Clazz(typeUri);
                List<String> superClasses = query.querySuperClass(typeUri);
                if (!ListUtils.isEmpty(superClasses)) {
                    clazz.setSuperClasses(superClasses);
                }
                clazzs.add(clazz);
            }
            return clazzs;
        }
        return null;
    }
}
