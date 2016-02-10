package com.sunzequn.geo.data.geonames.handler;

import com.sunzequn.geo.data.geonames.bean.*;
import com.sunzequn.geo.data.geonames.bean.Error;
import com.sunzequn.geo.data.jena.Rdf;
import com.sunzequn.geo.data.utils.StringUtils;
import com.sunzequn.geo.data.utils.TimeUtils;
import com.sunzequn.geo.data.utils.WriteUtils;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sloriac on 16-1-22.
 */
public class DataHandler {

    @Test
    public void synchronousContainsData() {

        TimeUtils timeUtils = new TimeUtils();
        timeUtils.start();

        ResourceDao resourceDao = new ResourceDao("contains_url");
        ContentDao contentDao = new ContentDao("contains");
        List<Content> contents = contentDao.getAll();
        System.out.println("the size of visited urls is : " + contents.size());
        Set<Integer> idSet = new HashSet<>();
        for (Content content : contents) {
            idSet.add(content.getId());
        }
        System.out.println("the size of id set is : " + idSet.size());
        List<Resource> containsUrls = resourceDao.getAll();
        for (Resource containsUrl : containsUrls) {
            int id = containsUrl.getId();
            if (idSet.contains(id)) {
                if (containsUrl.getIfvisited() != 1) {
                    resourceDao.update(id, 1);
                }
            } else {
                if (containsUrl.getIfvisited() == 1) {
                    resourceDao.update(id, 2);
                }
            }
        }
        timeUtils.end();
        timeUtils.print();
    }

    @Test
    public void countNotEmpty() {
        TimeUtils timeUtils = new TimeUtils();
        timeUtils.start();
        ContentDao contentDao = new ContentDao("nearby");
        Rdf rdf = new Rdf();
        List<Content> contents = contentDao.getAll();
        int num = 0;
        for (Content content : contents) {
            if (!rdf.isEmpty(content.getContent())) {
                num++;
            }
        }
        timeUtils.end();
        timeUtils.print();
        System.out.println(num);
    }

    public static void writeToFile() throws Exception {

        String file = "Data/src/main/resources/data/sw/contains.rdf";
        WriteUtils writeUtils = new WriteUtils(file, true);
        TimeUtils timeUtils = new TimeUtils();
        timeUtils.start();
        ContentDao contentDao = new ContentDao("contains");
        Rdf rdf = new Rdf();
        List<Content> contents = contentDao.getAll();
        int num = 0;
        for (Content content : contents)
            if (!rdf.isEmpty(content.getContent())) {
                writeUtils.write(content.getContent());
                num++;
            }
        writeUtils.close();
        timeUtils.end();
        timeUtils.print();
        System.out.println(num);
    }

    public static void writeToNt() throws IOException {

        int num = 0;
        List<Content> contents = new ArrayList<>();
        String nt = "Data/src/main/resources/data/sw/contains.nt";
        File file = new File(nt);
        OutputStream outputStream = new FileOutputStream(file, true);
        TimeUtils timeUtils = new TimeUtils();
        timeUtils.start();
        ContentDao contentDao = new ContentDao("contains");
        Rdf rdf = new Rdf();
        contents = contentDao.getAll();
        ErrorDao errorDao = new ErrorDao();

        for (Content content : contents) {

            if (!rdf.isEmpty(content.getContent())) {
                num++;
                try {
                    rdf.toNt(StringUtils.string2InputStream(content.getContent()), outputStream);
                } catch (Exception e) {
                    errorDao.save(new Error(content.getId()));
                }
            }
        }
        outputStream.close();
        timeUtils.end();
        timeUtils.print();
        System.out.println(num);
    }

    public static void neighboursToFile() {
        String file = "Data/src/main/resources/data/sw/neighbours.rdf";
        WriteUtils writeUtils = new WriteUtils(file, true);
        TimeUtils timeUtils = new TimeUtils();
        timeUtils.start();
        ContentDao contentDao = new ContentDao("neighbours");
        Rdf rdf = new Rdf();
        List<Content> contents = contentDao.getAll();
        int num = 0;
        for (Content content : contents)
            if (!rdf.isEmpty(content.getContent())) {
                writeUtils.write(content.getContent());
                num++;
            } else {
                System.out.println(content.getContent());
            }
        writeUtils.flush();
        writeUtils.close();
        timeUtils.end();
        timeUtils.print();
        System.out.println(num);
    }

    public static void nearbyToFile() {
        NearbyDao nearbyDao = new NearbyDao();
        String file = "Data/src/main/resources/data/sw/nearby.rdf";
        WriteUtils writeUtils = new WriteUtils(file, true);
        TimeUtils timeUtils = new TimeUtils();
        timeUtils.start();
        Rdf rdf = new Rdf();
        int start = 0;
        int num = 0;
        int size = 0;
        while (true) {
            List<Nearby> nearbies = nearbyDao.getAll(start, 40000);
            if (nearbies == null || nearbies.size() == 0) {
                System.out.println("over");
                break;
            }

            size += nearbies.size();
            System.out.println(size);

            for (int i = 0; i < nearbies.size(); i++) {
                Nearby nearby = nearbies.get(i);

                if (i == nearbies.size() - 1) {
                    start = nearby.getId();
                }

                if (nearby.getContent() != null && !rdf.isEmpty(nearby.getContent())) {
                    writeUtils.write(nearby.getContent());
                    num++;
                }

            }
            writeUtils.flush();
        }
        writeUtils.close();
        timeUtils.end();
        timeUtils.print();
        System.out.println(num);
    }

    public static void main(String[] args) throws Exception {
//        writeToFile();
//        writeToNt();

//        neighboursToFile();
        nearbyToFile();
    }


}
