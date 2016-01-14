package com.sunzequn.geo.data.Jena;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Sloriac on 16/1/14.
 */
public class Trig {

    private static final String TRIG = "Data/src/main/resources/data/sw/test.trig";
    private static final String EX = "Data/src/main/resources/data/sw/ex.trig";
    private static final String NT = "Data/src/main/resources/data/sw/test.nt";

    public static void main(String[] args) throws FileNotFoundException {
        Model model = ModelFactory.createDefaultModel();
        model.read(TRIG);
        System.out.println(model.isEmpty());

        FileOutputStream fileOutputStream = new FileOutputStream(NT);
        RDFDataMgr.write(fileOutputStream, model, Lang.NT);

    }
}
