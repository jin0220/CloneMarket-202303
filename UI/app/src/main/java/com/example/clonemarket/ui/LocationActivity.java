package com.example.clonemarket.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.example.clonemarket.R;
import com.example.clonemarket.databinding.ActivityLocationBinding;
import com.google.gson.JsonObject;

import java.security.Provider;

public class LocationActivity extends AppCompatActivity {

    private ActivityLocationBinding binding;

    double cur_lat, cur_lon;

    LocationManager locationManager;
    LocationListener locationListener;

    LocationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(LocationViewModel.class);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                cur_lat = location.getLatitude(); // 위도
                cur_lon = location.getLongitude(); // 경도

                Log.d("confirm", "위도: " + cur_lat);
                Log.d("confirm", "경도: " + cur_lon);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("latitude", cur_lat);
                jsonObject.addProperty("longitude", cur_lon);

                locationManager.removeUpdates(locationListener); // 위치 정보를 받은 후 locationListener 제거

                sendLocationInfo(jsonObject);
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 권한 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            Log.d("confirm", "권한없음. 권한요청. ");
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            Log.d("confirm", "위치 가져오기");
        }




        // 동네 선택하면 휴대폰 인증 화면 이동

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
    }

    public void sendLocationInfo(JsonObject jsonObject) {
        viewModel.getLocationResult(jsonObject);

        viewModel.response.observe(LocationActivity.this, new Observer<JsonObject>() {
            @Override
            public void onChanged(JsonObject jsonObject) {
                if(!jsonObject.isJsonNull()){
                    Log.d("confirm", jsonObject.get("latitude").toString());
                }
            }
        });

    }
}