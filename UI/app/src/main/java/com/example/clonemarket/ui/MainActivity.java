package com.example.clonemarket.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.clonemarket.R;
import com.example.clonemarket.databinding.ActivityLocationBinding;
import com.example.clonemarket.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    Fragment home, town, chattingRoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        home = new HomeFragment();
        town = new TownInfoFragment();
        chattingRoom = new ChattingRoomFragment();

        getSupportFragmentManager().beginTransaction().add(binding.mainFragment.getId(), home).commit();

        binding.bottomMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(binding.mainFragment.getId(), home).commit();
                        return true;
                    case R.id.town:
                        getSupportFragmentManager().beginTransaction().replace(binding.mainFragment.getId(), town).commit();
                        return true;
                    case R.id.chatting:
                        getSupportFragmentManager().beginTransaction().replace(binding.mainFragment.getId(), chattingRoom).commit();
                        return true;
//                    case R.id.my:
                }
                return false;
            }
        });
    }

}