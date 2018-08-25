package com.belac.ines.foodie.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantDetailsResponse {

	@SerializedName("error") @Expose private Boolean error;
	@SerializedName("results") @Expose private List<Result> results = null;

	public Boolean getError() {
		return error;
	}

	public List<Result> getResults() {
		return results;
	}

	public class Result {

		@SerializedName("name") @Expose private String name;
		@SerializedName("telephone") @Expose private String telephone;
		@SerializedName("location") @Expose private String location;

		public String getName() {
			return name;
		}

		public String getTelephone() {
			return telephone;
		}

		public String getLocation() {
			return location;
		}
	}
}
