package com.sunzequn.geo.data.utils;

import java.io.*;

/**
 * Created by Sloriac on 15/12/20.
 */
public class WriteUtils {

    private String file;
    private boolean append;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;

    private WriteUtils() {
    }

    public WriteUtils(String file, boolean append) {
        this.file = file;
        this.append = append;
        try {
            fileWriter = new FileWriter(file, append);
            bufferedWriter = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String content) {
        try {
            bufferedWriter.write(content);
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            flush();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void flush() {
        try {
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
