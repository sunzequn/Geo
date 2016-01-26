package com.sunzequn.geo.data.dbpedia;

import com.sunzequn.geo.data.utils.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * Created by Sloriac on 16/1/26.
 */
public class ZhEncoder {

    public static void main(String[] args) throws IOException {
        hanlder();
    }

    private static void hanlder() throws IOException {
        String dir = "Data/src/main/resources/data/dbpedia/old";
        String newDir = "Data/src/main/resources/data/dbpedia/new/";
        File root = new File(dir);
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.getName().contains("DS_Store")) {
                continue;
            }
            LineIterator it = FileUtils.lineIterator(file, "UTF-8");
            FileOutputStream fileOutputStream = new FileOutputStream(newDir + file.getName(), true);
            String line;
            try {
                while (it.hasNext()) {
                    line = it.nextLine();
                    line = line.trim();
                    //不是注释
                    if (!line.startsWith("#")) {
                        Model model = lineHandler(line);
                        RDFDataMgr.write(fileOutputStream, model, Lang.NT);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                LineIterator.closeQuietly(it);
                fileOutputStream.close();

            }
        }
    }

    private static Model modelHandler(Model model) {
        Model newModel = ModelFactory.createDefaultModel();
        StmtIterator iter = model.listStatements();
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode object = stmt.getObject();

            String subjectNS = subject.getNameSpace();
            String subjectLN = URLEncoder.encode(subject.getLocalName());

            String predicateNS = predicate.getNameSpace();
            String predicateLN = URLEncoder.encode(predicate.getLocalName());

            Resource newSubject = newModel.createResource(subjectNS + subjectLN);
            Property newPredicate = newModel.createProperty(predicateNS, predicateLN);
            if (object.isLiteral()) {
                Literal literal = object.asLiteral();
                newSubject.addProperty(newPredicate, literal);
            } else {
                String objectNS = object.asResource().getNameSpace();
                String objectLN = URLEncoder.encode(object.asResource().getLocalName());
                RDFNode newObject = newModel.createResource(objectNS + objectLN);
                newSubject.addProperty(newPredicate, newObject);
            }
            return newModel;
        }

        return null;
    }

    private static Model lineHandler(String line) {
        Model model = ModelFactory.createDefaultModel();
        InputStream inputStream = StringUtils.string2InputStream(line);
        model.read(inputStream, null, "N-TRIPLES");
        return modelHandler(model);

    }
}
