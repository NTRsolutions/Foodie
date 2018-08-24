package com.belac.ines.foodie.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MenuResponse {

    @SerializedName("error") @Expose
    private Boolean error;
    @SerializedName("results") @Expose private List<Result> results = null;

    public Boolean getError() {
        return error;
    }

    public List<Result> getResults() {
        return results;
    }

    public class Result {

        @SerializedName("name") @Expose private String name;
        @SerializedName("first") @Expose private String first;
        @SerializedName("second") @Expose private String second;
        @SerializedName("third") @Expose private String third;
        @SerializedName("price") @Expose private String price;
        @SerializedName("id") @Expose private String id;

        public String getName() {
            return name;
        }

        public String getFirst() {
            return first;
        }

        public String getSecond() {
            return second;
        }

        public String getThird() {
            return third;
        }

        public String getPrice() {
            return price;
        }

        public String getId() {
            return id;
        }
    }

}
