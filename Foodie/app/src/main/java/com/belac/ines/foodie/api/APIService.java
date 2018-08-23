package com.belac.ines.foodie.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {
    @POST("/foodie/Login.php") @FormUrlEncoded
    Call<com.belac.ines.foodie.api.models.LoginResponse> login(@Field("email") String email, @Field("password") String password);

}
