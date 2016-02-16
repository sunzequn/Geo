package com.sunzequn.geo.data.geonames.bean;

/**
 * Created by Sloriac on 16/2/16.
 */
public class CountryInfo {

    private String iso_alpha2;
    private String iso_alpha3;
    private int iso_numeric;
    private String fips_code;
    private String name;
    private String capital;
    private double areainsqkm;
    private int population;
    private String continent;
    private String tld;
    private String currency;
    private String currencyName;
    private String Phone;
    private String postalCodeFormat;
    private String postalCodeRegex;
    private int geonameId;
    private String languages;
    private String neighbours;
    private String equivalentFipsCode;

    public String getIso_alpha2() {
        return iso_alpha2;
    }

    public void setIso_alpha2(String iso_alpha2) {
        this.iso_alpha2 = iso_alpha2;
    }

    public String getIso_alpha3() {
        return iso_alpha3;
    }

    public void setIso_alpha3(String iso_alpha3) {
        this.iso_alpha3 = iso_alpha3;
    }

    public int getIso_numeric() {
        return iso_numeric;
    }

    public void setIso_numeric(int iso_numeric) {
        this.iso_numeric = iso_numeric;
    }

    public String getFips_code() {
        return fips_code;
    }

    public void setFips_code(String fips_code) {
        this.fips_code = fips_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public double getAreainsqkm() {
        return areainsqkm;
    }

    public void setAreainsqkm(double areainsqkm) {
        this.areainsqkm = areainsqkm;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getTld() {
        return tld;
    }

    public void setTld(String tld) {
        this.tld = tld;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPostalCodeFormat() {
        return postalCodeFormat;
    }

    public void setPostalCodeFormat(String postalCodeFormat) {
        this.postalCodeFormat = postalCodeFormat;
    }

    public String getPostalCodeRegex() {
        return postalCodeRegex;
    }

    public void setPostalCodeRegex(String postalCodeRegex) {
        this.postalCodeRegex = postalCodeRegex;
    }

    public int getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(int geonameId) {
        this.geonameId = geonameId;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(String neighbours) {
        this.neighbours = neighbours;
    }

    public String getEquivalentFipsCode() {
        return equivalentFipsCode;
    }

    public void setEquivalentFipsCode(String equivalentFipsCode) {
        this.equivalentFipsCode = equivalentFipsCode;
    }
}
