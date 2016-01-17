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
    private static final String NT = "Data/src/main/resources/data/sw/test.nt";

    public void toNt(String trigFile, String ntFile) {
        Model model = ModelFactory.createDefaultModel();
        model.read(trigFile);
        if (model.isEmpty())
            return;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(ntFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        RDFDataMgr.write(fileOutputStream, model, Lang.NT);
    }

    public static void main(String[] args) {

        Trig trig = new Trig();
        trig.toNt(TRIG, NT);

    }
}
