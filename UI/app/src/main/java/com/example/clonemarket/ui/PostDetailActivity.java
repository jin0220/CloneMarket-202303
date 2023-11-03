package com.example.clonemarket.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.clonemarket.R;
import com.example.clonemarket.data.PreferenceManager;
import com.example.clonemarket.data.api.RetrofitClient;
import com.example.clonemarket.databinding.ActivityPostDetailBinding;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

public class PostDetailActivity extends AppCompatActivity {

    private ActivityPostDetailBinding binding;

    PostViewModel viewModel;
    String nickName, profile=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        binding = ActivityPostDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(PostViewModel.class);

        String postNum = getIntent().getStringExtra("postNum");
        String sellerUser = null;

        viewModel.getPostDetailResult(PreferenceManager.getString(this, "accessToken"), PreferenceManager.getString(this, "phoneNum"), postNum);

        viewModel.response3.observe(this, new Observer<JsonElement>() {
            @Override
            public void onChanged(JsonElement jsonElement) {
                if(!jsonElement.isJsonNull()){
                    nickName = jsonElement.getAsJsonObject().get("nickName").getAsString();

                    if(!jsonElement.getAsJsonObject().get("profile").getAsString().equals("") && !jsonElement.getAsJsonObject().get("profile").getAsString().isEmpty()) {
                        profile = RetrofitClient.BASE_URL + "profile/" + jsonElement.getAsJsonObject().get("profile").getAsString();
                        Picasso.get().load(profile).into(binding.imageView);
                    }

                    binding.title.setText(jsonElement.getAsJsonObject().get("title").getAsString());
                    binding.nickName.setText(nickName);
                    binding.content.setText(jsonElement.getAsJsonObject().get("content").getAsString());
                    binding.location.setText(jsonElement.getAsJsonObject().get("location").getAsString());
                    binding.sellerUser.setText(jsonElement.getAsJsonObject().get("sellerUser").getAsString());

                    for(int i = 1; i <= 10; i++) {
                        if (!jsonElement.getAsJsonObject().get("img" + i).isJsonNull()) {
                            String imgUrl = RetrofitClient.BASE_URL + "profile/" + jsonElement.getAsJsonObject().get("img" + i).getAsString();
                            Picasso.get().load(imgUrl).into(binding.img);
                        }
                    }

                    binding.chatCnt.setText(jsonElement.getAsJsonObject().get("chatCnt").getAsString());
                    binding.likeCnt.setText(jsonElement.getAsJsonObject().get("likeCnt").getAsString());
                    binding.viewCnt.setText(jsonElement.getAsJsonObject().get("viewCnt").getAsString());
                }
            }
        });

        binding.chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("postNum", postNum);
                intent.putExtra("sellerUser", binding.sellerUser.getText());
                intent.putExtra("nickName", nickName);
                intent.putExtra("profile",profile);
                startActivity(intent);
            }
        });

    }
}