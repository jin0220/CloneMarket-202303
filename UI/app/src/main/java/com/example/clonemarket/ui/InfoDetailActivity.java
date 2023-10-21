package com.example.clonemarket.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.clonemarket.R;
import com.example.clonemarket.data.PreferenceManager;
import com.example.clonemarket.data.api.RetrofitClient;
import com.example.clonemarket.databinding.ActivityInfoDetailBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;

public class InfoDetailActivity extends AppCompatActivity {

    private ActivityInfoDetailBinding binding;

    TownInfoViewModel viewModel;

    Long num;
    String writer, user;
    String title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("동네생활");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        num = getIntent().getLongExtra("infoNum", 0);
        writer = getIntent().getStringExtra("writer");
        user = PreferenceManager.getString(this, "phoneNum");

        viewModel = new TownInfoViewModel();

        Log.d("confirm", writer);

        viewModel.getInfo(writer, num);
        viewModel.response.observe(this, new Observer<JsonElement>() {
            @Override
            public void onChanged(JsonElement jsonElement) {
                if(!jsonElement.isJsonNull()){
                    title = jsonElement.getAsJsonObject().get("title").getAsString();
                    content = jsonElement.getAsJsonObject().get("content").getAsString();

                    binding.nickName.setText(jsonElement.getAsJsonObject().get("nickName").getAsString());
                    if(!jsonElement.getAsJsonObject().get("profile").isJsonNull() || !jsonElement.getAsJsonObject().get("profile").getAsString().equals(""))
                        Picasso.get().load(RetrofitClient.BASE_URL + "profile/" + jsonElement.getAsJsonObject().get("profile").getAsString()).into(binding.imageView);
                    binding.title.setText(title);
                    binding.content.setText(content);
                    binding.location.setText(jsonElement.getAsJsonObject().get("town").getAsString());
                    binding.viewCnt.setText(jsonElement.getAsJsonObject().get("viewCnt").getAsString());
                    binding.commentCnt.setText(jsonElement.getAsJsonObject().get("commentCnt").getAsString());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(user.equals(writer))
            getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.delete:
                deletePost();
                return true;
            case R.id.modify:
                Intent intent = new Intent(this, WriteInfoActivity.class);
                intent.putExtra("mod_chk", true);
                intent.putExtra("num", num);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deletePost(){
        viewModel.deleteInfo(num);

        viewModel.responseStr.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("true")) {
                    finish();
                }
            }
        });
    }
}