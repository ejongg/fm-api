
package com.fm.api.classes;


public class Product {
    private int id;
    private String name;
    private String size;
    private double price;
    private int logical_count;
    private int physical_count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getLogicalCount() {
        return logical_count;
    }

    public void setLogicalCount(int logical_count) {
        this.logical_count = logical_count;
    }

    public int getPhysicalCount() {
        return physical_count;
    }

    public void setPhysicalCount(int physical_count) {
        this.physical_count = physical_count;
    }
}
