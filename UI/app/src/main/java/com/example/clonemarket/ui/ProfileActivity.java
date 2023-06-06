package com.example.clonemarket.ui;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.example.clonemarket.R;
import com.example.clonemarket.databinding.ActivityProfileBinding;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;

    UserViewModel viewModel;
    Uri imgFile;
    String fileName;

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    imgFile = uri;
                    binding.imageView.setImageURI(imgFile);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    fileName = sdf.format(System.currentTimeMillis());
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        String phoneNum = getIntent().getStringExtra("phoneNum");

        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imgFile != null) {
                    File file = new File(getPathFromUri(imgFile));
                    if (file.exists()) {
                        Log.d("confirm", "파일이 존재합니다.");

                        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
                        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), fileBody);

                        RequestBody phoneNumBody = RequestBody.create(MediaType.parse("text/plain"), phoneNum);
                        RequestBody nickNameBody = RequestBody.create(MediaType.parse("text/plain"), binding.editText.getText().toString());

                        Map<String, RequestBody> requestMap = new HashMap<>();
                        requestMap.put("phoneNum", phoneNumBody);
                        requestMap.put("nickName", nickNameBody);

                        viewModel.setProfile(requestMap, filePart);

//                        viewModel.setProfile(phoneNum, binding.editText.getText().toString(), filePart);

                        viewModel.response3.observe(ProfileActivity.this, new Observer<Boolean>() {
                            @Override
                            public void onChanged(Boolean result) {
                                if (result) {
                                    Log.d("confirm", "success");
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Log.d("confirm", "fail");
                                }
                            }
                        });
                    } else {
                        Log.d("confirm", "파일이 존재하지 않습니다.");
                    }
                } else {
                    Log.d("confirm", "imgFile is null");
                }
            }
        });
    }

    private String getPathFromUri(Uri uri) {
        String path = "";
        Cursor cursor = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.RELATIVE_PATH}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            @SuppressLint("Range") String relativePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.RELATIVE_PATH));

            path = relativePath + displayName;
            cursor.close();
        }

        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + path;
    }
}