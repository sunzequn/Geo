package com.sunzequn.geo.data.dbpedia;

import com.sunzequn.geo.data.exception.RdfException;
import com.sunzequn.geo.data.utils.StringUtils;
import com.sunzequn.geo.data.utils.TimeUtils;
import com.sunzequn.geo.data.utils.WriteUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import java.io.*;
import java.net.URLEncoder;

/**
 * Created by Sloriac on 16/1/27.
 */
public class ZhString {

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

                        line = org.apache.commons.lang3.StringUtils.removeEnd(line, ".");
                        String[] strings = org.apache.commons.lang3.StringUtils.split(line, ">");
                        String subject = strings[0].trim() + ">";
                        String predicate = strings[1].trim() + ">";

                        String newLine = "";
                        newLine += encode(subject) + " ";
                        newLine += encode(predicate) + " ";

                        String object = "";
                        for (int i = 2; i < strings.length; i++) {
                            object += strings[i];
                        }
                        if (object.trim().startsWith("\"")) {
                            newLine += object.trim();
                            if (object.trim().contains("^^<")) {
                                newLine += ">";
                            }
                        } else {
                            newLine += encode(object.trim() + ">");
                        }
                        newLine += " .";
                        Model model = lineHandler(newLine);
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

    private static Model lineHandler(String line) throws RdfException, UnsupportedEncodingException {
        Model model = ModelFactory.createDefaultModel();
        InputStream inputStream = StringUtils.string2InputStream(line);
        model.read(inputStream, null, "N-TRIPLES");
        return model;
    }

    private static boolean isLiteral(Model model) {
        StmtIterator iter = model.listStatements();
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            RDFNode object = stmt.getObject();
            if (object.isLiteral()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private static String encode(String string) throws UnsupportedEncodingException {
        string = string.replace(" ", "_");
        String prefix = "<http://";
        if (string.startsWith(prefix)) {
            string = org.apache.commons.lang3.StringUtils.removeStart(string, prefix);
        } else {
            prefix = "";
        }

        char[] chars = string.toCharArray();
        String temp = "";
        int i = 0;
        while (i < string.length()) {
            char ch = chars[i];
            if (ch == ':') {
                if (chars[i - 1] == 'p' && chars[i - 2] == 't' && chars[i - 3] == 't') {
                    temp += ch;
                } else {
                    temp += URLEncoder.encode(String.valueOf(ch), "utf-8");
                }
                i++;
            } else if (ch == '\\' && chars[i + 1] == 'u') {
                String uni = String.valueOf(ch) + "u";
                int j = i + 2;
                while (Character.isDigit(chars[j]) || Character.isLetter(chars[j])) {
                    uni += chars[j];
                    j++;
                }
                i = j;
                uni = StringEscapeUtils.unescapeJava(uni);
                uni = URLEncoder.encode(uni, "utf-8");
                //处理空格,变成下划线
                if (uni.equals(" ")) {
                    uni = "_";
                }
                temp += uni;
            } else {
                temp += ch;
                i++;
            }
        }
        return prefix + temp.trim();
    }
}
