package com.example.clonemarket.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;

import com.example.clonemarket.R;
import com.example.clonemarket.databinding.ActivityInfoDetailBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class InfoDetailActivity extends AppCompatActivity {

    private ActivityInfoDetailBinding binding;

    TownInfoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Long num = getIntent().getLongExtra("infoNum", 0);

        viewModel = new TownInfoViewModel();

        viewModel.getInfo(num);
        viewModel.response.observe(this, new Observer<JsonElement>() {
            @Override
            public void onChanged(JsonElement jsonElement) {
                if(!jsonElement.isJsonNull()){
                    binding.title.setText(jsonElement.getAsJsonObject().get("title").getAsString());
                    binding.content.setText(jsonElement.getAsJsonObject().get("content").getAsString());
                    binding.location.setText(jsonElement.getAsJsonObject().get("town").getAsString());
                    binding.viewCnt.setText(jsonElement.getAsJsonObject().get("viewCnt").getAsString());
                    binding.commentCnt.setText(jsonElement.getAsJsonObject().get("commentCnt").getAsString());
                }
            }
        });
    }
}