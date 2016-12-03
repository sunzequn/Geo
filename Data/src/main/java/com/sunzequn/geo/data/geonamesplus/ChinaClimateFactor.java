package com.sunzequn.geo.data.geonamesplus;

import java.util.List;

/**
 * Created by sunzequn on 2016/7/22.
 */
public class ChinaClimateFactor {
//	要素ID                        要素中文含义                  要素单位                      要素类型               
//	V01000                        区站号                                                      Number(5)                                                                                 
//	V04001                        年                            年                            Number(4)                                                                                 
//	V04002                        月                            月                            Number(2)                                                                                 
//	V04003                        日                            日                            Number(2)                                                                                 
//	V11002                        平均风速                      0.1m/s                        Number(6)                                                                                 
//	V11042                        最大风速                      0.1m/s                        Number(6)                                                                                 
//	V11212                        最大风速的风向                方位                          Number(6)                                                                                 
//	V11041                        极大风速                      0.1m/s                        Number(6)                                                                                 
//	V11043                        极大风速的风向                方位                          Number(6)                                                                                 
//	V14032                        日照时数                      0.1小时                       Number(6)                                                                                 
//	V10004                        平均本站气压                  0.1hPa                        Number(6)                                                                                 
//	V10201                        日最高本站气压                0.1hPa                        Number(6)                                                                                 
//	V10202                        日最低本站气压                0.1hPa                        Number(6)                                                                                 
//	V12001                        平均气温                      0.1℃                         Number(6)                                                                                 
//	V12052                        日最高气温                    0.1℃                         Number(6)                                                                                 
//	V12053                        日最低气温                    0.1℃                         Number(6)                                                                                 
//	V13004                        平均水汽压                    0.1hPa                        Number(6)                                                                                 
//	V13003                        平均相对湿度                  1%                            Number(6)                                                                                 
//	V13007                        最小相对湿度                  1%                            Number(6)                                                                                 
//	V13201                        20-20时降水量                 0.1mm                         Number(6)                                                                                 
    private int V01000;
    private int V04001;
    private int V04002;
    private int V04003;
    private int V11002;
    private int V11042;
    private int V11212;
    private int V11041;
    private int V11043;
    private int V14032;
    private int V10004;
    private int V10201;
    private int V10202;
    private int V12001;
    private int V12052;
    private int V12053;
    private int V13004;
    private int V13003;
    private int V13007;
    private int V13201;

    public ChinaClimateFactor(List<Integer> values) {
        this.V01000 = values.get(0);
        this.V04001 = values.get(1);
        this.V04002 = values.get(2);
        this.V04003 = values.get(3);
        this.V11002 = values.get(4);
        this.V11042 = values.get(5);
        this.V11212 = values.get(6);
        this.V11041 = values.get(7);
        this.V11043 = values.get(8);
        this.V14032 = values.get(9);
        this.V10004 = values.get(10);
        this.V10201 = values.get(11);
        this.V10202 = values.get(12);
        this.V12001 = values.get(13);
        this.V12052 = values.get(14);
        this.V12053 = values.get(15);
        this.V13004 = values.get(16);
        this.V13003 = values.get(17);
        this.V13007 = values.get(18);
        this.V13201 = values.get(19);

    }

    public ChinaClimateFactor() {
    }

    public int getV01000() {
        return V01000;
    }

    public void setV01000(int v01000) {
        V01000 = v01000;
    }

    public int getV04001() {
        return V04001;
    }

    public void setV04001(int v04001) {
        V04001 = v04001;
    }

    public int getV04002() {
        return V04002;
    }

    public void setV04002(int v04002) {
        V04002 = v04002;
    }

    public int getV04003() {
        return V04003;
    }

    public void setV04003(int v04003) {
        V04003 = v04003;
    }

    public int getV11002() {
        return V11002;
    }

    public void setV11002(int v11002) {
        V11002 = v11002;
    }

    public int getV11042() {
        return V11042;
    }

    public void setV11042(int v11042) {
        V11042 = v11042;
    }

    public int getV11212() {
        return V11212;
    }

    public void setV11212(int v11212) {
        V11212 = v11212;
    }

    public int getV11041() {
        return V11041;
    }

    public void setV11041(int v11041) {
        V11041 = v11041;
    }

    public int getV11043() {
        return V11043;
    }

    public void setV11043(int v11043) {
        V11043 = v11043;
    }

    public int getV14032() {
        return V14032;
    }

    public void setV14032(int v14032) {
        V14032 = v14032;
    }

    public int getV10004() {
        return V10004;
    }

    public void setV10004(int v10004) {
        V10004 = v10004;
    }

    public int getV10201() {
        return V10201;
    }

    public void setV10201(int v10201) {
        V10201 = v10201;
    }

    public int getV10202() {
        return V10202;
    }

    public void setV10202(int v10202) {
        V10202 = v10202;
    }

    public int getV12001() {
        return V12001;
    }

    public void setV12001(int v12001) {
        V12001 = v12001;
    }

    public int getV12052() {
        return V12052;
    }

    public void setV12052(int v12052) {
        V12052 = v12052;
    }

    public int getV12053() {
        return V12053;
    }

    public void setV12053(int v12053) {
        V12053 = v12053;
    }

    public int getV13004() {
        return V13004;
    }

    public void setV13004(int v13004) {
        V13004 = v13004;
    }

    public int getV13003() {
        return V13003;
    }

    public void setV13003(int v13003) {
        V13003 = v13003;
    }

    public int getV13007() {
        return V13007;
    }

    public void setV13007(int v13007) {
        V13007 = v13007;
    }

    public int getV13201() {
        return V13201;
    }

    public void setV13201(int v13201) {
        V13201 = v13201;
    }
}
