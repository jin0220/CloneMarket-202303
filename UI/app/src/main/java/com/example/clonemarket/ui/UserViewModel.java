package com.example.clonemarket.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.clonemarket.data.repository.UserRepository;
import com.google.gson.JsonObject;

public class UserViewModel extends ViewModel {
    private static UserRepository repository = new UserRepository();

    public MutableLiveData<JsonObject> response = new MutableLiveData<>();
    public void getAuthNumResult(JsonObject jsonObject) {
        repository.getAuthNumResult(jsonObject);
        response = repository.dataList;
    }
}
