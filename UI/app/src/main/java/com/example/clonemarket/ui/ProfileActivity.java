package com.example.clonemarket.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.clonemarket.R;
import com.example.clonemarket.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}