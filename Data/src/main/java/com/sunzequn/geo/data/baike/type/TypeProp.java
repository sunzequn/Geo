package com.sunzequn.geo.data.baike.type;

/**
 * Created by Sloriac on 16/3/22.
 * <p>
 * basic info 的属性词, 也就是key
 */
public class TypeProp {

    private String name;
    //用tf-idf算
    private double weight;

    public TypeProp(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


    @Override
    public String toString() {
        return "TypeProp{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                '}';
    }
}
