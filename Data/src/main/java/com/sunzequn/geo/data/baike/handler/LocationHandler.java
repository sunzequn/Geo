package com.sunzequn.geo.data.baike.handler;

import com.sunzequn.geo.data.baike.bdbk.UrlType;
import com.sunzequn.geo.data.baike.bdbk.UrlTypeDao;
import com.sunzequn.geo.data.baike.bdbk.UrlTypeLocation;
import com.sunzequn.geo.data.baike.bdbk.UrlTypeLocationDao;
import com.sunzequn.geo.data.baike.bdmap.BDDT;
import com.sunzequn.geo.data.baike.bdmap.LocationPull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/12.
 */
public class LocationHandler {

    private static UrlTypeLocationDao urlTypeLocationDao = new UrlTypeLocationDao();
    private static UrlTypeDao urlTypeDao = new UrlTypeDao();
    private static LocationPull locationPull = new LocationPull();

    public static void main(String[] args) {
        getLocation("学校");
    }

    private static void getLocation(String type) {
        List<UrlType> urlTypes = urlTypeDao.getByType(type);
        List<UrlTypeLocation> urlTypeLocations = new ArrayList<>();
        for (UrlType urlType : urlTypes) {
            BDDT bddt = locationPull.getLngLat(urlType.getTitle());
            if (bddt.isValid()) {
                UrlTypeLocation urlTypeLocation = new UrlTypeLocation(urlType.getUrl(), urlType.getType(), urlType.getTitle());
                urlTypeLocation.setLng(bddt.getLng());
                urlTypeLocation.setLat(bddt.getLat());
                urlTypeLocation.setConfidence(bddt.getConfidence());
                urlTypeLocation.setLevel(bddt.getLevel());
                urlTypeLocation.setPrecise(bddt.getPrecise());
                System.out.println(urlTypeLocation);
                urlTypeLocations.add(urlTypeLocation);
                urlTypeLocationDao.add(urlTypeLocation);
            }
        }
//        if (urlTypeLocations.size() > 0){
//            urlTypeLocationDao.addBatch(urlTypeLocations);
//        }
    }
}
