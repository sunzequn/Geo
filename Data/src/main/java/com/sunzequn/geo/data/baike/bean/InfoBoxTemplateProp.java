package com.sunzequn.geo.data.baike.bean;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sloriac on 16-3-29.
 */
public class InfoBoxTemplateProp {

    private String name;
    private String domain1;
    private String range1;
    private int type;
    private String altname;
    private String ename;
    private String comment;

    private List<String> domains = new ArrayList<>();
    private List<String> ranges = new ArrayList<>();
    private List<String> altnames = new ArrayList<>();

    public InfoBoxTemplateProp() {
    }

    public InfoBoxTemplateProp(String domain1, String name, String range1, int type) {
        this.domain1 = domain1;
        this.name = name;
        this.range1 = range1;
        this.type = type;
    }

    public void initAll() {
        initDomainsAndRange();
        initAltName();
    }

    public void initDomainsAndRange() {
        init(domain1, domains);
        init(range1, ranges);
    }

    public void initAltName() {
        init(altname, altnames);
    }

    private void init(String string, List<String> strings) {
        if (string == null) {
            return;
        }
        if (string.contains("/")) {
            String[] ss = StringUtils.split(string, "/");
            Collections.addAll(strings, ss);
        } else {
            strings.add(string);
        }
    }

    public String getDomain1() {
        return domain1;
    }

    public void setDomain1(String domain1) {
        this.domain1 = domain1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRange1() {
        return range1;
    }

    public void setRange1(String range1) {
        this.range1 = range1;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAltname() {
        return altname;
    }

    public void setAltname(String altname) {
        this.altname = altname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public List<String> getAltnames() {
        return altnames;
    }

    public void setAltnames(List<String> altnames) {
        this.altnames = altnames;
    }

    public List<String> getDomains() {
        return domains;
    }

    public void setDomains(List<String> domains) {
        this.domains = domains;
    }

    public List<String> getRanges() {
        return ranges;
    }

    public void setRanges(List<String> ranges) {
        this.ranges = ranges;
    }

    @Override
    public String toString() {
        return "InfoBoxTemplateProp{" +
                "domain1='" + domain1 + '\'' +
                ", name='" + name + '\'' +
                ", range1='" + range1 + '\'' +
                ", type=" + type +
                '}';
    }
}
