package com.example.clonemarket.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.clonemarket.R;
import com.example.clonemarket.data.model.LocationDto;
import com.example.clonemarket.databinding.ActivityLocationBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity {

    private ActivityLocationBinding binding;

    double cur_lat, cur_lon;

    LocationManager locationManager;
    LocationListener locationListener;

    LocationViewModel viewModel;

    LocationAdapter adapter;

    List<LocationDto> list = new ArrayList<>();

    int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new LocationAdapter();
        binding.recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(LocationViewModel.class);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        page = 0;

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                cur_lat = location.getLatitude(); // 위도
                cur_lon = location.getLongitude(); // 경도

                Log.d("confirm", "위도: " + cur_lat);
                Log.d("confirm", "경도: " + cur_lon);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("page", page);
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

        binding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    page++;

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("page", page);
                    jsonObject.addProperty("latitude", cur_lat);
                    jsonObject.addProperty("longitude", cur_lon);

                    sendLocationInfo(jsonObject);
                }
            }
        });

        // 동네 선택하면 휴대폰 인증 화면 이동
        adapter.setOnItemClickListener(new LocationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, String userLocation) {
                Intent intent = new Intent(getApplicationContext(), LoginPhoneActivity.class);
                intent.putExtra("userLocation", userLocation);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
    }

    public void sendLocationInfo(JsonObject jsonObject) {
        viewModel.getLocationResult(jsonObject);
        viewModel.response.observe(LocationActivity.this, new Observer<JsonArray>() {
            @Override
            public void onChanged(JsonArray jsonElements) {
                if (!jsonElements.isJsonNull()) {
                    for(int i = 0; i < jsonElements.size(); i++){
                        JsonObject jsonObject1 = (JsonObject) jsonElements.get(i);

                        LocationDto data = new LocationDto();
                        data.setDistrict(jsonObject1.get("district").getAsString());
                        data.setCity(jsonObject1.get("city").getAsString());
                        data.setTown(jsonObject1.get("town").getAsString());
//                        data.setTownship(jsonObject1.get("township").getAsString());
//                        data.setVillage(jsonObject1.get("village").getAsString());

//                        list.add(data);
                        adapter.addData(data);
                    }
//                    adapter.addData(list);
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }
}