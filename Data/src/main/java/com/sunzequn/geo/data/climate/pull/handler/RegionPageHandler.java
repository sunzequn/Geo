package com.sunzequn.geo.data.climate.pull.handler;

import java.util.*;

/**
 * Created by sloriac on 16-2-12.
 */
public class RegionPageHandler {

    private RegionPageWrapper regionPageWrapper = new RegionPageWrapper();

    public RegionPageHandler(int regionId) {
        regionPageWrapper.setId(regionId);
    }

    public void addPageNumber(int pageNumber) {
        regionPageWrapper.getPages().add(pageNumber);
    }

    public List<Integer> getUnExisted() {
        Set<Integer> pages = regionPageWrapper.getPages();
        int maxPage = 0;
        Iterator<Integer> integerIterator = pages.iterator();
        while (integerIterator.hasNext()) {
            int page = integerIterator.next();
            if (maxPage < page) {
                maxPage = page;
            }
        }

        Set<Integer> unExistedPages = new HashSet<>();
        for (int i = 2; i <= maxPage; i++) {
            unExistedPages.add(i);
        }
        unExistedPages.removeAll(pages);
        return new ArrayList<>(unExistedPages);
    }

    public static void main(String[] args) {
        RegionPageHandler regionPageHandler = new RegionPageHandler(1);
        regionPageHandler.addPageNumber(2);
//        regionPageHandler.addPageNumber(10);
        regionPageHandler.addPageNumber(3);
        System.out.println(regionPageHandler.getUnExisted());
    }
}
