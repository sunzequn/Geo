package com.sunzequn.geo.data.baike.basicinfo;

import com.sunzequn.geo.data.algorithm.hanyu.Pinyin;
import com.sunzequn.geo.data.baike.bdbk.*;
import com.sunzequn.geo.data.baike.bean.InfoBoxTemplateProp;
import com.sunzequn.geo.data.baike.bean.Prop;
import com.sunzequn.geo.data.baike.dao.InfoBoxTemplatePropDao;
import com.sunzequn.geo.data.baike.jena.MultiLang;
import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.ReadUtils;
import com.sunzequn.geo.data.utils.WriteUtils;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.SKOS;

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
public class RawHandler {

    private static final String CLINGA = "http://ws.nju.edu.cn/clinga/";
    private static final String DIR = "Data/src/main/resources/basicinfo";
    private static final String PRE = "http://ws.nju.edu.cn/clinga/A";
    private static BasicInfoDao basicInfoDao = new BasicInfoDao();
    private static UrlIndexDao urlIndexDao = new UrlIndexDao();
    private static UrlTypeDao urlTypeDao = new UrlTypeDao();
    private static Pinyin pinyin = new Pinyin();
    private static InfoBoxTemplatePropDao propDao = new InfoBoxTemplatePropDao();
    private static Set<String> keys = new HashSet<>();

    public static void main(String[] args) {
//        clean();
        toFile();
    }

    private static void clean() {
        ReadUtils readUtils = new ReadUtils("D:\\Code\\github\\Geo\\Data\\src\\main\\resources\\basicinfo\\raw");
        WriteUtils writeUtils = new WriteUtils("D:\\Code\\github\\Geo\\Data\\src\\main\\resources\\basicinfo\\raw_cleaned", false);
        List<String> rawProps = readUtils.readByLine();
        List<InfoBoxTemplateProp> props = propDao.getAll();
        Set<String> mappedProps = new HashSet<>();
        for (InfoBoxTemplateProp prop : props) {
            mappedProps.add(prop.getName());
        }
        System.out.println(mappedProps.size());
        for (String rawProp : rawProps) {
            if (!mappedProps.contains(rawProp)) {
                String temp = getProp(rawProp);
                if (temp != null) {
                    writeUtils.write(rawProp);
                }

            }
        }
    }

    private static void handleRaw() {
        List<String> types = getType();
        int num = 0;
        for (String type : types) {
            System.out.println(num++ + " " + type);
            handleType(type.trim());
            System.out.println(keys.size());
        }
        System.out.println(keys.size());
        WriteUtils writeUtils = new WriteUtils("D:\\Code\\github\\Geo\\Data\\src\\main\\resources\\basicinfo\\raw", false);
        for (String key : keys) {
            writeUtils.write(key);
        }
        writeUtils.close();
    }

    private static void handleType(String type) {

        List<UrlType> urlTypes = urlTypeDao.getByType(type);
        for (UrlType urlType : urlTypes) {
            List<BasicInfo> basicInfos = basicInfoDao.getByUrl(urlType.getUrl());
            if (!ListUtils.isEmpty(basicInfos)) {
                for (BasicInfo basicInfo : basicInfos) {
                    String key = BasicInfoUtils.parseKey(basicInfo.getKey());
//                    key = getProp(key);
                    if (key != null) {
                        key = key.trim();
                        keys.add(key);
                    }
                }
            }
        }
//        toFile(model, file);
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
        ReadUtils readUtils = new ReadUtils("D:\\Code\\github\\Geo\\Data\\src\\main\\resources\\basicinfo\\type.txt");
        List<String> lines = readUtils.readByLine();
        return lines;
    }

    private static void toFile() {
        String file = "D:\\Code\\github\\Geo\\Data\\src\\main\\resources\\basicinfo\\raw_property_ontology.owl";
        FileOutputStream fileOutputStream = null;
        Model model = ModelFactory.createDefaultModel();
        ReadUtils readUtils = new ReadUtils("D:\\Code\\github\\Geo\\Data\\src\\main\\resources\\basicinfo\\raw_cleaned");
        List<String> props = readUtils.readByLine();
        for (String prop : props) {
            String propUri = getProp(prop);
            Property p = model.createProperty(propUri);
            p.addProperty(RDF.type, RDF.Property);
            p.addProperty(SKOS.prefLabel, model.createLiteral(prop, MultiLang.ZH));
            p.addProperty(RDFS.label, model.createLiteral(prop, MultiLang.ZH));
        }

        try {
            fileOutputStream = new FileOutputStream(file, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        RDFDataMgr.write(fileOutputStream, model, Lang.RDFXML);
    }

}
