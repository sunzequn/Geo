package com.sunzequn.geo.data.geonames.bean;

/**
 * Created by Sloriac on 16/2/28.
 */
public class FeatureCodes {
    private String code;
    private String name;
    private String description;

    public FeatureCodes() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "FeatureCodes{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
