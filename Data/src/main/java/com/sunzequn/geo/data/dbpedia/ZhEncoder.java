package com.sunzequn.geo.data.dbpedia;

import com.sunzequn.geo.data.exception.RdfException;
import com.sunzequn.geo.data.utils.MyStringUtils;
import com.sunzequn.geo.data.utils.StringUtils;
import com.sunzequn.geo.data.utils.TimeUtils;
import com.sunzequn.geo.data.utils.WriteUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import java.io.*;
import java.net.URLEncoder;

/**
 * Created by Sloriac on 16/1/26.
 */
public class ZhEncoder {

    public static void main(String[] args) throws IOException {

        TimeUtils timeUtils = new TimeUtils();
        timeUtils.start();
        hanlder();
        timeUtils.end();
        timeUtils.print();
    }

    private static void hanlder() throws IOException {
        String dir = "Data/src/main/resources/data/dbpedia/old";
        String newDir = "Data/src/main/resources/data/dbpedia/new/";
        String errorDir = "Data/src/main/resources/data/dbpedia/error/";
        File root = new File(dir);
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.getName().contains("DS_Store")) {
                continue;
            }
            System.out.println(file.getName());
            LineIterator it = FileUtils.lineIterator(file, "UTF-8");
            FileOutputStream fileOutputStream = new FileOutputStream(newDir + file.getName(), true);
            WriteUtils error = new WriteUtils(errorDir + file.getName(), true);
            String line = null;
            while (it.hasNext()) {
                try {
                    line = it.nextLine();
                    line = line.trim();
                    //不是注释
                    if (!line.startsWith("#")) {
                        Model model = lineHandler(line);
                        RDFDataMgr.write(fileOutputStream, model, Lang.NT);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    error.write(line);
                }
            }
            error.close();
            LineIterator.closeQuietly(it);
            fileOutputStream.close();
        }
    }


    private static Model modelHandler(Model model) throws RdfException, UnsupportedEncodingException {
        Model newModel = ModelFactory.createDefaultModel();
        StmtIterator iter = model.listStatements();
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode object = stmt.getObject();

            String[] subjects = nameHandler(subject.getNameSpace(), subject.getLocalName());
            String subjectNS = subjects[0];
            String subjectLN = subjects[1];

            String[] predicates = nameHandler(predicate.getNameSpace(), predicate.getLocalName());
            String predicateNS = predicates[0];
            String predicateLN = predicates[1];

            Resource newSubject = newModel.createResource(subjectNS + subjectLN);
            Property newPredicate = newModel.createProperty(predicateNS, predicateLN);
            if (object.isLiteral()) {
                Literal literal = object.asLiteral();
                newSubject.addProperty(newPredicate, literal);
            } else {
                String[] objects = nameHandler(object.asResource().getNameSpace(), object.asResource().getLocalName());
                String objectNS = objects[0];
                String objectLN = objects[1];
                RDFNode newObject = newModel.createResource(objectNS + objectLN);
                newSubject.addProperty(newPredicate, newObject);
            }
            return newModel;
        }

        return null;
    }

    private static String[] nameHandler(String ns, String ln) throws RdfException, UnsupportedEncodingException {

        String prefix = "http://";
//        if(MyStringUtils.isContainsChinese(ns) || ns.contains(":")){
        ns.replace(" ", "_");
        ns = MyStringUtils.encode(ns);
//            String tempNs = org.apache.commons.lang3.StringUtils.removeStart(ns, prefix);
//            tempNs = MyStringUtils.encode(tempNs, "/");
//            ns = prefix + tempNs;
//        }
//        if (MyStringUtils.isContainsChinese(ln) || ){
        ln.replace(" ", "_");
//            ln = URLEncoder.encode(ln, "UTF-8");
        ln = MyStringUtils.encode(ln);
//        }
        String[] strings = {ns, ln};
        return strings;
    }

    private static Model lineHandler(String line) throws RdfException, UnsupportedEncodingException {
        Model model = ModelFactory.createDefaultModel();
        InputStream inputStream = StringUtils.string2InputStream(line);
        model.read(inputStream, null, "N-TRIPLES");
        return modelHandler(model);
    }
}

