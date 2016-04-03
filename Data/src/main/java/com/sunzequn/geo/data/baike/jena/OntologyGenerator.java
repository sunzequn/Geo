package com.sunzequn.geo.data.baike.jena;

import com.sunzequn.geo.data.baike.bean.InfoBoxTemplate;
import com.sunzequn.geo.data.baike.dao.InfoBoxTemplateDao;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.OWL;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/3.
 */
public class OntologyGenerator {

    private static final String FILE = "D:/DevSpace/github/Geo/Data/src/main/resources/baike/ontology.owl";
//    private static final String RDF = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
//    private static final String RDFS = "http://www.w3.org/2000/01/rdf-schema#";
//    private static final String SKOS = "http://www.w3.org/2004/02/skos/core#";
//    private static final String OWL = "http://www.w3.org/2002/07/owl#";

    private static final String BAIDU = "http://ws.nju.edu.cn/geoscholar/baidu/";
    private static final String BO = BAIDU + "ontology/";
    private static final String BP = BAIDU + "property/";
    private static OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

    public static void main(String[] args) {

//        generateOntology();
        generateRelation();
    }

    private static void generateRelation() {
        OntClass ontClass = model.createClass();

        Property property = model.createProperty("http://www.geonames.org/ontology#featureCode");
        Restriction restriction = model.createRestriction(property);

        ontClass.setEquivalentClass(restriction);
        Resource resource = model.createResource("http://www.geonames.org/ontology#T.ATOL");
        restriction.setPropertyValue(OWL.hasValue, resource);
        toFile(model);
    }

    private static void generateOntology() {

        InfoBoxTemplateDao templateDao = new InfoBoxTemplateDao();
        List<InfoBoxTemplate> templates = templateDao.getAll();
        List<InfoBoxTemplate> templates1 = new ArrayList<>();
        List<InfoBoxTemplate> templates2 = new ArrayList<>();
        List<InfoBoxTemplate> templates3 = new ArrayList<>();
        for (InfoBoxTemplate template : templates) {
            if (template.getLevel() == 1) {
                templates1.add(template);
            } else if (template.getLevel() == 2) {
                templates2.add(template);
            } else {
                templates3.add(template);
            }
        }

        for (InfoBoxTemplate template : templates1) {
            handler(model, templates, template);
        }
        for (InfoBoxTemplate template : templates2) {
            handler(model, templates, template);
        }
        for (InfoBoxTemplate template : templates3) {
            handler(model, templates, template);
        }
        toFile(model);
    }

    private static void handler(OntModel model, List<InfoBoxTemplate> templates, InfoBoxTemplate template) {
        String ns = BO + template.getTitle();
        OntClass ontClass = model.getOntClass(ns);
        //这个类不存在，就创建
        if (ontClass == null) {
            OntClass newClass = model.createClass(ns);
            System.out.println(newClass);
            newClass.setRDFType(OWL.Class);
            //设置父类
            List<String> parents = getParentsByName(templates, template.getTitle());
            if (parents != null) {
                if (parents.size() > 1) {
                    System.out.println(template.getTitle());
                    System.out.println(parents);
                }
                for (String parent : parents) {
                    String parentNs = BO + parent;
                    OntClass parentClass = model.getOntClass(parentNs);
                    if (parentClass == null) {
                        System.out.println("出错");
                    } else {
                        newClass.addSuperClass(parentClass);
                    }
                }
            }
        }
        //存在就不管了
    }

    private static List<String> getParentsByName(List<InfoBoxTemplate> templates, String localName) {
        List<String> parents = new ArrayList<>();
        for (InfoBoxTemplate template : templates) {
            if (template.getTitle().trim().equals(localName)) {
                String parent = getTitleById(templates, template.getParentid());
                //一级目录的父节点是自己
                if (parent != null && !parent.equals(localName)) {
                    parents.add(parent);
                }
            }
        }
        if (parents.size() > 0) {
            return parents;
        }
        return null;
    }

    private static String getTitleById(List<InfoBoxTemplate> templates, int id) {
        for (InfoBoxTemplate template : templates) {
            if (template.getId() == id) {
                return template.getTitle().trim();
            }
        }
        return null;
    }

    private static void toFile(OntModel model) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(FILE, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        RDFDataMgr.write(fileOutputStream, model, Lang.RDFXML);
    }
}
