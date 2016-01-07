package com.sunzequn.geo.data.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sloriac on 15/12/20.
 */
public class ReadUtil {

    private String file;
    private FileInputStream fileInputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;

    public ReadUtil() {
    }

    public ReadUtil(String file) {
        this.file = file;
        try {
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<String> readByLine() {
        List<String> strings = new ArrayList<>();
        String string = "";
        try {
            while ((string = bufferedReader.readLine()) != null) {
                strings.add(string.trim());
            }
        } catch (IOException e) {
            close();
            e.printStackTrace();
        }
        close();
        return strings;
    }

    public void close() {
        try {
            fileInputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
