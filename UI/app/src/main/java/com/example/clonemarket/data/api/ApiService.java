package com.example.clonemarket.data.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("/api/v1/sms")
    Call<JsonObject> getAuthNumResult(@Body JsonObject jsonObject);

    @Headers("Content-Type: application/json")
    @POST("/api/v1/login")
    Call<JsonObject> getLoginResult(@Body JsonObject jsonObject);

    @Headers("Content-Type: application/json")
    @POST("/api/v1/location")
    Call<JsonObject> getLocationResult(@Body JsonObject jsonObject);
}
