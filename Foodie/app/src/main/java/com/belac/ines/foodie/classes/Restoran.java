package com.belac.ines.foodie.classes;

/**
 * Created by Ines on 20.11.2017..
 */

public class Restoran {
    private String name, adress;
    private double longitude, latitude, distance;
    private Menu meni;
    private int ID;

    public Restoran() {
    }

    public Restoran(String name, String adress, double longitude, double latitude, double distance) {

        this.name = name;
        this.adress = adress;
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
    }

    public Restoran(int id, String name, String adress) {
        this.ID = id;
        this.name = name;
        this.adress = adress;
    }

    public Restoran(int id, String name, Menu meni) {
        this.ID = id;
        this.name = name;
        this.meni = meni;
    }

    public int getID() { return ID; }

    public void setID(int ID) { this.ID = ID; }

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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getDistance() {return distance;}

    public void setDistance(double distance) {this.distance = distance;}

    public Menu getMeni() { return meni; }

    public void setMeni(Menu meni) { this.meni = meni; }
}
