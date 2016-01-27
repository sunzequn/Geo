package com.sunzequn.geo.data.dbpedia;

import com.sunzequn.geo.data.utils.MyStringUtils;
import org.apache.jena.rdf.listeners.ObjectListener;
import org.apache.jena.rdf.model.*;

import java.io.InputStream;

/**
 * Created by Sloriac on 16/1/27.
 */
public class OntologyHandler {

    public static void main(String[] args) {
        String file = "Data/src/main/resources/data/dbpedia/dbpedia_2015-04.nt";
        Model model = ModelFactory.createDefaultModel();
        model.read(file);
        StmtIterator iter = model.listStatements();

        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode object = stmt.getObject();

            if (MyStringUtils.containsChinese(subject.toString()))
                System.out.println("subject:  " + subject.toString());
            if (MyStringUtils.containsChinese(predicate.toString()))
                System.out.println("predicate:  " + predicate.toString());
            if (MyStringUtils.containsChinese(object.toString()))
                System.out.println("object:  " + object.toString());
        }

    }
}
