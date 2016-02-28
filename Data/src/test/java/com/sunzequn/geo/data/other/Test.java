package com.sunzequn.geo.data.other;

import com.sunzequn.geo.data.utils.MyStringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Sloriac on 16/1/26.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        String name = "Data/src/main/resources/data/dbpedia/test.nt";
        File file = new File(name);
        LineIterator it = FileUtils.lineIterator(file, "UTF-8");
        String line = null;
        while (it.hasNext()) {
            line = it.nextLine().trim();
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
            System.out.println(newLine);
        }
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
