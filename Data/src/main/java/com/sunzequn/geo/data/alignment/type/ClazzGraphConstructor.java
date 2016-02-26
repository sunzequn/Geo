package com.sunzequn.geo.data.alignment.type;

import com.sunzequn.geo.data.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloriac on 16/2/26.
 */
public class ClazzGraphConstructor {

    public ClazzGraph constractGraph(Clazz clazz) {
        List<String> roots = new ArrayList<>();
        while (!ListUtils.isEmpty(clazz.getSuperClasses())) {
            String root = getRoot(clazz).getUri();
            roots.add(root);
            clazz = removeRoot(clazz, root);
        }
        System.out.println(roots);
        return null;
    }

    /**
     * 根据uri判断实体是否相同
     *
     * @param clazz1 第一个类
     * @param clazz2 第二个类
     * @return
     */
    private boolean isSame(Clazz clazz1, Clazz clazz2) {
        return (clazz1.getUri() != null && clazz2.getUri() != null) && clazz1.getUri().equals(clazz2.getUri());
    }

    /**
     * 获取root节点
     *
     * @param clazz 某个类
     * @return 如果正确则返回root节点, 否则返回null;
     */
    private Clazz getRoot(Clazz clazz) {
        //如果没有父类,则自己就是root节点,理论上来说,root是owl:Thing
        if (ListUtils.isEmpty(clazz.getSuperClasses())) {
            return clazz;
        }
        //理论上来讲,一个类节点的root是唯一的,这里做一个检查
        Clazz root = null;
        for (Clazz superClass : clazz.getSuperClasses()) {
            Clazz superRoot = getRoot(superClass);
            if (root == null) {
                root = superRoot;
            } else if (!isSame(root, superRoot)) {
                System.out.println("root不唯一");
                return null;
            }
        }
        return root;
    }

    private Clazz removeRoot(Clazz clazz, String uri) {
        List<Clazz> superClasses = clazz.getSuperClasses();
        int index = -1;
        for (int i = 0; i < superClasses.size(); i++) {
            if (superClasses.get(i).getUri().equals(uri)) {
                index = i;
                break;
            }
        }

        //superclass里面存在root节点即删除
        if (index != -1) {
            superClasses.remove(index);
            clazz.setSuperClasses(superClasses);
        }

        if (superClasses.size() > 0) {
            for (Clazz superClass : superClasses) {
                removeRoot(superClass, uri);
            }
        }
        return clazz;
    }

    public static void main(String[] args) {
        Clazz entity = new Clazz("E");
        Clazz A = new Clazz("A");
        Clazz B1 = new Clazz("B");
        Clazz B = new Clazz("B");
        Clazz C1 = new Clazz("C");
        Clazz C2 = new Clazz("C");
        Clazz C3 = new Clazz("C");
        List<Clazz> B1s = new ArrayList<>();
        B1s.add(C1);
        B1.setSuperClasses(B1s);
        List<Clazz> As = new ArrayList<>();
        As.add(B1);
        A.setSuperClasses(As);
        List<Clazz> Es = new ArrayList<>();
        Es.add(A);

        List<Clazz> Bs = new ArrayList<>();
        Bs.add(C2);
        B.setSuperClasses(Bs);

        Es.add(B);
        Es.add(C3);
        entity.setSuperClasses(Es);

        ClazzGraphConstructor clazzGraphConstructor = new ClazzGraphConstructor();
        clazzGraphConstructor.constractGraph(entity);


    }

}
