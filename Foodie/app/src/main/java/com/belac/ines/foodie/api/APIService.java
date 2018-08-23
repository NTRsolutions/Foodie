package com.belac.ines.foodie.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {
    @POST("/foodie/Login.php") @FormUrlEncoded
    Call<com.belac.ines.foodie.api.models.LoginResponse> login(@Field("email") String email, @Field("password") String password);

    @POST("/foodie/Register.php") @FormUrlEncoded Call<ResponseBody> register(
            @Field("name") String name, @Field("surname") String surname, @Field("email") String email,
            @Field("telephone") String telephone, @Field("address") String address, @Field("restoran") String restoran,
            @Field("password") String password);

}
