package com.sunzequn.geo.data.clinga;

import com.sunzequn.geo.data.utils.StringUtils;
import com.sunzequn.geo.data.utils.WriteUtils;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.SKOS;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sunzequn on 2016/7/5.
 */
public class ReadOntology {

    private static final String STRUCT_ONTO = "Data/src/main/resources/clinga/structure_ontology.owl";
    private static final String ONTO = "Data/src/main/resources/clinga/clinga_ontology.owl";
    private static final String FILE = "Data/src/main/resources/clinga/localname_1";
    private static WriteUtils writeUtils = new WriteUtils(FILE, true);

    public static void getLocalName(String file) throws FileNotFoundException {
        OntModel ont = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        ont.read(new FileInputStream(file), "");
        System.out.println(ont.size());
        Iterator i = ont.listClasses();
        int num = 0;
        while (i.hasNext()) {
            OntClass ontClass = (OntClass) i.next();
            System.out.println(ontClass.toString());
            if (ontClass != null)
                writeUtils.write(ontClass.getLocalName());
                num++;
        }

        System.out.println(num);

        writeUtils.write("---- ObjectProperty ----");
        i = ont.listObjectProperties();
        num = 0;
        while (i.hasNext()) {
            ObjectProperty objectProperty = (ObjectProperty) i.next();
            writeUtils.write(objectProperty.getLocalName());
            num++;
        }
        System.out.println(num);

        writeUtils.write("---- DatatypeProperty ----");
        i = ont.listDatatypeProperties();
        num = 0;
        while (i.hasNext()) {
            DatatypeProperty datatypeProperty = (DatatypeProperty) i.next();
            writeUtils.write(datatypeProperty.getLocalName());
            num++;
        }
        System.out.println(num);

        writeUtils.close();
    }

    public static void getLeaf(String file) throws FileNotFoundException {
        OntModel ont = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        ont.read(new FileInputStream(file), "");
        Iterator i = ont.listClasses();
        int n = 0;
        while (i.hasNext()) {
            OntClass ontClass = (OntClass) i.next();
            if (ontClass.listSubClasses().hasNext()) {
                n++;
                Statement statement = ont.getProperty(ontClass, SKOS.prefLabel);
                System.out.println(StringUtils.removeEnd(statement.getObject().toString(), "@zh"));
            }
        }
        System.out.println(n);
    }

    public static void main(String[] args) throws FileNotFoundException {
//        getLeaf(ONTO);
        getLocalName(ONTO);
    }
}
