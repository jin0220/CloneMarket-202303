package com.example.clonemarket.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.clonemarket.data.api.RetrofitClient;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    public MutableLiveData<JsonObject> dataList =  new MutableLiveData<>();

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


    public MutableLiveData<Boolean> dataList2 =  new MutableLiveData<>();

    public void getLoginResult(JsonObject jsonObject){

        Call<JsonObject> call = RetrofitClient.api().getLoginResult(jsonObject);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    Log.d("confirm", "getLoginResult() 응답 성공 ->" + response.body().get("data").getAsJsonObject().get("result"));
                    dataList2.setValue(response.body().get("data").getAsJsonObject().get("result").getAsBoolean());
                }
                else{
                    Log.d("confirm", "getLoginResult() 응답 실패");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                Log.d("confirm", "getLoginResult() 통신 실패");
            }
        });

    }

    // 프로필 설정
    public MutableLiveData<Boolean> dataList3 =  new MutableLiveData<>();

    public void setProfile(String nickName, MultipartBody.Part file) {
        Call<JsonObject> call = RetrofitClient.api().setProfile(nickName, file);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    Log.d("confirm", "setProfile() 응답 성공 -> " + response.body().get("data").toString());
//                    dataList3.setValue();
                }
                else{
                    Log.d("confirm", "setProfile() 응답 실패");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                Log.d("confirm", "setProfile() 통신 실패");
            }
        });
    }

}
