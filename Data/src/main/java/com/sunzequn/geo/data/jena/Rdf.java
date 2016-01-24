package com.sunzequn.geo.data.jena;

import com.sunzequn.geo.data.utils.StringUtils;
import org.apache.jena.rdf.model.*;

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
        model.write(out);
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

}
