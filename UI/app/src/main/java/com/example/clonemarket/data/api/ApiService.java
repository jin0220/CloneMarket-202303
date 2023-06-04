package com.example.clonemarket.data.api;

import com.example.clonemarket.data.model.PostDto;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

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
    Call<JsonObject> setProfile(
            @PartMap Map<String, RequestBody> params,
            @Part MultipartBody.Part file
    );

    @Headers("Content-Type: application/json")
    @GET("/api/v1/posts/{page}")
    Call<JsonObject> getPostResult(@Path("page") int page);

    @Headers("Content-Type: application/json")
    @POST("/api/v1/post")
    Call<JsonObject> setPost(@Body JsonObject jsonObject);

}
