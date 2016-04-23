package com.sunzequn.geo.data.baike.datamining.frequentpatterns;

import com.sunzequn.geo.data.baike.bdbk.BasicInfo;
import com.sunzequn.geo.data.baike.bdbk.BasicInfoDao;
import com.sunzequn.geo.data.baike.bdbk.UrlType;
import com.sunzequn.geo.data.baike.bdbk.UrlTypeDao;
import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.WriteUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sunzequn on 2016/4/23.
 */
public class FindBasicInfoFrequence {

    private static final float SUPPORT = 0.2f;
    private static long absSupport;

    private static final String FILE = "D:/DevSpace/github/Geo/Data/src/main/resources/mining/set.txt";
    private static UrlTypeDao urlTypeDao = new UrlTypeDao();
    private static BasicInfoDao basicInfoDao = new BasicInfoDao();

    public static void main(String[] args) {
        handleType("半岛", 1);
        mining();
    }

    private static void mining() {
        List<String[]> matrix = Reader.readAsMatrix("D:/DevSpace/github/Geo/Data/src/main/resources/mining/set.txt", "\t", "utf-8");
        System.out.println("size " + matrix.size());
        absSupport = (long) (SUPPORT * matrix.size());
        FpTree fpTree = new FpTree(absSupport);
        System.out.println("绝对支持度： " + absSupport);
        System.out.println("频繁项集： ");
        Map<String, Integer> frequentMap = new LinkedHashMap<String, Integer>();// 一级频繁项
        Map<String, FpNode> header = fpTree.getHeader(matrix, frequentMap);
        FpNode root = fpTree.getFpTree(matrix, header, frequentMap);
//        fpTree.printTree(root);
        Map<Set<FpNode>, Long> frequents = fpTree.fpGrowth(root, header, null);
        for (Map.Entry<Set<FpNode>, Long> fre : frequents.entrySet()) {
            for (FpNode node : fre.getKey())
                System.out.print(node.idName + " ");
            System.out.println("\t" + fre.getValue());
        }

    }

    private static void handleType(String type, int con) {
        List<UrlType> urlTypes = urlTypeDao.getByTypeConfidence(type, con);
        System.out.println(urlTypes.size());
        WriteUtils writeUtils = new WriteUtils(FILE, false);
        for (UrlType urlType : urlTypes) {
            List<BasicInfo> basicInfos = basicInfoDao.getByUrl(urlType.getUrl());
            if (!ListUtils.isEmpty(basicInfos)) {
                String line = "";
                for (BasicInfo basicInfo : basicInfos) {
                    line += basicInfo.getKey();
                    line += "\t";
                }
                line = StringUtils.removeEnd(line, "\t");
                writeUtils.write(line);
            }
        }
        writeUtils.close();
    }
}
