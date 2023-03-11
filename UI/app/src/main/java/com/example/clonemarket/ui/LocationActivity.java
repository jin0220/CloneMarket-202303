package com.example.clonemarket.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.clonemarket.R;
import com.example.clonemarket.databinding.ActivityLocationBinding;

public class LocationActivity extends AppCompatActivity {

    private ActivityLocationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}