package com.example.clonemarket.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.clonemarket.R;
import com.example.clonemarket.data.model.PostDto;
import com.example.clonemarket.databinding.ActivityLocationBinding;
import com.example.clonemarket.databinding.ActivityWritePostBinding;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class WritePostActivity extends AppCompatActivity {

    private ActivityWritePostBinding binding;

    PostViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);
        binding = ActivityWritePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("중고거래 글쓰기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = new ViewModelProvider(this).get(PostViewModel.class);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.write_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.store:
                postSave();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void postSave() {
        PostDto post = new PostDto();
        post.setTitle(binding.title.toString());
        post.setContent(binding.content.toString());
        post.setPrice(binding.price.toString());

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HHmmss");
        String date = sdfDate.format(System.currentTimeMillis());
        String time = sdfTime.format(System.currentTimeMillis());
        post.setDate(date);
        post.setTime(time);

        Log.d("confirm", binding.title.getText().toString());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("title", binding.title.getText().toString());
        jsonObject.addProperty("content", binding.content.getText().toString());
        jsonObject.addProperty("price", binding.price.getText().toString());
        jsonObject.addProperty("date", date);
        jsonObject.addProperty("time", time);

        viewModel.setPost(jsonObject);


        viewModel.response2.observe(WritePostActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                if (result) {
                    Toast.makeText(WritePostActivity.this, "글이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(WritePostActivity.this, "서버 요청 오류로 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}