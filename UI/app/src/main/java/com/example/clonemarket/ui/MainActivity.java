package com.example.clonemarket.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.clonemarket.R;
import com.example.clonemarket.databinding.ActivityLocationBinding;
import com.example.clonemarket.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    Fragment home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        home = new HomeFragment();

        getSupportFragmentManager().beginTransaction().add(binding.mainFragment.getId(), home).commit();


    }
}