
package com.fm.api.classes;

public class InventoryProduct extends Product{
    private String expiration;
    private int age;

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
