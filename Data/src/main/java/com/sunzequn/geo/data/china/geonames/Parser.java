package com.sunzequn.geo.data.china.geonames;

import com.google.gson.Gson;
import com.sunzequn.geo.data.utils.WriteUtil;
import com.sunzequn.geo.data.crawler.simple.parser.HttpMethod;
import com.sunzequn.geo.data.crawler.simple.parser.PullText;
import com.sunzequn.geo.data.utils.ReadUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sloriac on 15/12/31.
 */
public class Parser extends PullText {

    private static final String FILE_PATH = "Data/src/main/resources/data/geonames/china/";
    private static final String URL_HEAD = "http://sws.geonames.org/";
    private static final String CONTAINS = "contains.rdf";
    private static final String ABOUT = "about.rdf";
    private GeoCityDao cityDao = new GeoCityDao();

    public String getRdf(String url) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Document document = pullFromUrl(url, 15000, HttpMethod.Get);
        if (document != null) {
            return document.toString();
        }
        return null;
    }

    public void writeRdf(String file, String rdf) {
        WriteUtil writeUtil = new WriteUtil(file, false);
        writeUtil.write(rdf);
        writeUtil.close();
    }

    public void mkdir(String dir) {
        File file = new File(FILE_PATH + dir);
        if (file.exists()) {
            System.out.println("the dir " + dir + " is existed");
            return;
        }
        file.mkdir();
    }

    public String getParam(String url, int max, int index) {
        String string = StringUtils.removeStart(url, URL_HEAD);
        String[] data = StringUtils.split(string, "/");
        if (data.length == max) {
            return data[index].trim();
        }
        return null;
    }

    public String getId(String url) {
        return getParam(url, 2, 0);
    }

    public String getType(String url) {
        return getParam(url, 2, 1);
    }

    public String getContainsUrl(String id) {
        return URL_HEAD + id + "/" + CONTAINS;
    }

    public String getAboutUrl(String id) {
        return URL_HEAD + id + "/" + ABOUT;
    }

    public String getContainsFileName(String id) {
        return FILE_PATH + id + "/" + CONTAINS;
    }

    public String getAboutFileName(String id) {
        return FILE_PATH + id + "/" + ABOUT;
    }

    public void initChina() {
        String chinaId = "1814991";
        saveRdf(chinaId);
    }

    public void saveRdf(String id) {
        System.out.println("download-----------------------------");
        String aboutUrl = getAboutUrl(id);
        String containsUrl = getContainsUrl(id);
        mkdir(id);
        String aboutFileName = getAboutFileName(id);
        String aboutRdf = getRdf(aboutUrl);
        writeRdf(aboutFileName, aboutRdf);

        String containsFileName = getContainsFileName(id);
        String containsRdf = getRdf(containsUrl);
        writeRdf(containsFileName, containsRdf);
    }

    public List<String> contiansId(String id) {
        String containsFile = getContainsFileName(id);
        ReadUtil readUtil = new ReadUtil(containsFile);
        List<String> lines = readUtil.readByLine();
        List<String> ids = new ArrayList<>();
        for (String line : lines) {
            if (line.contains("isdefinedby")) {
                String s[] = StringUtils.split(line, "/");
                ids.add(s[s.length - 3]);
            }
        }
        if (ids.size() > 0) {
            return ids;
        }
        return null;
    }

    public String getChineseName(String id) {
        String name = "";
        String aboutFile = getAboutFileName(id);
        ReadUtil readUtil = new ReadUtil(aboutFile);
        List<String> lines = readUtil.readByLine();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.contains("<gn:alternatename xml:lang=\"zh\">")) {
                String res = lines.get(i + 1);
                if (isContainChinese(res) && res.length() > name.length()) {
                    name = res;
                }
            }
        }
        if (!name.equals("")) {
            return name;
        }
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.contains("<gn:name>")) {
                return lines.get(i + 1);
            }
        }

        return null;
    }

    public void handle(String parentId, String id) {

        int iid = Integer.parseInt(id);
        if (cityDao.getById(iid) != null) {
            System.out.println(id + " has been handled.");
        } else {
            saveRdf(id);
            String name = getChineseName(id);
            GeoCity city = new GeoCity();
            city.setGeonamesid(Integer.parseInt(id));
            city.setName(name);
            city.setParentid(Integer.parseInt(parentId));
            cityDao.save(city);
        }

        List<String> ids = contiansId(id);
        if (ids != null) {
            System.out.println(ids);
            for (String i : ids) {
                handle(id, i);
            }
        }
    }

    public boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public void updateName() {
        List<GeoCity> cities = cityDao.getAll();
        for (GeoCity city : cities) {
            String id = String.valueOf(city.getGeonamesid());
            String name = getChineseName(id);
            System.out.print(city.getName() + " / ");
            System.out.println(name);
            cityDao.updateName(city.getGeonamesid(), name);
        }
    }

    public List<Json> getContains(int id) {

        List<GeoCity> cities = cityDao.getByParentId(id);
        List<Json> jsons = new ArrayList<>();
        if (cities != null) {
            for (GeoCity city : cities) {
                Json json = new Json();
                json.setName(city.getName());
                json.setContains(getContains(city.getGeonamesid()));
                jsons.add(json);
            }
            return jsons;
        }
        return null;
    }

    public void toJson() {
        China china = new China();
        china.setContains(getContains(1814991));
        Gson gson = new Gson();
        System.out.println(gson.toJson(china));
    }

    public static void main(String[] args) {
        Parser parser = new Parser();
//        parser.initChina();
//        String chinaId = "1814991";
//        List<String> ids = parser.contiansId(chinaId);
//        System.out.println(ids);
//        for (String id : ids) {
//            parser.handle(chinaId, id);
//        }

//        parser.updateName();
        parser.toJson();

    }

}
