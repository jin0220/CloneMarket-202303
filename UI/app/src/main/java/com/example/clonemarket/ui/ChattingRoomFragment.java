package com.example.clonemarket.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clonemarket.R;
import com.example.clonemarket.data.PreferenceManager;
import com.example.clonemarket.data.model.ChatDto;
import com.example.clonemarket.databinding.FragmentChattingRoomBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;


public class ChattingRoomFragment extends Fragment {
    private FragmentChattingRoomBinding binding;

    ChattingRoomAdapter adapter;
    ChatViewModel viewModel;

    int page;
    Long roomId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChattingRoomBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        adapter = new ChattingRoomAdapter();
        binding.recyclerView.setAdapter(adapter);

        page = 0;

        viewModel = new ChatViewModel();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userPhone", PreferenceManager.getString(getContext(), "phoneNum"));
        jsonObject.addProperty("page", page);

        viewModel.getRoomList(jsonObject);

        viewModel.response2.observe(getViewLifecycleOwner(), new Observer<JsonArray>() {
            @Override
            public void onChanged(JsonArray jsonArray) {
                List<ChatDto> list = new ArrayList<>();
                if(!jsonArray.isJsonNull()){
                    for(int i = 0; i < jsonArray.size(); i++) {
                        JsonObject jsonObject1 = (JsonObject) jsonArray.get(i);

                        roomId = jsonObject1.get("roomId").getAsLong();

                        ChatDto data = new ChatDto();
                        data.setRoomId(roomId);
//                        data.setPhone(jsonObject1.get("sellerUser").getAsString());
                        data.setNickName(jsonObject1.get("sellerUser").getAsString());
//                        data.setContent(jsonObject1.get("contents").getAsString());
//                        data.setTime(jsonObject1.get("sendTime").getAsString());

                        list.add(data);
                    }
                }
                adapter.dataList = list;
                adapter.notifyDataSetChanged();
            }
        });

        adapter.setOnItemClickListener(new ChattingRoomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, Long roomId) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("postNum", "null");
                intent.putExtra("roomId", roomId);
                startActivity(intent);
            }
        });


        return view;
    }
}