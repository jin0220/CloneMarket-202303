package com.example.clonemarket.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clonemarket.R;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Timer;
import java.util.TimerTask;

public class LoginPhoneActivity extends AppCompatActivity {
    TextView editTextPhone, emailLogin, minuteText, secondText;
    EditText editTextAuthNum;
    Button msg, confirm;
    LinearLayout authNum;
    int minute, second;

    UserViewModel viewModel;
    String responseAuthNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);

        editTextPhone = findViewById(R.id.editTextPhone2);
        emailLogin = findViewById(R.id.textView3);
        msg = findViewById(R.id.button2);
        authNum = findViewById(R.id.authNum);
        minuteText = findViewById(R.id.timer);
        secondText = findViewById(R.id.timer2);
        editTextAuthNum = findViewById(R.id.editTextAuthNum);
        confirm = findViewById(R.id.button3);


        editTextPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (editTextPhone.getText().length() >= 10) {
                    msg.setTextColor(Color.BLACK);
                    msg.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authNum.setVisibility(View.VISIBLE);
                minuteText.setText("03");
                secondText.setText("00");

                minute = Integer.parseInt(minuteText.getText().toString());
                second = Integer.parseInt(secondText.getText().toString());

                msg.setText("인증문자 다시 받기");
                confirm.setVisibility(View.VISIBLE);

                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        LoginPhoneActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                if (second != 0) {
                                    second--;
                                } else if (minute != 0) {
                                    minute--;
                                    second = 59;
                                }

                                minuteText.setText("0" + minute);

                                if (second > 9)
                                    secondText.setText(Integer.toString(second));
                                else
                                    secondText.setText("0" + second);

                                if (minute == 0 && second == 0) {
                                    timer.cancel(); // 타이머 종료
                                }
                            }
                        });
                    }
                };

                timer.schedule(timerTask, 0, 1000); //Timer 실행

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("phoneNum", editTextPhone.getText().toString());
                jsonObject.addProperty("content", "test");

                viewModel.getAuthNumResult(jsonObject);

                viewModel.response.observe(LoginPhoneActivity.this, new Observer<JsonObject>() {
                    @Override
                    public void onChanged(JsonObject result) {
                        if (!result.get("authNum").getAsString().equals(null)) {
                            Log.d("confirm", "success");
                            responseAuthNum = result.get("authNum").getAsString();
                        } else {
                            Log.d("confirm", "fail");
                            Toast.makeText(getApplicationContext(), "서버 요청 오류로 잠시 후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        editTextAuthNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editTextAuthNum.getText().length() >= 6) {
                    confirm.setBackground(getResources().getDrawable(R.drawable.btn_2));
                    confirm.setTextColor(Color.WHITE);
                    confirm.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextAuthNum.getText().toString().equals(responseAuthNum)) {

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("phoneNum", editTextPhone.getText().toString());

                    viewModel.getLoginResult(jsonObject);

                    viewModel.response2.observe(LoginPhoneActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean result) {
                            if (result) {
                                Log.d("confirm", "success");

                                Intent intent = new Intent(LoginPhoneActivity.this, MainActivity.class);
                                startActivity(intent);

                            } else {
                                Log.d("confirm", "fail");
                                Toast.makeText(getApplicationContext(), "서버 요청 오류로 잠시 후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });


        emailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPhoneActivity.this, LoginEmailActivity.class);
                startActivity(intent);
            }
        });

    }
}