package com.sunzequn.geo.data.geonamesplus.handler;

import com.sunzequn.geo.data.geonamesplus.*;
import com.sunzequn.geo.data.utils.DoubleUtils;
import com.sunzequn.geo.data.utils.ReadUtils;
import com.sunzequn.geo.data.utils.WriteUtils;

import java.util.*;

/**
 * Created by sloriac on 16-12-3.
 *
 * 遍历GeoNames的全部点，补充koppen气候类型、中学气候类型、降水
 */
public class GeoNamesClimateHandler {

    private static final int THREAD_NUM = 20;
    private static LinkedList<Integer> geonamesIds = new LinkedList<>();
    private static WriteUtils writeUtils = new WriteUtils(GeoNamesConf.GEONAMES_CLIMATE, false);
    private static int noid = 0;
    private static int writeNum = 0;

    public static void main(String[] args) {
        readIds();
        climate();

    }

    private static void climate() {
        Vector<Thread> threads = new Vector<>();
        for (int i = 0; i < THREAD_NUM; i++) {
            Thread thread = new Thread(() -> {
                GeonameDao geonameDao = new GeonameDao();
                ClimateHandler climateHandler = new ClimateHandler();
                PrecipitationHandler precipitationHandler = new PrecipitationHandler();
                while (true){
                    int geonamesId = getId();
                    if (geonamesId == -1) return;
                    try {
                        Geoname geoname = geonameDao.getById(geonamesId);
                        if (geoname == null) {
                            noidplus();
                            continue;
                        }
                        //年均降水，　夏季降水总量，冬季降水总量, koppen气候类型, 中学气候类型,　热量带
                        double nianjunjiangshui = precipitationHandler.avgPrecipitataionOfPerYear(geoname.getLongitude(), geoname.getLatitude());
                        List<Double> avPrecipitataionOfPerMonth = precipitationHandler.avPrecipitataionOfPerMonth(geoname.getLongitude(), geoname.getLatitude());
                        double xiajijiangshui = precipitationHandler.avSummerPrecipitation(geoname.getLatitude(), avPrecipitataionOfPerMonth);
                        double dongjijiangshui = precipitationHandler.avWinterPrecipitation(geoname.getLatitude(), avPrecipitataionOfPerMonth);
                        String qihouleixihngs[] = climateHandler.getZhongxueTypeByLntLat(geoname.getLongitude(), geoname.getLatitude());
                        String koppenType = qihouleixihngs[0];
                        String zhongxueType = qihouleixihngs[1];
                        String reliangdai = climateHandler.getReLiangDai(geoname.getLatitude());
                        String line = geonamesId + GeoNamesConf.SPLIT +
                                DoubleUtils.m2d(nianjunjiangshui) + GeoNamesConf.SPLIT +
                                DoubleUtils.m2d(xiajijiangshui) + GeoNamesConf.SPLIT +
                                DoubleUtils.m2d(dongjijiangshui) + GeoNamesConf.SPLIT +
                                koppenType + GeoNamesConf.SPLIT +
                                zhongxueType + GeoNamesConf.SPLIT +
                                reliangdai;
                        write(line);
                    } catch (Exception e){
                        System.out.println(geonamesId);
                        e.printStackTrace();

                    }
                }

            }, "thread " + i);
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                // 等待所有线程执行完毕
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        writeUtils.close();
        System.out.println("no ids : " + noid);
    }

    private static synchronized void noidplus() {
        noid++;
    }

    private static synchronized int getId(){
        if (geonamesIds.size() > 0){
            return geonamesIds.pop();
        }
        return -1;
    }

    private static synchronized void write(String line){
        writeUtils.write(line);
        writeNum++;
        if (writeNum % 10000 == 0){
            writeUtils.flush();
        }
    }

    private static void readIds() {
        ReadUtils reader = new ReadUtils(GeoNamesConf.GEONAMES_ID);
        List<String> lines = reader.readByLine();
        for (String line : lines) {
            int id = Integer.parseInt(line.trim());
            geonamesIds.add(id);
        }
        System.out.println("id数量: " + geonamesIds.size());
    }


}
