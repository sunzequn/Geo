package com.sunzequn.geo.data.baike.jena;

import com.sunzequn.geo.data.baike.bean.ExtendedType;
import com.sunzequn.geo.data.baike.bean.InfoBoxTemplate;
import com.sunzequn.geo.data.baike.bean.TypeLink;
import com.sunzequn.geo.data.baike.dao.ExtendedTypeDao;
import com.sunzequn.geo.data.baike.dao.InfoBoxTemplateDao;
import com.sunzequn.geo.data.baike.dao.TypeLinkDao;
import com.sunzequn.geo.data.utils.StringUtils;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.SKOS;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/3.
 */
public class OntologyGenerator {

    private static final String FILE = "D:/DevSpace/github/Geo/Data/src/main/resources/baike/ontology.owl";
    private static final String DBO = "http://dbpedia.org/ontology/";
    private static final String GEO = "http://www.geonames.org/ontology#";
    private static final String GEO_F = "http://www.geonames.org/ontology#featureCode";
    //百度百科uri前缀
    private static final String BAIDU = "http://ws.nju.edu.cn/geoscholar/baidu/";
    private static final String BO = BAIDU + "ontology/";
    private static final String BP = BAIDU + "property/";
    private static OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

    public static void main(String[] args) {
        generateOntology();
        generateRelation();
        toFile(model);
    }

    /**
     * 处理link关系
     */
    private static void generateRelation() {
        TypeLinkDao dao = new TypeLinkDao();
        List<TypeLink> links = dao.getAll();
        for (TypeLink link : links) {
            if (link.getEntity().trim().startsWith("dbo:")) {
                handleDboLink(link);
            } else if (link.getEntity().trim().startsWith("geo:")) {
                handleGeoLink(link);
            } else {
                System.out.println("link error");
            }
        }
    }

    private static String getDbo(String uri) {
        return org.apache.commons.lang3.StringUtils.removeStart(uri.trim(), "dbo:");
    }

    private static String getGeo(String uri) {
        return org.apache.commons.lang3.StringUtils.removeStart(uri.trim(), "geo:");
    }

    private static void handleDboLink(TypeLink link) {
        OntClass ontClass = model.getOntClass(BO + link.getType().trim());
        if (ontClass == null) {
            System.out.println("no class : " + link.getType());
        } else {
            String relation = link.getRelation();
            String dbos = link.getEntity();
            if (dbos.contains("/")) {
                String[] dboNames = org.apache.commons.lang3.StringUtils.split(dbos, "/");
                for (String dboName : dboNames) {
                    Resource dbo = model.createResource(DBO + getDbo(dboName));
                    handleDbo(dbo, ontClass, relation);
                }
            } else {
                Resource dbo = model.createResource(DBO + getDbo(dbos));
                handleDbo(dbo, ontClass, relation);
            }
        }
    }

    private static void handleDbo(Resource resource, OntClass ontClass, String relation) {
        if (relation.contains("exactMatch")) {
            ontClass.addEquivalentClass(resource);
        } else if (relation.contains("broadMatch")) {
            ontClass.addProperty(SKOS.broadMatch, resource);
        } else {
            ontClass.addProperty(SKOS.narrowMatch, resource);
        }
    }

    private static void handleGeo(OntClass ontClass, String relation, String geos) {
        if (relation.contains("broadMatch")) {
            System.out.println("broadMatch");
        } else {
            //narrowMatch
            if (geos.contains("/")) {
                String[] geoCodes = org.apache.commons.lang3.StringUtils.split(geos, "/");
                RDFNode[] rdfNodes = new RDFNode[geoCodes.length];
                for (int i = 0; i < geoCodes.length; i++) {
                    Restriction restriction = model.createRestriction(model.createProperty(GEO_F));
                    restriction.addProperty(OWL.hasValue, model.createResource(GEO + getGeo(geoCodes[i])));
                    rdfNodes[i] = restriction;
                }
                RDFList rdfList = model.createList(rdfNodes);
                UnionClass unionClass = model.createUnionClass(null, rdfList);
                ontClass.addEquivalentClass(unionClass);
            }
            //narrowMatch 或者 exactMatch
            else {
                System.out.println(geos);
                Restriction restriction = model.createRestriction(model.createProperty(GEO_F));
                restriction.addProperty(OWL.hasValue, model.createResource(GEO + getGeo(geos)));
                RDFList rdfList = model.createList(new RDFNode[]{restriction});
                UnionClass unionClass = model.createUnionClass(null, rdfList);
                ontClass.addEquivalentClass(unionClass);
            }
        }
    }

