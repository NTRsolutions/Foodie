package com.belac.ines.foodie.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderResult {

	@SerializedName("name") @Expose private String name;
	@SerializedName("surname") @Expose private String surname;
	@SerializedName("date") @Expose private String date;
	@SerializedName("price") @Expose private String price;
	@SerializedName("delivery") @Expose private String delivery;
	@SerializedName("firstMeal") @Expose private String firstMeal;
	@SerializedName("secondMeal") @Expose private String secondMeal;
	@SerializedName("thirdMeal") @Expose private String thirdMeal;
	@SerializedName("restoran") @Expose private String restoran;

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getDate() {
		return date;
	}

	public String getPrice() {
		return price;
	}

	public String getDelivery() {
		return delivery;
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

	public String getRestoran() {
		return restoran;
	}
}
