package com.sunzequn.geo.data.baike.jena;

import com.mongodb.gridfs.CLI;
import com.sunzequn.geo.data.algorithm.hanyu.Pinyin;
import com.sunzequn.geo.data.baike.bean.InfoBoxTemplateProp;
import com.sunzequn.geo.data.baike.dao.InfoBoxTemplatePropDao;
import com.sunzequn.geo.data.utils.ReadUtils;
import com.sunzequn.geo.data.utils.WriteUtils;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFWriter;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.SKOS;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sunzequn on 2016/4/28.
 */
public class RawPropertyGenerator {

    private static final String CLINGA = "http://ws.nju.edu.cn/clinga/";
    private static final String PREFIX = "D:/DevSpace/github/Geo/Data/src/main/resources/baike/";
    private static final InfoBoxTemplatePropDao propDao = new InfoBoxTemplatePropDao();
    private static Pinyin pinyin = new Pinyin();
    private static int num = 0;

    public static void main(String[] args) {
        generator();
    }

    private static void generator() {

        List<InfoBoxTemplateProp> pprops = propDao.getAll();
        Set<String> propsets = new HashSet<>();
        for (InfoBoxTemplateProp prop : pprops) {
            propsets.add(prop.getName().trim());
        }
        System.out.println(propsets.size());

        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        InfoBoxTemplatePropDao propDao = new InfoBoxTemplatePropDao();
        List<InfoBoxTemplateProp> props = propDao.getAll();
        Set<String> propset = new HashSet<>();
        for (InfoBoxTemplateProp prop : props) {
            propset.add(prop.getName().trim());
        }
        String keyFile = PREFIX + "rawprop.txt";
        ReadUtils readUtils = new ReadUtils(keyFile);

        List<String> lines = readUtils.readByLine();
        for (String line : lines) {
            line = line.trim();
            if (!propset.contains(line))
                handleRawProp(model, line);
        }
        toFile(model, PREFIX + "raw.owl");
        System.out.println(num);
    }

    private static void handleRawProp(OntModel model, String line) {
        String key = pinyin.getPinyinWithFirstOneLower(line);
        if (!(key.endsWith("%") || key.startsWith("[") || key.endsWith("]"))) {
            key = CLINGA + key;
            OntClass ontClass = model.createClass(key);
            ontClass.setRDFType(RDF.Property);
            ontClass.addProperty(SKOS.prefLabel, model.createLiteral(line, MultiLang.ZH));
            num++;
        }
        System.out.println(num);
    }

    private static void toFile(OntModel model, String file) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        RDFDataMgr.write(fileOutputStream, model, Lang.RDFXML);
    }
}
