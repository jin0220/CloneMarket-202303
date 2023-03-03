package com.example.clonemarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginPhoneActivity extends AppCompatActivity {
    TextView editTextPhone, emailLogin;
    Button msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);

        editTextPhone = findViewById(R.id.editTextPhone2);
        emailLogin = findViewById(R.id.textView3);
        msg = findViewById(R.id.button2);

        editTextPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if(editTextPhone.getText().length() >= 10){
                    msg.setTextColor(Color.BLACK);
                    msg.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if(editTextPhone.getText().length() >= 10) {
            msg.setEnabled(true);
            msg.setTextColor(Color.BLACK);
        }

        emailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPhoneActivity.this, LoginEmailActivity.class);
                startActivity(intent);
            }
        });

    }
}