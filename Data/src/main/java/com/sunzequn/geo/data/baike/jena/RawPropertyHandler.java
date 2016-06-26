package com.sunzequn.geo.data.baike.jena;

import com.sunzequn.geo.data.algorithm.hanyu.Pinyin;
import com.sunzequn.geo.data.baike.basicinfo.BasicInfoUtils;
import com.sunzequn.geo.data.baike.bdbk.*;
import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.ReadUtils;
import com.sunzequn.geo.data.utils.WriteUtils;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sunzequn on 2016/4/30.
 */
public class RawPropertyHandler {

    private static final String CLINGA = "http://ws.nju.edu.cn/clinga/";
    private static final String DIR = "D:/DevSpace/github/Geo/Data/src/main/resources/baike/";
    private static BasicInfoDao basicInfoDao = new BasicInfoDao();
    private static UrlTypeDao urlTypeDao = new UrlTypeDao();
    private static Pinyin pinyin = new Pinyin();

    public static void main(String[] args) {
        handle();
    }

    private static void handle() {
        String file = DIR + "rawprop.txt";
        WriteUtils writeUtils = new WriteUtils(file, false);
        List<UrlType> urlTypes = urlTypeDao.getAll();
        Set<String> rawProps = new HashSet<>();
        for (UrlType urlType : urlTypes) {
            List<BasicInfo> basicInfos = basicInfoDao.getByUrl(urlType.getUrl());
            if (!ListUtils.isEmpty(basicInfos)) {
                for (BasicInfo basicInfo : basicInfos) {
                    String key = BasicInfoUtils.parseKey(basicInfo.getKey());
                    key = getProp(key);
                    if (key != null) {
                        rawProps.add(basicInfo.getKey().trim());
                    }
                }
            }
        }
        System.out.println(rawProps.size());
        for (String rawProp : rawProps) {
            writeUtils.write(rawProp);
        }
        writeUtils.close();
    }


    private static String getProp(String prop) {
        if (isNumeric(prop) || isNumeric(String.valueOf(prop.charAt(prop.length() - 1)))) {
            return null;
        }
        String key = CLINGA + pinyin.getPinyinWithFirstOneLower(prop);
        if (isUrl(key) && !prop.endsWith("%") && !prop.contains(":1") && !prop.endsWith("="))
            return key;
        return null;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static boolean isUrl(String pInput) {
        if (pInput == null) {
            return false;
        }
        String regEx = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(pInput);
        return matcher.matches();
    }


    private static List<String> getType() {
        ReadUtils readUtils = new ReadUtils(DIR + "type");
        List<String> lines = readUtils.readByLine();
        return lines;
    }

    private static void toFile(Model model, String file) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        RDFDataMgr.write(fileOutputStream, model, Lang.RDFXML);
    }

}
