package com.example.clonemarket.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.clonemarket.data.api.RetrofitClient;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    public MutableLiveData<JsonObject> dataList = new MutableLiveData<>();

    public void getAuthNumResult(JsonObject jsonObject) {

        Call<JsonObject> call = RetrofitClient.api().getAuthNumResult(jsonObject);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    JsonParser jsonParser = new JsonParser();
                    Object obj = jsonParser.parse(response.body().get("data").toString());
                    JsonObject data = (JsonObject) obj;

                    Log.d("confirm", "getAuthNumResult() 응답 성공 ->" + data);
                    dataList.setValue(data);
                }
                else {
                    Log.d("confirm", "getAuthNumResult() 응답 실패");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                Log.d("confirm", "getAuthNumResult() 통신 실패");
            }
        });


    }

}
