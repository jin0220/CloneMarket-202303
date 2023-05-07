package com.example.clonemarket.data.api;

import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

    @Multipart
    @POST("/api/v1/profile")
    Call<JsonObject> setProfile(@Part("nickName") String nickName, @Part MultipartBody.Part file);

}
