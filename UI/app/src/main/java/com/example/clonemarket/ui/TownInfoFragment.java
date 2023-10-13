package com.example.clonemarket.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clonemarket.R;
import com.example.clonemarket.data.PreferenceManager;
import com.example.clonemarket.data.model.TownInfoDto;
import com.example.clonemarket.databinding.FragmentChattingRoomBinding;
import com.example.clonemarket.databinding.FragmentTownInfoBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;


public class TownInfoFragment extends Fragment {
    private FragmentTownInfoBinding binding;

    TownInfoAdapter adapter;
    TownInfoViewModel viewModel;

    int page;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTownInfoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        adapter = new TownInfoAdapter();
        binding.recyclerView.setAdapter(adapter);

        page = 0;

        viewModel = new TownInfoViewModel();

//        JsonObject jsonObject = new JsonObject();
        //jsonObject.addProperty("userPhone", PreferenceManager.getString(getContext(), "phoneNum"));
//        jsonObject.addProperty("page", page);

        viewModel.getInfoList(page);

        viewModel.responseArr.observe(getViewLifecycleOwner(), new Observer<JsonArray>() {
            @Override
            public void onChanged(JsonArray jsonArray) {
                List<TownInfoDto> list = new ArrayList<>();
                if (jsonArray != null) {
                    if (!jsonArray.isJsonNull()) {
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JsonObject obj = (JsonObject) jsonArray.get(i);

                            TownInfoDto data = new TownInfoDto();
                            data.setNum(Long.parseLong(obj.get("num").getAsString()));
                            data.setWriteUser(obj.get("writer").getAsString());
                            data.setTitle(obj.get("title").getAsString());
                            data.setContent(obj.get("content").getAsString());
                            data.setTown(obj.get("town").getAsString());
                            data.setViewCnt(Long.parseLong(obj.get("viewCnt").getAsString()));

                            list.add(data);
                        }
                    }
                    adapter.dataList = list;
                    adapter.notifyDataSetChanged();
                }
            }
        });

        adapter.setOnItemClickListener(new TownInfoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, Long num) {
                Intent intent = new Intent(getContext(), InfoDetailActivity.class);
                intent.putExtra("infoNum", num);
                startActivity(intent);
            }
        });

        binding.floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), WriteInfoActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}