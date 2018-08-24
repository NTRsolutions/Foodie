package com.belac.ines.foodie.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllRestaurantsResponse {

    @Expose
    private Boolean error;

    @SerializedName("results") @Expose private List<Result> results;

    public Boolean getError() {
        return error;
    }

    public List<Result> getResults() {
        return results;
    }

    public class Result {

        @SerializedName("latitude") @Expose private String latitude;
        @SerializedName("longitude") @Expose private String longitude;
        @SerializedName("address") @Expose private String address;
        @SerializedName("name") @Expose private String name;
        @SerializedName("id") @Expose private String id;

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
    }

}
