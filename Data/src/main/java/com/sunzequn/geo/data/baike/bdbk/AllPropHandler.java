package com.sunzequn.geo.data.baike.bdbk;

import com.sunzequn.geo.data.baike.bean.InfoBoxTemplateProp;
import com.sunzequn.geo.data.baike.dao.InfoBoxTemplatePropDao;
import com.sunzequn.geo.data.utils.ListUtils;
import com.sunzequn.geo.data.utils.WriteUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sunzequn on 2016/4/27.
 */
public class AllPropHandler {

    private static UrlTypeDao urlTypeDao = new UrlTypeDao();
    private static BasicInfoDao basicInfoDao = new BasicInfoDao();
    private static InfoBoxTemplatePropDao propDao = new InfoBoxTemplatePropDao();
    private static final String FILE = "D:/DevSpace/github/Geo/Data/src/main/resources/baike/";
    private static final String RAW_FILE = FILE + "rawprop.txt";

    public static void main(String[] args) {
        writeRawProp();
    }

    private static void writeRawProp() {
        WriteUtils writeUtils = new WriteUtils(RAW_FILE, false);
        List<UrlType> urlTypes = urlTypeDao.getAll();
        System.out.println(urlTypes.size());

        List<InfoBoxTemplateProp> props = propDao.getAll();
        Set<String> propset = new HashSet<>();
        for (InfoBoxTemplateProp prop : props) {
            propset.add(prop.getName().trim());
        }
        System.out.println(propset.size());

        Set<String> keys = new HashSet<>();
        for (UrlType urlType : urlTypes) {
            String url = urlType.getUrl();
            List<BasicInfo> basicInfos = basicInfoDao.getByUrl(url);
            if (!ListUtils.isEmpty(basicInfos)) {
                for (BasicInfo basicInfo : basicInfos) {
                    keys.add(basicInfo.getKey().trim());
                }
            }
            System.out.println(keys.size());
        }
        System.out.println(keys.size());
        keys.removeAll(propset);
        System.out.println(keys.size());
        for (String key : keys) {
            writeUtils.write(key);
        }
        writeUtils.close();
    }
}
