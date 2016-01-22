package com.sunzequn.geo.data.crawler.application.climate.query;

import com.sunzequn.geo.data.utils.ReadUtils;
import com.sunzequn.geo.data.utils.MyStringUtils;
import com.sunzequn.geo.data.utils.TimeUtils;
import com.sunzequn.geo.data.utils.WriteUtils;
import org.junit.Test;

import java.util.*;

/**
 * Created by Sloriac on 15/12/20.
 */

public class Main {

    private static final String CITY_PATH = "Data/src/main/resources/city/city_mathmatical.txt";
    private static final String CITY_CLIMATE_PATH = "Data/src/main/resources/city/city_mathmatical_climate.txt";
    private static final String CITY_NO_CLIMATE_PATH = "Data/src/main/resources/city/city_mathmatical_no_climate.txt";
    private static final int THREAD_NUM = 2;
    private static LinkedList<City> cities = new LinkedList<>();
    private static WriteUtils writeClimate = new WriteUtils(CITY_CLIMATE_PATH, true);
    private static WriteUtils writeNoClimate = new WriteUtils(CITY_NO_CLIMATE_PATH, true);
    private static int num = 0;

    public static void main(String[] args) {
        readCity();
        System.out.println(cities.size());
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(() -> {
                Parser parser = new Parser();
                while (true) {
                    City city = getCity();
                    if (city != null) {
                        CityWithClimate cityWithClimate = parser.parse(city);
                        if (cityWithClimate != null) {
                            writeClimate(cityWithClimate);
                        } else {
                            writeNoClimate(city);
                        }
                    } else {
                        writeClimate.close();
                        writeNoClimate.close();
                        return;
                    }
                }
            }, "thread" + i).start();
        }
    }

    public static synchronized City getCity() {
        if (cities.size() > 0) {
            num += 1;
            if (num % 500 == 0) {
                writeClimate.flush();
                writeNoClimate.flush();
                System.out.println("count: " + num);
            }
            return cities.removeFirst();
        }
        System.out.println("count: " + num);
        System.out.println("---- Over! ----");
        return null;
    }

    public static synchronized void writeClimate(CityWithClimate city) {
        writeClimate.write(city.toString());
    }

    public static synchronized void writeNoClimate(City city) {
        writeNoClimate.write(city.toString());
    }

    public static void readCity() {
        ReadUtils readUtils = new ReadUtils("Data/src/main/resources/city/city.txt");
        List<String> strings = readUtils.readByLine();
        readUtils.close();
        for (String string : strings) {
            List<String> data = MyStringUtils.split(string, ",");
            City city = new City(data.get(0), data.get(1), data.get(2));
            cities.addLast(city);
        }
    }

    @Test
    public void pullAgain() {
        ReadUtils readUtils1 = new ReadUtils("src/main/resources/city/city_mathmatical.txt");
        List<String> strings1 = readUtils1.readByLine();
        readUtils1.close();

        ReadUtils readUtils2 = new ReadUtils("src/main/resources/city/city_mathmatical_climate.txt");
        List<String> strings2 = readUtils2.readByLine();
        readUtils2.close();

        ReadUtils readUtils3 = new ReadUtils("src/main/resources/city/city_mathmatical_no_climate.txt");
        List<String> strings3 = readUtils3.readByLine();
        readUtils3.close();

        System.out.println(strings1.size());
        System.out.println(strings2.size());
        System.out.println(strings3.size());

        for (String ss : strings2) {
            List<String> sss = MyStringUtils.split(ss, ",");
            String ssss = sss.get(0) + "," + sss.get(1) + "," + sss.get(2);
            strings3.add(ssss);
        }

        System.out.println(strings3.size());

        Set<String> set1 = new HashSet<>(strings1);
        Set<String> set3 = new HashSet<>(strings3);

        System.out.println(set1.size());
        System.out.println(set3.size());

        set1.removeAll(set3);
        System.out.println(set1.size());

        List<String> temp = new ArrayList<>(set1);
        System.out.println(temp.size());

        WriteUtils writeUtils = new WriteUtils("src/main/resources/city/city.txt", false);
        for (String t : temp) {
            writeUtils.write(t);
        }
        writeUtils.close();
    }


}
