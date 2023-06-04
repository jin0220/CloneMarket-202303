package com.example.clonemarket.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.clonemarket.data.api.RetrofitClient;
import com.example.clonemarket.data.model.PostDto;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRepository {

    public MutableLiveData<JsonArray> dataListArr;

    public void getPostResult(int page) {
        dataListArr = new MutableLiveData<>();

        Call<JsonObject> call = RetrofitClient.api().getPostResult(page);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    JsonParser jsonParser = new JsonParser();
                    JsonObject obj = (JsonObject) jsonParser.parse(response.body().get("data").toString());
                    JsonArray jsonArray = (JsonArray) obj.get("dataList");

                    Log.d("confirm", "getPostResult() 응답 성공 ->" + jsonArray);
                    dataListArr.setValue(jsonArray);
                }
                else {
                    Log.d("confirm", "getPostResult() 응답 실패");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("confirm", "getPostResult() 통신 실패");
            }
        });
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if(response.isSuccessful()){
//                    JsonParser jsonParser = new JsonParser();
//                    JsonObject obj = (JsonObject) jsonParser.parse(response.body().get("data").toString());
//                    JsonArray jsonArray = (JsonArray) obj.get("dataList");
//
//                    Log.d("confirm", "getPostResult() 응답 성공 ->" + jsonArray);
//                }
//                else {
//                    Log.d("confirm", "getPostResult() 응답 실패");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Log.d("confirm", "getPostResult() 통신 실패");
//            }
//        });
    }


    public MutableLiveData<Boolean> dataList1;
    public void setPost(JsonObject jsonObject) {
        dataList1 =  new MutableLiveData<>();

        Call<JsonObject> call = RetrofitClient.api().setPost(jsonObject);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    Log.d("confirm", "setPost() 응답 성공 ->" + response.body().get("data").toString());
                    dataList1.setValue(response.body().get("data").getAsJsonObject().get("result").getAsBoolean());
                }
                else {
                    dataList1.setValue(false);
                    Log.d("confirm", "setPost() 응답 실패");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                Log.d("confirm", "setPost() 통신 실패");
            }
        });

    }
}
