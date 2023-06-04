package com.example.clonemarket.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.clonemarket.R;
import com.example.clonemarket.databinding.ActivityPostDetailBinding;

public class PostDetailActivity extends AppCompatActivity {

    private ActivityPostDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        binding = ActivityPostDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String postNum = getIntent().getStringExtra("postNum");



    }
}