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

    public Model getModel(String rdfFile, String type) {
        Model model = ModelFactory.createDefaultModel();
        model.read(rdfFile, type);
        return model;
    }

    public List<String> getObject(String rdf, String prop, String type) {

        Model model = ModelFactory.createDefaultModel();
        model.read(StringUtils.string2InputStream(rdf), type);
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

    public void print(String rdfFile, String type) {
        Model model = ModelFactory.createDefaultModel();
        model.read(rdfFile, type);
        StmtIterator iter = model.listStatements();
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode object = stmt.getObject();

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

}
