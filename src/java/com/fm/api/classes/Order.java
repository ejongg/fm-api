
package com.fm.api.classes;

public class Order extends Product{
    private String date_received;

    public String getDate_received() {
        return date_received;
    }

    public void setDate_received(String date_received) {
        this.date_received = date_received;
    }
}
