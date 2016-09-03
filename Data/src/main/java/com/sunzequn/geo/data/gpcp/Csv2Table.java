package com.sunzequn.geo.data.gpcp;

import com.sunzequn.geo.data.utils.DoubleUtils;
import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.MyStringUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by sunzequn on 2016/6/26.
 */
public class Csv2Table {

    private static final String FOLDER = "Data/src/main/resources/gpcp/";
    private static PrecipitationDao dao = new PrecipitationDao();

    public static void main(String[] args) throws IOException {
        handler();
//        readCSV(FOLDER + "gpcp_v2.2_psg.1979.csv");
//        System.out.println(longConverter("222.23W"));
//        System.out.println(latConverter("53.75N"));
    }

    private static void handler() throws IOException {
        File folder = new File(FOLDER);
        for (File file : folder.listFiles()) {
            String filePath = FOLDER + file.getName();
            System.out.println(filePath);
            readCSV(filePath);
        }
    }

    private static double longConverter(String longitude) {
        longitude = longitude.substring(0, longitude.length() - 1);
//        System.out.println(longitude);
        double res = Double.valueOf(longitude);
        if (res < 180.0) {
            return res;
        } else {
            return res - 360.0;
        }
    }

    private static double latConverter(String lat) {
        lat = lat.trim();
        char ch = lat.charAt(lat.length() - 1);
        lat = lat.substring(0, lat.length() - 1);
        double res = Double.valueOf(lat);
        if (ch == 'N') {
            return res;
        } else {
            return 0 - res;
        }
    }

    private static void readCSV(String filePath) throws IOException {
        String[] files = filePath.split("/");
        String fileName = files[files.length - 1];
        fileName = MyStringUtils.removeSuffix(fileName, ".csv");
        int year = Integer.valueOf(fileName.substring(fileName.length() - 4, fileName.length()));
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();

        //处理map, 72*144,
        //经度（正：东经　负：西经）; 纬度（正：北纬　负：南纬）

        LngLat[][] lnglatMap = new LngLat[72][144];

        for (int i = 0; i < 72; i++) {
            line = reader.readLine();
            String[] items = line.split(",");
            for (int j = 0; j < 144; j++) {
                String item = items[j];
                String[] jingweidu = item.split(" ");
                String weidu = jingweidu[0];
                String jingdu = jingweidu[1];
                double lat = latConverter(weidu);
                double lng = longConverter(jingdu);
                LngLat lngLat = new LngLat(lng, lat);
                lnglatMap[i][j] = lngLat;
            }
        }

        while ((line = reader.readLine()) != null) {
            if (line.contains(",")) {
                System.out.println("Error");
            } else {
                List<Precipitation> precipitations = new ArrayList<>();
                int month = getMonth(line);
                System.out.println(year + " " + month);
                for (int i = 0; i < 72; i++) {
                    line = reader.readLine();
                    String[] items = line.split(",");
                    for (int j = 0; j < 144; j++) {
                        String item = items[j];
                        double p = Double.valueOf(item);
                        LngLat lngLat = lnglatMap[i][j];
                        Precipitation precipitation = new Precipitation(lngLat.getLng(), lngLat.getLat(), year, month, p);
//                        System.out.println(precipitation);
//                        dao.save(precipitation);
                        precipitations.add(precipitation);
                    }
                }
                dao.saveBatch(precipitations);
            }
        }
    }

    private static int getMonth(String month) {
        month = month.trim();
        if (month.equals("January")) {
            return 1;
        }
        if (month.equals("February")) {
            return 2;
        }
        if (month.equals("March")) {
            return 3;
        }
        if (month.equals("April")) {
            return 4;
        }
        if (month.equals("May")) {
            return 5;
        }
        if (month.equals("June")) {
            return 6;
        }
        if (month.equals("July")) {
            return 7;
        }
        if (month.equals("August")) {
            return 8;
        }
        if (month.equals("September")) {
            return 9;
        }
        if (month.equals("October")) {
            return 10;
        }
        if (month.equals("November")) {
            return 11;
        }
        if (month.equals("December")) {
            return 12;
        }
        return 13;
    }
}
