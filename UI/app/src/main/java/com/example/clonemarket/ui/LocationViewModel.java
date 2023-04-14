package com.example.clonemarket.ui;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.clonemarket.data.repository.LocationRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class LocationViewModel extends ViewModel {
    private static LocationRepository repository = new LocationRepository();

    public MutableLiveData<JsonArray> response = new MutableLiveData<>();
    public void getLocationResult(JsonObject jsonObject) {
        Log.d("confirm", jsonObject.get("latitude") + "");
        repository.getLocationResult(jsonObject);
        response = repository.dataList;
    }
}
