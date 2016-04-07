package com.sunzequn.geo.data.baike.jena;

import com.sunzequn.geo.data.baike.bean.TypeLink;
import com.sunzequn.geo.data.baike.dao.TypeLinkDao;
import com.sunzequn.geo.data.utils.ReadUtils;
import com.sunzequn.geo.data.utils.WriteUtils;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFList;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.SKOS;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/6.
 */
public class MappingGenerator {

    private static final String MAPPING_FILE = "D:/DevSpace/github/Geo/Data/src/main/resources/baike/mapping.owl";
    private static final String DBO = "http://dbpedia.org/ontology/";
    private static final String GEO = "http://www.geonames.org/ontology#";
    private static final String GEO_F = "http://www.geonames.org/ontology#featureCode";
    //百度百科uri前缀
    private static final String BAIDU = "http://ws.nju.edu.cn/geoscholar/baidu/";
    private static final String BO = BAIDU + "ontology/";
    private static final String BP = BAIDU + "property/";

    public static void main(String[] args) {
        generateRelation();
        reformatRDF(MAPPING_FILE);
    }


    /**
     * 处理link关系
     */
    private static void generateRelation() {
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        TypeLinkDao dao = new TypeLinkDao();
        List<TypeLink> links = dao.getAll();
        for (TypeLink link : links) {
            if (link.getEntity().trim().startsWith("dbo:")) {
                handleDboLink(link, model);
            } else if (link.getEntity().trim().startsWith("geo:")) {
                handleGeoLink(link, model);
            } else {
                System.out.println("link error");
            }
        }
        toFile(model, MAPPING_FILE);
    }

    private static void handleDboLink(TypeLink link, OntModel model) {
        String ns = BO + link.getType().trim();
        OntClass ontClass = model.getOntClass(ns);
        if (ontClass == null) {
            ontClass = model.createClass(ns);
        }
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

    private static void handleDbo(Resource resource, OntClass ontClass, String relation) {
        if (relation.contains("exactMatch")) {
            ontClass.addEquivalentClass(resource);
        } else if (relation.contains("broadMatch")) {
            ontClass.addProperty(SKOS.broadMatch, resource);
        } else {
            ontClass.addProperty(SKOS.narrowMatch, resource);
        }
    }

    private static void handleGeo(OntClass ontClass, String relation, String geos, OntModel model) {
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
                ontClass.addEquivalentClass(restriction);
            }
        }
    }

    private static void handleGeoLink(TypeLink link, OntModel model) {
        String ns = BO + link.getType().trim();
        OntClass ontClass = model.getOntClass(ns);
        if (ontClass == null) {
            ontClass = model.createClass(ns);
        }

        String relation = link.getRelation();
        String geos = link.getEntity();
        handleGeo(ontClass, relation, geos, model);

    }

    private static String getDbo(String uri) {
        return org.apache.commons.lang3.StringUtils.removeStart(uri.trim(), "dbo:");
    }

    private static String getGeo(String uri) {
        return org.apache.commons.lang3.StringUtils.removeStart(uri.trim(), "geo:");
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

    private static void reformatRDF(String file) {
        ReadUtils readUtils = new ReadUtils(file);
        List<String> lines = readUtils.readByLine();
        readUtils.close();
        if (lines.size() > 0) {
            WriteUtils writeUtils = new WriteUtils(file, false);
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).contains("owl:hasValue")) {
                    writeUtils.write(lines.get(i + 1));
                    writeUtils.write(lines.get(i));
                    i++;
                } else {
                    writeUtils.write(lines.get(i));
                }
            }
            writeUtils.close();
        }
    }
}
