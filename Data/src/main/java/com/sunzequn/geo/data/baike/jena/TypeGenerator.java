package com.sunzequn.geo.data.baike.jena;

import com.sunzequn.geo.data.algorithm.hanyu.Pinyin;
import com.sunzequn.geo.data.baike.bdbk.TypeUri;
import com.sunzequn.geo.data.baike.bdbk.TypeUriDao;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDF;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/30.
 */
public class TypeGenerator {

    //百度百科uri前缀
    private static final String CLINGA = "http://ws.nju.edu.cn/clinga/";
    private static final String DIR = "D:/DevSpace/github/Geo/Data/src/main/resources/baike/";
    private static Pinyin pinyin = new Pinyin();
    private static TypeUriDao typeUriDao = new TypeUriDao();

    public static void main(String[] args) {
        List<TypeUri> typeUris = typeUriDao.getAll();
        Model model = ModelFactory.createDefaultModel();
        for (TypeUri typeUri : typeUris) {
            Resource s = model.createResource(typeUri.getUri());
            Resource p = model.createResource(typeUri.getType());
            s.addProperty(RDF.type, p);
        }
        toFile(model, DIR + "type.rdf");
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
