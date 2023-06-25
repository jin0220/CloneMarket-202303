package com.example.clonemarket.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clonemarket.R;
import com.example.clonemarket.data.PreferenceManager;
import com.example.clonemarket.data.model.ChatDto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<ChatDto> dataList = new ArrayList<>();

    String phoneNum;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        phoneNum = PreferenceManager.getString(parent.getContext(),"phoneNum");
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_left, parent, false);
            return new LeftHolder(view);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_right, parent, false);
            return new RightHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatDto chat = dataList.get(position);

        String time;
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

        if (Integer.parseInt(new SimpleDateFormat("HH").format(chat.getTime())) > 12) {
            time = "오후 " + sdf.format(chat.getTime());
        } else {
            time = "오전 " + sdf.format(chat.getTime());
        }

        if(chat.getPhone().equals(phoneNum)){
            LeftHolder leftHolder = (LeftHolder) holder;
            leftHolder.name.setText(chat.getNickName());
            leftHolder.content.setText(chat.getContent());
            leftHolder.time.setText(time);
        }
        else {
            RightHolder rightHolder = (RightHolder) holder;
            rightHolder.content.setText(chat.getContent());
            rightHolder.time.setText(time);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getPhone().equals(phoneNum) ? 0 : 1 ;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class LeftHolder extends RecyclerView.ViewHolder {
        TextView name, content, time;

        public LeftHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            content = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.time);
        }
    }

    public class RightHolder extends RecyclerView.ViewHolder {
        TextView  content, time;

        public RightHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.time);
        }
    }
}
