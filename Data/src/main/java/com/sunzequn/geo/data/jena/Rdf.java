package com.sunzequn.geo.data.jena;

import com.sunzequn.geo.data.utils.StringUtils;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloriac on 16/1/21.
 */
public class Rdf {

    public List<String> getObject(String rdf, String prop) {

        Model model = ModelFactory.createDefaultModel();
        model.read(StringUtils.string2InputStream(rdf), null);
        Property property = model.createProperty(prop);
        NodeIterator nodeIterator = model.listObjectsOfProperty(property);
        List<String> strings = new ArrayList<>();
        if (nodeIterator.hasNext()) {
            strings.add(nodeIterator.nextNode().toString());
        }
        if (strings.size() > 0)
            return strings;
        return null;
    }

    public void toNt(InputStream in, OutputStream out) {
        Model model = ModelFactory.createDefaultModel();
        model.read(in, null);
        RDFDataMgr.write(out, model, Lang.NT);
    }


    public boolean validate(String rdf) {
        try {
            Model model = ModelFactory.createDefaultModel();
            model.read(StringUtils.string2InputStream(rdf), null);
            if (model.isEmpty()) {
                System.out.println("------The RDF file is empty. ----");
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEmpty(String rdf) {
        try {
            Model model = ModelFactory.createDefaultModel();
            model.read(StringUtils.string2InputStream(rdf), null);
            return model.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public void print(String rdf) {
        Model model = ModelFactory.createDefaultModel();
        model.read(StringUtils.string2InputStream(rdf), null);
        // list the statements in the Model
        StmtIterator iter = model.listStatements();

        // print out the predicate, subject and object of each statement
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();  // get next statement
            Resource subject = stmt.getSubject();     // get the subject
            Property predicate = stmt.getPredicate();   // get the predicate
            RDFNode object = stmt.getObject();      // get the object

            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }

            System.out.println(" .");
        }

    }

    public static void main(String[] args) {
        Rdf rdf = new Rdf();
        String s = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?> <rdf:RDF xmlns:cc=\"http://creativecommons.org/ns#\" xmlns:dcterms=\"http://purl.org/dc/terms/\" xmlns:foaf=\"http://xmlns.com/foaf/0.1/\" xmlns:gn=\"http://www.geonames.org/ontology#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:wgs84_pos=\"http://www.w3.org/2003/01/geo/wgs84_pos#\"> <gn:Feature rdf:about=\"http://sws.geonames.org/10976302/\"> <rdfs:isDefinedBy rdf:resource=\"http://sws.geonames.org/10976302/about.rdf\"/> <gn:name>Rūdkhāneh-ye Faşlī-ye Chārīmīlī</gn:name> <gn:nearby rdf:resource=\"http://sws.geonames.org/3/\"/> </gn:Feature> <gn:Feature rdf:about=\"http://sws.geonames.org/167/\"> <rdfs:isDefinedBy rdf:resource=\"http://sws.geonames.org/167/about.rdf\"/> <gn:name>Gūr Sharān</gn:name> <gn:nearby rdf:resource=\"http://sws.geonames.org/3/\"/> </gn:Feature> <gn:Feature rdf:about=\"http://sws.geonames.org/84/\"> <rdfs:isDefinedBy rdf:resource=\"http://sws.geonames.org/84/about.rdf\"/> <gn:name>Kūh-e Nesār</gn:name> <gn:nearby rdf:resource=\"http://sws.geonames.org/3/\"/> </gn:Feature> <gn:Feature rdf:about=\"http://sws.geonames.org/10976287/\"> <rdfs:isDefinedBy rdf:resource=\"http://sws.geonames.org/10976287/about.rdf\"/> <gn:name>Kūh-e Pesar Sorkhā</gn:name> <gn:nearby rdf:resource=\"http://sws.geonames.org/3/\"/> </gn:Feature> <gn:Feature rdf:about=\"http://sws.geonames.org/10976284/\"> <rdfs:isDefinedBy rdf:resource=\"http://sws.geonames.org/10976284/about.rdf\"/> <gn:name>Rūdkhāneh-ye Faşlī-ye Āb Mārū</gn:name> <gn:nearby rdf:resource=\"http://sws.geonames.org/3/\"/> </gn:Feature> <gn:Feature rdf:about=\"http://sws.geonames.org/10976298/\"> <rdfs:isDefinedBy rdf:resource=\"http://sws.geonames.org/10976298/about.rdf\"/> <gn:name>Rūdkhāneh-ye Faşlī-ye Āb</gn:name> <gn:nearby rdf:resource=\"http://sws.geonames.org/3/\"/> </gn:Feature> <gn:Feature rdf:about=\"http://sws.geonames.org/6660491/\"> <rdfs:isDefinedBy rdf:resource=\"http://sws.geonames.org/6660491/about.rdf\"/> <gn:name>Galeh Bīleh</gn:name> <gn:nearby rdf:resource=\"http://sws.geonames.org/3/\"/> </gn:Feature> <gn:Feature rdf:about=\"http://sws.geonames.org/36547/\"> <rdfs:isDefinedBy rdf:resource=\"http://sws.geonames.org/36547/about.rdf\"/> <gn:name>Galeh Bīleh</gn:name> <gn:nearby rdf:resource=\"http://sws.geonames.org/3/\"/> </gn:Feature> <gn:Feature rdf:about=\"http://sws.geonames.org/10976288/\"> <rdfs:isDefinedBy rdf:resource=\"http://sws.geonames.org/10976288/about.rdf\"/> <gn:name>Darreh-ye Chahār Bīsheh</gn:name> <gn:nearby rdf:resource=\"http://sws.geonames.org/3/\"/> </gn:Feature> </rdf:RDF>";
        System.out.println(!rdf.isEmpty(s));
    }

}
