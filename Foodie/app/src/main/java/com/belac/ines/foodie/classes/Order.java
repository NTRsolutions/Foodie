package com.belac.ines.foodie.classes;

import java.util.Date;

/**
 * Created by Korisnik on 21.01.2018..
 */

public class Order {
    private String userName;
    private String restoranName;
    private String datum;
    private String price;

    public Order(String userName, String restoranName, String datum, String price) {
        this.userName = userName;
        this.restoranName = restoranName;
        this.datum = datum;
        this.price = price;
    }

    public String getUserName() { return userName; }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRestoranName() {
        return restoranName;
    }

    public void setRestoranName(String restoranName) {
        this.restoranName = restoranName;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getPrice() { return price; }

    public void setPrice(String price) { this.price = price; }
}
