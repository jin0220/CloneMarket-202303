package com.example.clonemarket.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.clonemarket.data.repository.PostRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PostViewModel extends ViewModel {
    private static PostRepository repository = new PostRepository();

    public MutableLiveData<JsonArray> response = new MutableLiveData<>();
    public void getPost(String accessToken, String userPhone, int page) {
        repository.getPostResult(accessToken, userPhone, page);
        response = repository.dataListArr;
    }

    public MutableLiveData<Boolean> response2 = new MutableLiveData<>();
    public void setPost(Map<String, RequestBody> requestMap, MultipartBody.Part file) {
        repository.setPost(requestMap, file);
        response2 = repository.dataList1;
    }

    public MutableLiveData<JsonElement> response3 = new MutableLiveData<>();
    public void getPostDetailResult(String accessToken, String userPhone, String postNum) {
        repository.getPostDetailResult(accessToken, userPhone, postNum);
        response3 = repository.dataList2;
    }
}

