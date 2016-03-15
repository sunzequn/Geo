package com.sunzequn.geo.data.dbpedia;

import com.sunzequn.geo.data.utils.StringUtils;
import com.sunzequn.geo.data.utils.TimeUtils;
import com.sunzequn.geo.data.utils.WriteUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sloriac on 16/3/15.
 * <p>
 * 得到Bbpedia中文的namespace
 */
public class NameSpaceHandler {

    public static void main(String[] args) throws IOException {

        TimeUtils timeUtils = new TimeUtils();
        timeUtils.start();
        hanlder();
        timeUtils.end();
        timeUtils.print();
    }

    private static void hanlder() throws IOException {
        String dir = "Data/src/main/resources/data/dbpedia/old";
        String namespaceFile = "Data/src/main/resources/data/dbpedia/namespace";
        WriteUtils nsWrite = new WriteUtils(namespaceFile, true);
        File root = new File(dir);
        File[] files = root.listFiles();
        Set<String> namespaceSet = new HashSet<>();
        for (File file : files) {
            if (file.getName().contains("DS_Store")) {
                continue;
            }
            System.out.println(file.getName());
            LineIterator it = FileUtils.lineIterator(file, "UTF-8");
            String line = null;
            while (it.hasNext()) {
                try {
                    line = it.nextLine();
                    line = line.trim();
                    //不是注释
                    if (!line.startsWith("#")) {
                        Model model = ModelFactory.createDefaultModel();
                        InputStream inputStream = StringUtils.string2InputStream(line);
                        model.read(inputStream, null, "N-TRIPLES");
                        StmtIterator iter = model.listStatements();
                        while (iter.hasNext()) {
                            Statement stmt = iter.nextStatement();
                            Resource subject = stmt.getSubject();
                            Property predicate = stmt.getPredicate();
                            namespaceSet.add(subject.getNameSpace());
                            namespaceSet.add(predicate.getNameSpace());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
            LineIterator.closeQuietly(it);
        }

        for (String ns : namespaceSet) {
            nsWrite.write(ns);
        }
        nsWrite.close();
    }

}
