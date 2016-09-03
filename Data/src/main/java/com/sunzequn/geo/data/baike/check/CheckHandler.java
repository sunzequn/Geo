package com.sunzequn.geo.data.baike.check;

import com.sunzequn.geo.data.baike.bdbk.UrlType;
import com.sunzequn.geo.data.baike.bdbk.UrlTypeDao;
import com.sunzequn.geo.data.utils.ReadUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/19.
 */
public class CheckHandler {

    private static final String FILE_PATH = "D:/DevSpace/github/Geo/Data/src/main/resources/jiancha/";
    private static UrlTypeDao urlTypeDao = new UrlTypeDao();

    public static void main(String[] args) {
//        checkSea();
//        check1();
//        check2();
        checkfu();
    }

    private static void checkfu() {
        String fileName = FILE_PATH + "qian2.txt";
        ReadUtils readUtils = new ReadUtils(fileName);
        List<String> lines = readUtils.readByLine();
        for (String line : lines) {
            handleLine(line, 2);
        }
    }

    private static void check2() {
        String fileName = FILE_PATH + "check2/xue.txt";
        ReadUtils readUtils = new ReadUtils(fileName);
        List<String> lines = readUtils.readByLine();
        for (String line : lines) {
            handleLine(line, 2);
        }
    }

    private static void check1() {
        String dirName = FILE_PATH + "check1";
        File dir = new File(dirName);
        File[] files = dir.listFiles();
        for (File file : files) {
            System.out.println(file.getName());
            ReadUtils readUtils = new ReadUtils(dirName + "/" + file.getName());
            List<String> lines = readUtils.readByLine();
            int num = 0;
            for (String line : lines) {
                handleLine(line, 1);
                if (line.startsWith("0")) {
                    num++;
                }
            }
            System.out.println(num);
        }

    }

    private static void checkSea() {
        String seaFile = FILE_PATH + "sea.txt";
        ReadUtils readUtils = new ReadUtils(seaFile);
        List<String> lines = readUtils.readByLine();
        for (String line : lines) {
            handleLine(line, 1);
        }
    }

    private static void handleLine(String line, int urlIndex) {
        line = line.trim();
        if (line.startsWith("0")) {
            String[] strings = StringUtils.split(line, "\t");
            String url = StringUtils.removeStart(strings[urlIndex], "http://baike.baidu.com");
            System.out.println(url);
            urlTypeDao.updateConfidence(url, 0);
        }
    }
}
