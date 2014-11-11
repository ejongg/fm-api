
package com.fm.api.classes;


public class Product {
    private int id;
    private int prod_id;
    private String name;
    private String size;
    private double price;
    private int bottles;
    private int cases;
    private String brand;
    private int lifespan; // Lifespan in months 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getProd_Id(){
        return prod_id;
    }
    
    public void setProd_Id(int prod_id){
        this.prod_id = prod_id;
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

    public int getBottles() {
        return bottles;
    }

    public void setBottles(int bottles) {
        this.bottles = bottles;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getLifespan() {
        return lifespan;
    }

    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }

}
