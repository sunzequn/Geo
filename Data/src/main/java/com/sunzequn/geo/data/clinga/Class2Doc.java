package com.sunzequn.geo.data.clinga;

import com.sunzequn.geo.data.utils.StringUtils;
import com.sunzequn.geo.data.utils.WriteUtils;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
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
public class Class2Doc {

    private static final String ONTO = "Data/src/main/resources/clinga/clinga_ontology.owl";
    private static final String FILE = "Data/src/main/resources/clinga/doc.md";
    private static WriteUtils writeUtils = new WriteUtils(FILE, false);

    public static void main(String[] args) throws FileNotFoundException {
        handle();
    }

    private static void handle() throws FileNotFoundException {
        OntModel ont = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        ont.read(new FileInputStream(ONTO), "");
//        getDominOf(ont, null);
//        System.out.println(ont.size());
        Iterator i = ont.listClasses();
        int num = 0;
        writeUtils.write("####<span id = \"anchor_Class\">Class</span>");
        while (i.hasNext()) {
            OntClass ontClass = (OntClass) i.next();
            String string = "**Class:** clinga:" + ontClass.getLocalName() + "\n";
            if (ontClass.getComment("EN") != null)
                string += append("*", ontClass.getComment("EN") + "*");
            string += appendBlod("URI: ", ontClass.getURI());
//            string += appendBlod("type: ", "owl:" + ontClass.getRDFType().getLocalName());
            if (ontClass.getSuperClass() != null)
                string += appendBlod("Superclass: ", "clinga:" + ontClass.getSuperClass().getLocalName());
            if (ontClass.listSubClasses().hasNext()) {
                Iterator iterator = ontClass.listSubClasses();
                String subclasses = "";
                while (iterator.hasNext()) {
                    OntClass subclass = (OntClass) iterator.next();
                    subclasses += "clinga:" + subclass.getLocalName() + ", ";
                }
                subclasses = StringUtils.removeEnd(subclasses.trim(), ",");
                string += appendBlod("Subclass: ", subclasses);
            }


            string += appendBlod("Label: ", ontClass.getPropertyValue(RDFS.label).asLiteral().toString());
            string += appendBlod("Comment: ", ontClass.getComment("ZH") + "@ZH");
            string += appendBlod("PrefLabel: ", ontClass.getPropertyValue(SKOS.prefLabel).asLiteral().toString());
            string += appendBlod("AltLabel: ", ontClass.getPropertyValue(SKOS.altLabel).asLiteral().toString());


            List<String> domainOfs = getDominOf(ont, ontClass);
            if (domainOfs != null) {
                String sss = "";
                for (String domainOf : domainOfs) {
                    sss += domainOf + ", ";
                }
                sss = StringUtils.removeEnd(sss.trim(), ",");
                string += appendBlod("Properties include: ", sss);
            }
            List<String> rangeOfs = getRangeOf(ont, ontClass);
            if (rangeOfs != null) {
                String sss = "";
                for (String rangeOf : rangeOfs) {
                    sss += rangeOf + ", ";
                }
                sss = StringUtils.removeEnd(sss.trim(), ",");
                string += appendBlod("Used with: ", sss);
            }

            writeUtils.write(string);
            writeUtils.write("- - -");
            num++;
        }

        System.out.println(num);

        writeUtils.write("####<span id = \"anchor_ObjectProperty\">ObjectProperty</span>");
        i = ont.listObjectProperties();
        num = 0;
        while (i.hasNext()) {
            ObjectProperty objectProperty = (ObjectProperty) i.next();
            String string = "**ObjectProperty:** clinga:" + objectProperty.getLocalName() + "\n";
            string += appendBlod("URI: ", objectProperty.getURI());

            if (objectProperty.getDomain() != null)
                string += appendBlod("Domain:", "clinga:" + objectProperty.getDomain().getLocalName());
            if (objectProperty.getRange() != null)
                string += appendBlod("Range:", "clinga:" + objectProperty.getRange().getLocalName());

            string += appendBlod("Label: ", objectProperty.getPropertyValue(RDFS.label).asLiteral().toString());
            string += appendBlod("PrefLabel: ", objectProperty.getPropertyValue(SKOS.prefLabel).asLiteral().toString());
            if (objectProperty.getPropertyValue(SKOS.altLabel) != null)
                string += appendBlod("AltLabel: ", objectProperty.getPropertyValue(SKOS.altLabel).asLiteral().toString());

            num++;
            writeUtils.write(string);
            writeUtils.write("- - -");
        }

        System.out.println(num);

        writeUtils.write("####<span id = \"anchor_DatatypeProperty\">DatatypeProperty</span>");
        i = ont.listDatatypeProperties();
        num = 0;
        while (i.hasNext()) {
            DatatypeProperty datatypeProperty = (DatatypeProperty) i.next();
            String string = "**DatatypeProperty:** clinga:" + datatypeProperty.getLocalName() + "\n";
            string += appendBlod("URI: ", datatypeProperty.getURI());

            if (datatypeProperty.getDomain() != null)
                string += appendBlod("Domain:", "clinga:" + datatypeProperty.getDomain().getLocalName());
            if (datatypeProperty.getRange() != null)
                string += appendBlod("Range:", "xsd:" + datatypeProperty.getRange().getLocalName());

            string += appendBlod("Label: ", datatypeProperty.getPropertyValue(RDFS.label).asLiteral().toString());
            string += appendBlod("PrefLabel: ", datatypeProperty.getPropertyValue(SKOS.prefLabel).asLiteral().toString());
            if (datatypeProperty.getPropertyValue(SKOS.altLabel) != null)
                string += appendBlod("AltLabel: ", datatypeProperty.getPropertyValue(SKOS.altLabel).asLiteral().toString());

            num++;
            writeUtils.write(string);
            writeUtils.write("- - -");
        }
        System.out.println(num);

        writeUtils.close();

    }

    private static String appendBlod(String key, String value) {
        return "**" + key + "**" + value + "\n";
    }

    private static String append(String key, String value) {
        return "" + key + value + "\n";
    }

    private static List<String> getDominOf(OntModel model, OntClass ontClass) {
        Iterator iterator = model.listDatatypeProperties();
        List<String> res = new ArrayList<>();
        int num = 0;
        while (iterator.hasNext()) {
            num++;
            DatatypeProperty datatypeProperty = (DatatypeProperty) iterator.next();
            if (datatypeProperty.getDomain() == ontClass) {
                res.add("clinga:" + datatypeProperty.getLocalName());
            }
        }
//        System.out.println("Object"+num);
        iterator = model.listObjectProperties();
        num = 0;
        while (iterator.hasNext()) {
            num++;
            ObjectProperty objectProperty = (ObjectProperty) iterator.next();
            if (objectProperty.getDomain().getURI().equals(ontClass.getURI())) {
                res.add("clinga:" + objectProperty.getLocalName());
            }
        }
//        System.out.println("Data"+num);
        if (res.size() > 0) {
            return res;
        }
        return null;
    }

    private static List<String> getRangeOf(OntModel model, OntClass ontClass) {
        Iterator iterator = model.listObjectProperties();
        List<String> res = new ArrayList<>();
        while (iterator.hasNext()) {
            ObjectProperty objectProperty = (ObjectProperty) iterator.next();
            if (objectProperty.getRange() != null && objectProperty.getRange().getURI().equals(ontClass.getURI())) {
                res.add("clinga:" + objectProperty.getLocalName());
            }
        }
//        System.out.println("Data"+num);
        if (res.size() > 0) {
            return res;
        }
        return null;
    }


}
