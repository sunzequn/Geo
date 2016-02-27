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
    private Map<String, String> equivalents = new HashMap<>();

    public ClazzGraph() {
    }

    public void putEquivalents(String uri1, String uri2) {
        equivalents.put(uri1, uri2);
    }

    public List<String> getSuperClassRels() {
        return superClassRels;
    }

    public void setSuperClassRels(List<String> superClassRels) {
        this.superClassRels = superClassRels;
    }

    public Map<String, String> getEquivalents() {
        return equivalents;
    }

    public void setEquivalents(Map<String, String> equivalents) {
        this.equivalents = equivalents;
    }
}
