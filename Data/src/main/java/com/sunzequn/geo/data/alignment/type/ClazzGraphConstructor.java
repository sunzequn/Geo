package com.sunzequn.geo.data.alignment.type;

import com.sunzequn.geo.data.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloriac on 16/2/26.
 */
public class ClazzGraphConstructor {

    /**
     * 理论上来讲,clazzs里面是没有等价的类的,只有父类关系
     *
     * @param clazzs
     * @return
     */
    public List<String> contructGraph(List<Clazz> clazzs) {

        if (ListUtils.isEmpty(clazzs)) {
            return null;
        }

        List<String> res = new ArrayList<>();
        //一次取一个
        for (int i = 0; i < clazzs.size(); i++) {
            String root = getRoot(clazzs, res);
            if (root == null) {
                return null;
            }
            res.add(root);
            for (Clazz clazz : clazzs) {
                if (clazz.getSuperClasses() != null) {
                    clazz.setSuperClasses(removeRoot(clazz.getSuperClasses(), root));
                }

            }
        }
        return res;
    }

    private String getRoot(List<Clazz> clazzs, List<String> res) {
        if (ListUtils.isEmpty(clazzs)) {
            return null;
        }
        String root = null;
        for (Clazz clazz : clazzs) {
            //clazz没有父类,认为是一个root节点
            if (clazz.getSuperClasses() == null) {
                //节点已经作为root处理过了
                if (res.contains(clazz.getUri())) {

                }
                //第一次给root赋值
                else if (root == null) {
                    root = clazz.getUri();
                }
                //第二次即以后,检查后面的root节点是否相同,不相同就不对了
                else if (!root.equals(clazz.getUri())) {
                    System.out.println(root);
                    System.out.println(clazz.getUri());
                    System.out.println("root节点不唯一");
                    return null;
                }
            }
        }
        return root;
    }

    private List<String> removeRoot(List<String> superClass, String root) {
        superClass.remove(root);
        if (superClass.size() == 0) {
            return null;
        }
        return superClass;
    }

    public static void main(String[] args) {
        ClazzGraphConstructor clazzGraphConstructor = new ClazzGraphConstructor();

        List<Clazz> clazzs = new ArrayList<>();

        Clazz clazzA = new Clazz("A");
        List<String> As = new ArrayList<>();
        As.add("B");
        As.add("C");
        clazzA.setSuperClasses(As);
        clazzs.add(clazzA);

        Clazz clazzB = new Clazz("B");
        List<String> Bs = new ArrayList<>();
        Bs.add("C");
        clazzB.setSuperClasses(Bs);
        clazzs.add(clazzB);

        Clazz clazzC = new Clazz("C");
        clazzs.add(clazzC);

        System.out.println(clazzGraphConstructor.contructGraph(clazzs));


    }
}
