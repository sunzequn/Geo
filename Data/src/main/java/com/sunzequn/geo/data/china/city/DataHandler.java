package com.sunzequn.geo.data.china.city;

import com.sunzequn.geo.data.utils.MyStringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Sloriac on 15/12/30.
 */
public class DataHandler {

    private static final String CHINA_CITY_FILE = "Data/src/main/resources/data/china/china_city";

    public static void main(String[] args) {
        readData();
    }

    private static void readData() {
        File file = new File(CHINA_CITY_FILE);
        CityDao cityDao = new CityDao();
        int num = 0;
        LineIterator it;
        try {
            it = FileUtils.lineIterator(file, "UTF-8");
            while (it.hasNext()) {
                String line = it.nextLine();
                List<String> columns = MyStringUtils.split(line, "\t");
                ChinaCity chinaCity = new ChinaCity(columns);
                if (!org.apache.commons.lang3.StringUtils.isEmpty(chinaCity.getName())) {
                    num++;
                    cityDao.save(chinaCity);
                }
            }
            LineIterator.closeQuietly(it);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("the number of data : " + num);
    }
}
