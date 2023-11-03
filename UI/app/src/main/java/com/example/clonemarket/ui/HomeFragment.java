package com.example.clonemarket.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clonemarket.data.PreferenceManager;
import com.example.clonemarket.data.api.RetrofitClient;
import com.example.clonemarket.data.model.PostDto;
import com.example.clonemarket.databinding.FragmentHomeBinding;
import com.example.clonemarket.lib.DateParse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    HomeAdapter adapter;

    PostViewModel viewModel;

    int page;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        DateParse dateParse = new DateParse();

        adapter = new HomeAdapter();
        binding.recyclerView.setAdapter(adapter);

        page = 0;

        viewModel = new PostViewModel();
        String accessToken = PreferenceManager.getString(getContext(), "accessToken");
        String userPhone = PreferenceManager.getString(getContext(), "phoneNum");

//        if(PreferenceManager.getBoolean(getContext(), "login")) {
//
//        }

        viewModel.getPost(accessToken, userPhone, page);

        viewModel.response.observe(getViewLifecycleOwner(), new Observer<JsonArray>() {
            @Override
            public void onChanged(JsonArray jsonElements) {
                if(!jsonElements.isJsonNull()){
                    for(int i = 0; i < jsonElements.size(); i++) {
                        JsonObject jsonObject1 = (JsonObject) jsonElements.get(i);

                        PostDto data = new PostDto();
                        data.setNum(jsonObject1.get("num").getAsInt() + "");
                        data.setTitle(jsonObject1.get("title").getAsString());
                        data.setTown(jsonObject1.get("location").getAsString());
//                        data.setTime(jsonObject1.get("time").getAsString());
                        data.setPrice(jsonObject1.get("price").getAsString());
                        if(!jsonObject1.get("img1").isJsonNull()) {
                            String imgUrl = RetrofitClient.BASE_URL + "profile/" + jsonObject1.get("img1").getAsString();
                            data.setImg(imgUrl);
                        }

                        SimpleDateFormat date1 = new SimpleDateFormat("yyyy-MM-dd");

                        Date today = new Date(System.currentTimeMillis());

                        if(jsonObject1.get("date").getAsString().equals(date1.format(today)))
                            data.setTime(jsonObject1.get("time").getAsString());
                        else {
                            int dif = dateParse.dateDif(jsonObject1.get("date").getAsString(), date1.format(today));
                            if(dif < 30)
                                data.setTime(dif+"일전");
                            else if(dif < 365)
                                data.setTime(dif/30+"개월전");
                            else
                                data.setTime(dif/365+"년전");
                        }

                        adapter.addData(data);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

        adapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, String num) {
                Intent intent = new Intent(getContext(), PostDetailActivity.class);
                intent.putExtra("postNum", num);
                startActivity(intent);
            }
        });


        binding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    page++;

                    viewModel.getPost(accessToken, userPhone, page);
                }
            }
        });


        binding.floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), WritePostActivity.class);
                startActivity(intent);
            }
        });



        return view;
    }


}