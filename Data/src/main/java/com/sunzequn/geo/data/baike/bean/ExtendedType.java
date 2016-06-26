package com.sunzequn.geo.data.baike.bean;

import com.sunzequn.geo.data.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunzequn on 2016/4/5.
 */
public class ExtendedType {

    private String typeName;
    private String entype;
    private String superType;
    private String altLabel;
    private String comment;
    private String encomment;
    private List<String> altLabels;

    public ExtendedType() {
    }

    public ExtendedType(String altLabel, List<String> altLabels, String comment, String entype, String superType, String typeName) {
        this.altLabel = altLabel;
        this.altLabels = altLabels;
        this.comment = comment;
        this.entype = entype;
        this.superType = superType;
        this.typeName = typeName;
    }

    public void initAltLabels() {
        if (!StringUtils.isNullOrEmpty(altLabel)) {
            String[] labels = org.apache.commons.lang3.StringUtils.split(altLabel, "/");
            altLabels = new ArrayList<>();
            for (String label : labels) {
                altLabels.add(label);
            }
        }
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSuperType() {
        return superType;
    }

    public void setSuperType(String superType) {
        this.superType = superType;
    }

    public String getAltLabel() {
        return altLabel;
    }

    public void setAltLabel(String altLabel) {
        this.altLabel = altLabel;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getAltLabels() {
        return altLabels;
    }

    public void setAltLabels(List<String> altLabels) {
        this.altLabels = altLabels;
    }

    public String getEntype() {
        return entype;
    }

    public void setEntype(String entype) {
        this.entype = entype;
    }

    public String getEncomment() {
        return encomment;
    }

    public void setEncomment(String encomment) {
        this.encomment = encomment;
    }

    @Override
    public String toString() {
        return "ExtendedType{" +
                "altLabel='" + altLabel + '\'' +
                ", typeName='" + typeName + '\'' +
                ", entype='" + entype + '\'' +
                ", superType='" + superType + '\'' +
                ", comment='" + comment + '\'' +
                ", altLabels=" + altLabels +
                '}';
    }
}
