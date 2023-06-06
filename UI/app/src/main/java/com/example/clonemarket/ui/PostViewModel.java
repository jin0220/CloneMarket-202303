package com.example.clonemarket.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.clonemarket.data.repository.PostRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class PostViewModel extends ViewModel {
    private static PostRepository repository = new PostRepository();

    public MutableLiveData<JsonArray> response = new MutableLiveData<>();
    public void getPost(String accessToken, int page) {
        repository.getPostResult(accessToken, page);
        response = repository.dataListArr;
    }

    public MutableLiveData<Boolean> response2 = new MutableLiveData<>();
    public void setPost(JsonObject jsonObject) {
        repository.setPost(jsonObject);
        response2 = repository.dataList1;
    }
}

