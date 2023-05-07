package com.example.clonemarket.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.clonemarket.data.repository.UserRepository;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserViewModel extends ViewModel {
    private static UserRepository repository = new UserRepository();

    public MutableLiveData<JsonObject> response = new MutableLiveData<>();
    public void getAuthNumResult(JsonObject jsonObject) {
        repository.getAuthNumResult(jsonObject);
        response = repository.dataList;
    }

    public MutableLiveData<Boolean> response2 = new MutableLiveData<>();
    public void getLoginResult(JsonObject jsonObject) {
        repository.getLoginResult(jsonObject);
        response2 = repository.dataList2;
    }

    public MutableLiveData<Boolean> response3 = new MutableLiveData<>();
    public void setProfile(String nickName, MultipartBody.Part file){
        repository.setProfile(nickName, file);
        response3 = repository.dataList3;
    }
}
