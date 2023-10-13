package com.example.clonemarket.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.clonemarket.data.api.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TownInfoRepository {

    RetrofitClient retrofitClient;

    public MutableLiveData<JsonArray> dataList;
    public MutableLiveData<JsonElement> data;
    public MutableLiveData<String> result;

    public void getInfoList(int page){
        dataList = new MutableLiveData<>();
        retrofitClient = new RetrofitClient();

        Call<JsonObject> call = RetrofitClient.api().getInfoList(page);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    JsonParser jsonParser = new JsonParser();
                    JsonObject obj = (JsonObject) jsonParser.parse(response.body().get("data").toString());
                    JsonArray jsonArray = (JsonArray) obj.get("dataList");
                    Log.d("confirm", "getInfoList() 응답 성공 ->" + jsonArray);
                    dataList.setValue(jsonArray);
                }
                else {
                    Log.d("confirm", "getInfoList() 응답 실패");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                Log.d("confirm", "getInfoList() 통신 실패");
            }
        });
    }

    public void getInfo(Long num){
        data = new MutableLiveData<>();
        retrofitClient = new RetrofitClient();

        Call<JsonObject> call = RetrofitClient.api().getInfo(num);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {
                    JsonParser jsonParser = new JsonParser();
                    JsonObject obj = (JsonObject) jsonParser.parse(response.body().get("data").toString());
                    JsonElement jsonElement = (JsonElement) obj.get("dataList");
                    Log.d("confirm", "getInfo() 응답 성공 ->" + jsonElement);
                    data.setValue(jsonElement);
                }
                else {
                    Log.d("confirm", "getInfo() 응답 실패");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                Log.d("confirm", "getInfo() 통신 실패");
            }
        });
    }

    public void setInfo(JsonObject jsonObject){
        result = new MutableLiveData<>();
        retrofitClient = new RetrofitClient();

        Call<JsonObject> call = RetrofitClient.api().setInfo(jsonObject);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonParser jsonParser = new JsonParser();
                    JsonObject obj = (JsonObject) jsonParser.parse(response.body().get("data").toString());
                    JsonArray jsonArray = (JsonArray) obj.get("result");
                    Log.d("confirm", "setInfo() 응답 성공 ->" + jsonArray);
                    result.setValue(jsonArray.getAsString());
                }
                else {
                    Log.d("confirm", "setInfo() 응답 실패");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                Log.d("confirm", "setInfo() 통신 실패");
            }
        });
    }
}
