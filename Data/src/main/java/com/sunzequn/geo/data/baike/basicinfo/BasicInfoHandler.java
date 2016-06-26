package com.sunzequn.geo.data.baike.basicinfo;

import com.mongodb.gridfs.CLI;
import com.sunzequn.geo.data.algorithm.hanyu.Pinyin;
import com.sunzequn.geo.data.baike.bdbk.*;
import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.MyStringUtils;
import com.sunzequn.geo.data.utils.ReadUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.tdb.base.objectfile.StringFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sunzequn on 2016/4/30.
 */
public class BasicInfoHandler {

    private static final String CLINGA = "http://ws.nju.edu.cn/clinga/";
    private static final String DIR = "D:/DevSpace/github/Geo/Data/src/main/resources/basicinfo/";
    private static final String PRE = "http://ws.nju.edu.cn/clinga/A";
    private static BasicInfoDao basicInfoDao = new BasicInfoDao();
    private static UrlIndexDao urlIndexDao = new UrlIndexDao();
    private static UrlTypeDao urlTypeDao = new UrlTypeDao();
    private static Pinyin pinyin = new Pinyin();

    public static void main(String[] args) {
//        System.out.println(isUrl("http://ws.nju.edu.cn/clinga/DaZhongYunShu"));
        List<String> types = getType();
        int num = 0;
        for (String type : types) {
            System.out.println(num++ + " " + type);
            handleType(type.trim());
        }
    }

    private static void handleType(String type) {
        String file = DIR + pinyin.getPinyinWithFirstOneUpper(type) + ".rdf";
        Model model = ModelFactory.createDefaultModel();
        List<UrlType> urlTypes = urlTypeDao.getByType(type);
        for (UrlType urlType : urlTypes) {
            String uri = PRE + urlIndexDao.getByUrl(urlType.getUrl()).getId();
            Resource s = model.createResource(uri);
            List<BasicInfo> basicInfos = basicInfoDao.getByUrl(urlType.getUrl());
            if (!ListUtils.isEmpty(basicInfos)) {
                for (BasicInfo basicInfo : basicInfos) {
                    String key = BasicInfoUtils.parseKey(basicInfo.getKey());
                    key = getProp(key);
                    if (key != null) {
                        Property p = model.createProperty(key);
                        String value = basicInfo.getValue();
                        if (value.contains("{{") && value.contains("}}")) {
                            List<String> valueUrls = BasicInfoUtils.parseValue(value, 3);
                            for (String valueUrl : valueUrls) {
                                UrlIndex urlIndex = urlIndexDao.getByUrl(valueUrl);
                                if (urlIndex != null) {
                                    String valueUri = PRE + urlIndex.getId();
                                    Resource o = model.createResource(valueUri);
                                    s.addProperty(p, o);
                                }
                            }
                        } else {
                            Literal o = model.createLiteral(value);
                            s.addProperty(p, o);
                        }
                    }
                }
            }
        }
        toFile(model, file);
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
