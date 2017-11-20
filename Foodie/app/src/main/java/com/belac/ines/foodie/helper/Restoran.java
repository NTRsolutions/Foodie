package com.belac.ines.foodie.helper;

/**
 * Created by Ines on 20.11.2017..
 */

public class Restoran {
    private String name, adress;

    public Restoran() {
    }

    public Restoran(String name, String adress) {
        this.name = name;
        this.adress = adress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
