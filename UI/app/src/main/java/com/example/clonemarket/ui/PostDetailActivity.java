package com.example.clonemarket.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.example.clonemarket.R;
import com.example.clonemarket.data.PreferenceManager;
import com.example.clonemarket.databinding.ActivityPostDetailBinding;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PostDetailActivity extends AppCompatActivity {

    private ActivityPostDetailBinding binding;

    PostViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        binding = ActivityPostDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(PostViewModel.class);

        String postNum = getIntent().getStringExtra("postNum");



        viewModel.getPostDetailResult(PreferenceManager.getString(this, "accessToken"), postNum);

        viewModel.response3.observe(this, new Observer<JsonElement>() {
            @Override
            public void onChanged(JsonElement jsonElement) {
                if(!jsonElement.isJsonNull()){
                    binding.title.setText(jsonElement.getAsJsonObject().get("title").toString());
                    binding.nickName.setText(jsonElement.getAsJsonObject().get("nickName").toString());
                    binding.content.setText(jsonElement.getAsJsonObject().get("content").toString());
                    binding.location.setText(jsonElement.getAsJsonObject().get("location").toString());
                }
            }
        });

    }
}