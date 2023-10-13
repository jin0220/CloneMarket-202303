package com.example.clonemarket.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.clonemarket.data.repository.TownInfoRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class TownInfoViewModel extends ViewModel {
    private static TownInfoRepository repository = new TownInfoRepository();

    public MutableLiveData<JsonArray> responseArr;
    public MutableLiveData<JsonElement> response;
    public MutableLiveData<String> responseStr;

    public void getInfoList(int page) {
        response = new MutableLiveData<>();

        repository.getInfoList(page);
        responseArr = repository.dataList;
    }

    public void getInfo(Long num){
        response = new MutableLiveData<>();

        repository.getInfo(num);
        response = repository.data;
    }

    public void setInfo(JsonObject jsonObject) {
        response = new MutableLiveData<>();

        repository.setInfo(jsonObject);
        responseStr = repository.result;
    }
}
