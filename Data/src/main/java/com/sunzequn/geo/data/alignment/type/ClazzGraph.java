package com.sunzequn.geo.data.alignment.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sloriac on 16/2/27.
 */
public class ClazzGraph {

    private List<String> superClassRels;

    public ClazzGraph() {
    }

    public ClazzGraph(List<String> superClassRels) {
        this.superClassRels = superClassRels;
    }

    public List<String> getSuperClassRels() {
        return superClassRels;
    }

    public void setSuperClassRels(List<String> superClassRels) {
        this.superClassRels = superClassRels;
    }

    @Override
    public String toString() {
        return "ClazzGraph{" +
                "superClassRels=" + superClassRels +
                '}';
    }
}
