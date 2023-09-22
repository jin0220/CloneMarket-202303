package com.example.clonemarket.ui;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.clonemarket.R;
import com.example.clonemarket.data.PreferenceManager;
import com.example.clonemarket.data.model.PostDto;
import com.example.clonemarket.databinding.ActivityLocationBinding;
import com.example.clonemarket.databinding.ActivityWritePostBinding;
import com.google.gson.JsonObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class WritePostActivity extends AppCompatActivity {

    private ActivityWritePostBinding binding;

    PostViewModel viewModel;

    Uri imgFile;
    String fileName;

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    imgFile = uri;
                    Log.d("confirm", "imgFile : " + imgFile);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    fileName = sdf.format(System.currentTimeMillis());
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);
        binding = ActivityWritePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("중고거래 글쓰기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = new ViewModelProvider(this).get(PostViewModel.class);

        binding.imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
            }
        });

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
        MultipartBody.Part filePart = null;
        if (imgFile != null) {
            File file = new File(getPathFromUri(imgFile));
            if (file.exists()) {
                Log.d("confirm", "파일이 존재합니다.");

                RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
                filePart = MultipartBody.Part.createFormData("file", file.getName(), fileBody);

            }else {
                Log.d("confirm", "파일이 존재하지 않습니다.");
            }
        } else {
            Log.d("confirm", "imgFile is null");
        }

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HHmmss");
        String date = sdfDate.format(System.currentTimeMillis());
        String time = sdfTime.format(System.currentTimeMillis());

        RequestBody phoneNumBody = RequestBody.create(MediaType.parse("text/plain"), PreferenceManager.getString(getApplicationContext(),"phoneNum"));
        RequestBody titleBody = RequestBody.create(MediaType.parse("text/plain"), binding.title.getText().toString());
        RequestBody contentBody = RequestBody.create(MediaType.parse("text/plain"), binding.content.getText().toString());
        RequestBody priceBody = RequestBody.create(MediaType.parse("text/plain"), binding.price.getText().toString());
        RequestBody dateBody = RequestBody.create(MediaType.parse("text/plain"), date);
        RequestBody timeBody = RequestBody.create(MediaType.parse("text/plain"), time);

        Map<String, RequestBody> requestMap = new HashMap<>();
        requestMap.put("phoneNum", phoneNumBody);
        requestMap.put("title", titleBody);
        requestMap.put("content", contentBody);
        requestMap.put("price", priceBody);
        requestMap.put("date", dateBody);
        requestMap.put("time", timeBody);

        viewModel.setPost(requestMap, filePart);

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

//        PostDto post = new PostDto();
//        post.setTitle(binding.title.toString());
//        post.setContent(binding.content.toString());
//        post.setPrice(binding.price.toString());
//
//        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
//        SimpleDateFormat sdfTime = new SimpleDateFormat("HHmmss");
//        String date = sdfDate.format(System.currentTimeMillis());
//        String time = sdfTime.format(System.currentTimeMillis());
//        post.setDate(date);
//        post.setTime(time);
//
//        Log.d("confirm", binding.title.getText().toString());
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("phoneNum", PreferenceManager.getString(getApplicationContext(),"phoneNum"));
//        jsonObject.addProperty("title", binding.title.getText().toString());
//        jsonObject.addProperty("content", binding.content.getText().toString());
//        jsonObject.addProperty("price", binding.price.getText().toString());
//        jsonObject.addProperty("date", date);
//        jsonObject.addProperty("time", time);
//
//        viewModel.setPost(jsonObject);
//
//
//        viewModel.response2.observe(WritePostActivity.this, new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean result) {
//                if (result) {
//                    Toast.makeText(WritePostActivity.this, "글이 저장되었습니다.", Toast.LENGTH_SHORT).show();
//                    finish();
//                } else {
//                    Toast.makeText(WritePostActivity.this, "서버 요청 오류로 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

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