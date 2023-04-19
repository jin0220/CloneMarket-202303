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

public class LocationRepository {

    public MutableLiveData<JsonArray> dataList;

    public void getLocationResult(JsonObject jsonObject) {
        dataList =  new MutableLiveData<>();
        Call<JsonObject> call = RetrofitClient.api().getLocationResult(jsonObject);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    JsonParser jsonParser = new JsonParser();
                    JsonObject obj = (JsonObject) jsonParser.parse(response.body().get("data").toString());
                    JsonArray jsonArray = (JsonArray) obj.get("dataList");

//                    if (jsonArray.size() > 0){
//                        for(int i=0; i<jsonArray.size(); i++){
//                            JsonObject jsonObj = (JsonObject) jsonArray.get(i);
//                            Log.d("confirm", "getAuthNumResult() 응답 성공0 ->" + jsonObj.get("town"));
//
//                        }
//                        // StudyingAzae, Soodal 출력
//                    }

//                    Log.d("confirm", "getAuthNumResult() 응답 성공0 ->" + response.body().get("data").toString());
                    Log.d("confirm", "getAuthNumResult() 응답 성공 ->" + jsonArray);
                    dataList.setValue(jsonArray);
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
