package com.sunzequn.geo.data.baike.datamining.frequentpatterns;

import java.util.Map;

/**
 * Created by sunzequn on 2016/4/23.
 */
public class Holder {

    public final FpNode root;
    public final Map<String, FpNode> header;

    public Holder(FpNode root, Map<String, FpNode> header) {
        this.root = root;
        this.header = header;
    }

}
