package com.sunzequn.geo.data.longlatgrid;

import com.sunzequn.geo.data.algorithm.location.distance.LongLatCalculator;

import java.util.List;

/**
 * Created by sunzequn on 2016/6/29.
 */
public class FindGrid {

    private Converter converter = new Converter();
    private LongLatCalculator calculator = new LongLatCalculator();
    private List<List<Grid>> koppenGrid = converter.koppenGrid();
    private List<List<Grid>> gpcpGrid = converter.gpcpGrid();

    public static void main(String[] args) {
        FindGrid findGrid = new FindGrid();
//        System.out.println(findGrid.findKoppenGrid(107.73, 11.93));
        System.out.println(findGrid.findGpcpGrid(-37.73, 12.93));
    }


    public Grid findKoppenGrid(double lng, double lat) {
        return findGrid(lng, lat, koppenGrid);
    }

    public Grid findGpcpGrid(double lng, double lat) {
        return findGrid(lng, lat, gpcpGrid);
    }

    //找到最近的点
    private Grid findGrid(double lng, double lat, List<List<Grid>> grids) {
        for (int i = 0; i < grids.size() - 1; i++) {
            //当前行
            List<Grid> upGrids = grids.get(i);
            double upLat = upGrids.get(0).getLatitude();
            //下一行
            List<Grid> downGrids = grids.get(i + 1);
            double downLat = downGrids.get(0).getLatitude();

            //目标纬度在两行之间，然后去确定经度
            if (lat < upLat && lat > downLat) {

                return findGridBetweenRows(lng, lat, upGrids, downGrids);
            }
            //目标点在某一行
            else if (lat == upLat) {
                return findGridOnRow(lng, upGrids);
            } else if (lat == downLat) {
                return findGridOnRow(lng, downGrids);
            }
        }
        return null;
    }

    //已经确定了目标点在两行之间，确定最近的点
    private Grid findGridBetweenRows(double lng, double lat, List<Grid> upGrids, List<Grid> downGrids) {
        Grid upGrid = findGridOnRow(lng, upGrids);
        Grid downGrid = findGridOnRow(lng, downGrids);
        double upDis = calculator.distance(lat, lng, upGrid.getLatitude(), upGrid.getLongitude());
        double downDis = calculator.distance(lat, lng, downGrid.getLatitude(), downGrid.getLongitude());
        return upDis < downDis ? upGrid : downGrid;
    }

    //已经确定了目标点在某一行，确定最近的点
    private Grid findGridOnRow(double lng, List<Grid> grids) {
        //目标点的经度比当前行的最小经度还小，就返回最小经度的点
        if (lng < grids.get(0).getLongitude()) {
            return grids.get(0);
        }
        //目标点的经度比当前行的最大经度还大，就返回最大经度的点
        if (lng > grids.get(grids.size() - 1).getLongitude()) {
            return grids.get(grids.size() - 1);
        }
        //目标点再两列之间或者正好在列上
        for (int i = 0; i < grids.size() - 1; i++) {
            Grid left = grids.get(i);
            double leftLng = left.getLongitude();
            Grid right = grids.get(i + 1);
            double rightLng = right.getLongitude();
            //两列之间，直接判断经度差的大小
            if (lng > leftLng && lng < rightLng) {
                double leftDis = lng - leftLng;
                double rightDis = rightLng - lng;
                return leftDis < rightDis ? left : right;
            }
            //在列上
            else if (lng == leftLng) {
                return left;
            } else if (lng == rightLng) {
                return right;
            }
        }
        return null;
    }

}
