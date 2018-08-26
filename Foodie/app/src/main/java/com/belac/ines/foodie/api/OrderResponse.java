package com.belac.ines.foodie.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResponse {

	@SerializedName("error") @Expose private Boolean error;
	@SerializedName("results") @Expose private List<OrderResult> results;

	public Boolean getError() {
		return error;
	}

	public List<OrderResult> getResults() {
		return results;
	}
}
