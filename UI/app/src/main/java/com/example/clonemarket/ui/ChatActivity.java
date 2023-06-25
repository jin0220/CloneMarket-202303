package com.example.clonemarket.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.clonemarket.R;
import com.example.clonemarket.databinding.ActivityChatBinding;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;

    ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new ChatAdapter();
        binding.recyclerView.setAdapter(adapter);

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.content.getText().length() > 0) {
                    if (adapter.dataList.isEmpty()) {
                        
                    } else {

                    }

                    binding.content.setText("");
                }
            }
        });
    }

}