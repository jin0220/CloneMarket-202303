package com.example.clonemarket.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.clonemarket.data.repository.UserRepository;
import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserViewModel extends ViewModel {
    private static UserRepository repository = new UserRepository();

    public MutableLiveData<JsonObject> response = new MutableLiveData<>();
    public void getAuthNumResult(JsonObject jsonObject) {
        repository.getAuthNumResult(jsonObject);
        response = repository.dataList;
    }

    public MutableLiveData<String> response2 = new MutableLiveData<>();
    public void getLoginResult(JsonObject jsonObject) {
        repository.getLoginResult(jsonObject);
        response2 = repository.dataList2;
    }

    public void setProfile(Map<String, RequestBody> requestMap, MultipartBody.Part file){
        repository.setProfile(requestMap, file);
        response2 = repository.dataList2;
    }
}
