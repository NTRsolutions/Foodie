package com.belac.ines.foodie.classes;

import java.util.Date;

/**
 * Created by Korisnik on 21.01.2018..
 */

public class Order {
    private String userName;
    private String restoranName;
    private String datum;

    public Order(String userName, String restoranName, String datum) {
        this.userName = userName;
        this.restoranName = restoranName;
        this.datum = datum;
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
}
