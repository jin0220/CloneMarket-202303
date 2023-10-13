package com.example.clonemarket.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.clonemarket.R;
import com.example.clonemarket.data.PreferenceManager;
import com.example.clonemarket.databinding.ActivityWriteInfoBinding;
import com.example.clonemarket.databinding.ActivityWritePostBinding;
import com.google.gson.JsonObject;

public class WriteInfoActivity extends AppCompatActivity {

    private ActivityWriteInfoBinding binding;

    TownInfoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWriteInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("동네정보 글쓰기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = new ViewModelProvider(this).get(TownInfoViewModel.class);


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
                infoSave();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void infoSave(){
        String title = binding.title.getText().toString();
        String content = binding.content.getText().toString();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("writer", PreferenceManager.getString(getApplicationContext(), "phoneNum"));
        jsonObject.addProperty("title", title);
        jsonObject.addProperty("content", content);

        viewModel.setInfo(jsonObject);

        viewModel.responseStr.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                boolean chk = Boolean.parseBoolean(s);

                if(chk){
                    Toast.makeText(WriteInfoActivity.this, "글이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(WriteInfoActivity.this, "서버 요청 오류로 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}