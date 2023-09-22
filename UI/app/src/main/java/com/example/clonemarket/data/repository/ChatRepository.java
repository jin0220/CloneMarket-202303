package com.example.clonemarket.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.clonemarket.data.api.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRepository {

    RetrofitClient retrofitClient;

    public MutableLiveData<JsonArray> dataList;
    public void getChattingRoom(JsonObject jsonObject){
        dataList = new MutableLiveData<>();
        retrofitClient = new RetrofitClient();

        Call<JsonObject> call = RetrofitClient.api().getChattingRoom(jsonObject);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){

                    JsonParser jsonParser = new JsonParser();
                    JsonObject obj = (JsonObject) jsonParser.parse(response.body().get("data").toString());
                    JsonArray jsonArray = (JsonArray) obj.get("dataList");
                    Log.d("confirm", "getChattingRoom() 응답 성공 ->" + jsonArray);
                    dataList.setValue(jsonArray);
                }
                else {
//                    dataList1.setValue(false);
                    Log.d("confirm", "getChattingRoom() 응답 실패");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                Log.d("confirm", "getChattingRoom() 통신 실패");
            }
        });

    }

    public MutableLiveData<JsonArray> dataList2;
    public void getRoomList(JsonObject jsonObject){
        dataList2 = new MutableLiveData<>();
        retrofitClient = new RetrofitClient();

        Call<JsonObject> call = RetrofitClient.api().getRoomList(jsonObject);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){

                    JsonParser jsonParser = new JsonParser();
                    JsonObject obj = (JsonObject) jsonParser.parse(response.body().get("data").toString());
                    JsonArray jsonArray = (JsonArray) obj.get("dataList");
                    Log.d("confirm", "getRoomList() 응답 성공 ->" + jsonArray);
                    dataList2.setValue(jsonArray);
                }
                else {
//                    dataList2.setValue(false);
                    Log.d("confirm", "getRoomList() 응답 실패");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                Log.d("confirm", "getRoomList() 통신 실패");
            }
        });

    }
}