    private static void handleGeoLink(TypeLink link) {
        TypeLinkDao dao = new TypeLinkDao();
        OntClass ontClass = model.getOntClass(BO + link.getType().trim());
        if (ontClass == null) {
            System.out.println("no class : " + link.getType());
        } else {
            String relation = link.getRelation();
            String geos = link.getEntity();
            handleGeo(ontClass, relation, geos);
        }
    }

    private static void generateOntology() {
        generateOriginOntology();
        generateExtendedType();
    }

    private static void generateOriginOntology() {
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
            handleTemplate(model, templates, template);
        }
        for (InfoBoxTemplate template : templates2) {
            handleTemplate(model, templates, template);
        }
        for (InfoBoxTemplate template : templates3) {
            handleTemplate(model, templates, template);
        }
    }

    private static void generateExtendedType() {
        ExtendedTypeDao dao = new ExtendedTypeDao();
        List<ExtendedType> types = dao.getAll();
        for (ExtendedType type : types) {
            type.initAltLabels();
            String ns = BO + type.getTypeName();
            OntClass ontClass = model.getOntClass(ns);
            if (ontClass == null) {
                addClass(model, type, types);
            }
        }
    }

    private static OntClass addClass(OntModel model, ExtendedType type, List<ExtendedType> types) {
        OntClass ontClass = model.createClass(BO + type.getTypeName().trim());
        //添加父类信息
        if (!addSupClass(model, ontClass, type, types)) {
            System.out.println("错误");
        }

        //处理comment
        if (!StringUtils.isNullOrEmpty(type.getComment())) {
            Literal comment = model.createLiteral(type.getComment());
            ontClass.addLabel(comment);
        }
        //设置preflabel
        ontClass.addProperty(SKOS.prefLabel, model.createLiteral(type.getTypeName(), MultiLang.ZH));
        //处理中文altlabel
        if (type.getAltLabels() != null) {
            for (String label : type.getAltLabels())
                ontClass.addProperty(SKOS.altLabel, model.createLiteral(label, MultiLang.ZH));
        }
        //处理英文altlabel
        if (!StringUtils.isNullOrEmpty(type.getEntype())) {
            ontClass.addProperty(SKOS.altLabel, model.createLiteral(type.getEntype(), MultiLang.EN));
        }
        return ontClass;
    }

    private static boolean addSupClass(OntModel model, OntClass ontClass, ExtendedType type, List<ExtendedType> types) {
        OntClass template = model.getOntClass(BO + type.getSuperType());
        if (template != null) {
            ontClass.setSuperClass(template);
            return true;
        } else {
            for (ExtendedType type1 : types) {
                if (type1.getTypeName().trim().equals(type.getSuperType().trim())) {
                    ontClass.setSuperClass(addClass(model, type1, types));
                    return true;
                }
            }
        }
        return false;
    }

    private static void handleTemplate(OntModel model, List<InfoBoxTemplate> templates, InfoBoxTemplate template) {
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
            //设置preflabel
            newClass.addProperty(SKOS.prefLabel, model.createLiteral(template.getTitle(), MultiLang.ZH));
            //设置altlabel
            if (!StringUtils.isNullOrEmpty(template.getEntitle())) {
                newClass.addProperty(SKOS.altLabel, model.createLiteral(template.getEntitle(), MultiLang.EN));
            }
            //设置commet
            if (!StringUtils.isNullOrEmpty(template.getComment())) {
                newClass.addComment(template.getComment(), MultiLang.ZH);
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
