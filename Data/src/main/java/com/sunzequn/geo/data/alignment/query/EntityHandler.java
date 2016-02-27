package com.sunzequn.geo.data.alignment.query;

import com.sunzequn.geo.data.alignment.type.Clazz;
import com.sunzequn.geo.data.utils.ListUtils;
import org.neo4j.cypher.internal.compiler.v2_2.ast.In;
import org.neo4j.cypher.internal.compiler.v2_2.functions.Str;

import java.util.*;

/**
 * Created by Sloriac on 16/2/27.
 */
public class EntityHandler {

    private QueryDbpedia query = new QueryDbpedia();

    /**
     * 根据实体的uri获取它的type类
     *
     * @param uri
     * @return
     */
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
                List<String> equivalentClass = query.queryEquivalentClass(typeUri);
                if (!ListUtils.isEmpty(equivalentClass)) {
                    clazz.setEquivalentClasses(equivalentClass);
                }
                clazzs.add(clazz);
            }
            return clazzs;
        }
        return null;
    }

    /**
     * 去掉等价类
     *
     * @param clazzs
     */
    public List<Clazz> removeEquivalent(List<Clazz> clazzs) {
        if (!ListUtils.isEmpty(clazzs)) {
            Set<Integer> idsToRemove = new HashSet<>();
            for (int i = 0; i < clazzs.size(); i++) {
                List<String> equivalents = clazzs.get(i).getEquivalentClasses();
                for (int j = 0; j < clazzs.size(); j++) {
                    if (equivalents.contains(clazzs.get(j).getUri())) {
                        long isize = ListUtils.size(clazzs.get(i).getSuperClasses());
                        long jsize = ListUtils.size(clazzs.get(j).getSuperClasses());
                        if (isize < jsize) {
                            idsToRemove.add(i);
                        } else if (jsize < isize) {
                            idsToRemove.add(j);
                        }
                    }
                }
            }
            for (Integer index : idsToRemove) {
                clazzs.remove(index.intValue());
            }
            return clazzs;

        }
        return null;
    }

    public static void main(String[] args) {

    }
}
