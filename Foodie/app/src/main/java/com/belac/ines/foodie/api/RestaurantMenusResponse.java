package com.belac.ines.foodie.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantMenusResponse {

    @SerializedName("error") @Expose private Boolean error;
    @SerializedName("results") @Expose private List<Result> results;

    public Boolean getError() {
        return error;
    }

    public List<Result> getResults() {
        return results;
    }

    public class Result {
        @SerializedName("id") @Expose private String id;
        @SerializedName("firstMeal") @Expose private String firstMeal;
        @SerializedName("secondMeal") @Expose private String secondMeal;
        @SerializedName("thirdMeal") @Expose private String thirdMeal;
        @SerializedName("price") @Expose private String price;

        public String getId() {
            return id;
        }

        public String getFirstMeal() {
            return firstMeal;
        }

        public String getSecondMeal() {
            return secondMeal;
        }

        public String getThirdMeal() {
            return thirdMeal;
        }

        public String getPrice() {
            return price;
        }
    }
}
