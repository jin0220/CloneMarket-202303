package com.example.clonemarket.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.clonemarket.data.api.RetrofitClient;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    RetrofitClient retrofitClient;

    // 인증번호
    public MutableLiveData<JsonObject> dataList =  new MutableLiveData<>();
    public void getAuthNumResult(JsonObject jsonObject) {
        retrofitClient = new RetrofitClient();

        Call<JsonObject> call = retrofitClient.api().getAuthNumResult(jsonObject);

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

    // 로그인
    public MutableLiveData<String> dataList2 =  new MutableLiveData<>();
    public void getLoginResult(JsonObject jsonObject){
        retrofitClient = new RetrofitClient();
        Call<JsonObject> call = retrofitClient.api().getLoginResult(jsonObject);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    Log.d("confirm", "getLoginResult() 응답 성공 ->" + response.body().get("data").getAsJsonObject().get("result"));
                    dataList2.setValue(response.body().get("data").getAsJsonObject().get("result").getAsString());
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
    public void setProfile(Map<String, RequestBody> requestMap, MultipartBody.Part file) {
        retrofitClient = new RetrofitClient();
        Call<JsonObject> call = retrofitClient.api().setProfile(requestMap, file);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    Log.d("confirm", "setProfile() 응답 성공 -> " + response.body().get("data").toString());
                    dataList3.setValue(response.body().get("data").getAsJsonObject().get("result").getAsBoolean());
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
