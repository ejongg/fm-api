
package com.fm.api.classes;

public class ProductInfo {
    private int prod_id;
    private String name;
    private String brand;

    public int getProd_Id() {
        return prod_id;
    }

    public void setProd_Id(int id) {
        this.prod_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
