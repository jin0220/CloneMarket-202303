package com.example.clonemarket.data.api;

import android.content.Context;

import com.example.clonemarket.data.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://54d0-218-236-76-214.ngrok-free.app/";
    private static String token = "";

    public RetrofitClient() {
    }

    public RetrofitClient(String token) {
        this.token = token;
    }

    private static Retrofit getInstance() {
        Gson gson = new GsonBuilder().setLenient().create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("X-AUTH-TOKEN",
                                token)
                        .build();
                return chain.proceed(request);
            }
        });

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static ApiService api() {
        return getInstance().create(ApiService.class);
    }

}
