package com.example.clonemarket.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.clonemarket.data.repository.ChatRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ChatViewModel extends ViewModel {
    private static ChatRepository repository = new ChatRepository();

    public MutableLiveData<JsonArray> response = new MutableLiveData<>();
    public void getChattingRoom(JsonObject jsonObject){
        repository.getChattingRoom(jsonObject);
        response = repository.dataList;
    }
}
