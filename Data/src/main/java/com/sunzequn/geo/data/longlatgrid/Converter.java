package com.sunzequn.geo.data.longlatgrid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sunzequn on 2016/6/29.
 * <p>
 * 从左向右，经度从小到大，-180  — 0 — 180
 * 从上到下，纬度从大到小，90 — 0 — -90
 */
public class Converter {

    public List<List<Grid>> gpcpGrid() {
        List<List<Grid>> res = new ArrayList<>();
        double lat = 91.25;
        for (int i = 0; i < 72; i++) {
            lat -= 2.5;
            double lng = -181.25;
            List<Grid> row = new ArrayList<>();
            for (int j = 0; j < 144; j++) {
                lng += 2.5;
                row.add(new Grid(lng, lat));
            }
            res.add(row);
        }
        return res;
    }

    public List<List<Grid>> koppenGrid() {
        KoppenDao dao = new KoppenDao();
        List<Koppen> koppens = dao.getAll();
        List<List<Grid>> res = new ArrayList<>();
        List<Grid> row = new ArrayList<>();
        double latFlag = -9999;
        for (Koppen koppen : koppens) {
            if (koppen.getLatitude() != latFlag) {
                if (latFlag > -9999) {
                    res.add(row);
                }
                row = new ArrayList<>();
            }
            row.add(new Grid(koppen.getLongitude(), koppen.getLatitude()));
            latFlag = koppen.getLatitude();
        }
        res.add(row);
        return res;
    }

    private static void checkGrid(List<List<Grid>> koppenGrid) {
        double lat = 9999;
        //检查每行纬度是否递减
        for (List<Grid> grids : koppenGrid) {
            System.out.println(grids.get(0).getLatitude());
            if (grids.get(0).getLatitude() > lat) {
                System.out.println("Error");
            } else {
//                System.out.println("ok");
            }
            lat = grids.get(0).getLatitude();
        }

        //检查每行经度是否递增且纬度一样
        for (List<Grid> grids : koppenGrid) {
            lat = grids.get(0).getLatitude();
            double lng = -9999;
            for (Grid grid : grids) {
                if (grid.getLatitude() != lat) {
                    System.out.println("Error");
                }
                if (grid.getLongitude() < lng) {
                    System.out.println("Error");
                }
                lng = grid.getLongitude();
            }
        }

    }

    public static void main(String[] args) {
        Converter converter = new Converter();
//        List<List<Grid>> koppenGrid = converter.koppenGrid();
//        System.out.println(koppenGrid.size());
//        checkGrid(koppenGrid);
        List<List<Grid>> gpcpGrid = converter.gpcpGrid();
        checkGrid(gpcpGrid);
        for (Grid grid : gpcpGrid.get(0)) {
            System.out.println(grid);
        }

    }
}
