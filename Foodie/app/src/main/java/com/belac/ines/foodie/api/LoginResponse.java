package com.belac.ines.foodie.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("error") @Expose private Boolean error;
    @SerializedName("results") @Expose private Results results;

    public Boolean getError() {
        return error;
    }

    public Results getResults() {
        return results;
    }

    public class Results {

        @SerializedName("idUser") @Expose private Integer idUser;
        @SerializedName("idRestoran") @Expose private Integer idRestaurant;
        @SerializedName("name") @Expose private String name;
        @SerializedName("surname") @Expose private String surname;
        @SerializedName("email") @Expose private String email;
        @SerializedName("telephone") @Expose private String telephone;
        @SerializedName("address") @Expose private String address;
        @SerializedName("type") @Expose private String type;

        public Integer getIdUser() {
            return idUser;
        }

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }

        public String getEmail() {
            return email;
        }

        public String getTelephone() {
            return telephone;
        }

        public String getAddress() {
            return address;
        }

        public String getType() {
            return type;
        }

        public Integer getIdRestaurant() {
            return idRestaurant;
        }
    }
}
