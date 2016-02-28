package com.sunzequn.geo.data.alignment.sameas;

import com.sunzequn.geo.data.alignment.bean.EquivalentClass;
import com.sunzequn.geo.data.alignment.dao.EquivalentClassDao;
import com.sunzequn.geo.data.alignment.query.QueryDbpedia;
import com.sunzequn.geo.data.alignment.type.Clazz;
import com.sunzequn.geo.data.alignment.type.ClazzGraph;
import com.sunzequn.geo.data.alignment.type.ClazzGraphConstructor;
import com.sunzequn.geo.data.utils.ListUtils;
import org.neo4j.cypher.internal.compiler.v2_2.ast.In;
import org.neo4j.cypher.internal.compiler.v2_2.functions.Str;

import java.util.*;

/**
 * Created by Sloriac on 16/2/27.
 */
public class EntityHandler {

    private QueryDbpedia query = new QueryDbpedia();
    private ClazzGraphConstructor constructor = new ClazzGraphConstructor();
    private EquivalentClassDao equivalentClassDao = new EquivalentClassDao();

    public ClazzGraph getGraph(String uri) {
        List<Clazz> clazzs = getTypes(uri);
        clazzs = removeEquivalent(clazzs);
        List<String> types = constructor.contructGraph(clazzs);
        if (ListUtils.isEmpty(types)) {
            System.out.println(uri);
            System.out.println(types);
            return null;
        }
        ClazzGraph clazzGraph = new ClazzGraph();
        clazzGraph.setSuperClassRels(types);
        return clazzGraph;
    }

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
     * 去掉等价类,后面考虑保存等价类，但是不参与计算
     *
     * @param clazzs
     */
    public List<Clazz> removeEquivalent(List<Clazz> clazzs) {
        if (!ListUtils.isEmpty(clazzs)) {
            Set<Integer> idsToRemove = new HashSet<>();
            for (int i = 0; i < clazzs.size(); i++) {
                List<String> equivalents = clazzs.get(i).getEquivalentClasses();
                if (equivalents != null) {
                    for (int j = 0; j < clazzs.size(); j++) {
                        if (equivalents.contains(clazzs.get(j).getUri())) {
                            long isize = ListUtils.size(clazzs.get(i).getSuperClasses());
                            long jsize = ListUtils.size(clazzs.get(j).getSuperClasses());
                            String iuri = DbpediaUtils.dbo(clazzs.get(i).getUri());
                            String juri = DbpediaUtils.dbo(clazzs.get(j).getUri());
                            if (isize < jsize) {
                                idsToRemove.add(i);
                                equivalentClassDao.save(new EquivalentClass(juri, iuri));
                            } else if (jsize < isize) {
                                idsToRemove.add(j);
                                equivalentClassDao.save(new EquivalentClass(iuri, juri));
                            }
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
