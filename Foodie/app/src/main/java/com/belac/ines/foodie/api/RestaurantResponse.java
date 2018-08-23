package com.belac.ines.foodie.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantResponse {

    @SerializedName("latitude") @Expose
    private String latitude;
    @SerializedName("longitude") @Expose private String longitude;
    @SerializedName("address") @Expose private String address;
    @SerializedName("name") @Expose private String name;
    @SerializedName("id") @Expose private String id;
    @SerializedName("distance") @Expose private Integer distance;

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Integer getDistance() {
        return distance;
    }

}
