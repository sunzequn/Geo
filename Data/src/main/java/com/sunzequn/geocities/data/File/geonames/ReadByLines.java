package com.sunzequn.geocities.data.File.geonames;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;

/**
 * Created by Sloriac on 15/12/27.
 */
public class ReadByLines {

    private static final String GEONAMES_FILE = "Data/src/main/resources/data/geonames/allCountries.txt";

    public static void main(String[] args) {
        File file = new File(GEONAMES_FILE);
        int num = 0;
        LineIterator it;
        try {
            it = FileUtils.lineIterator(file, "UTF-8");
            while (it.hasNext()) {
                String line = it.nextLine();
                if (line.contains("北京"))
                    System.out.println(line);
                num++;
                if (num % 100000 == 0)
                    System.out.println(num);
            }
            System.out.println(num);
            LineIterator.closeQuietly(it);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
