package com.example.clonemarket.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clonemarket.data.model.ChatDto;
import com.example.clonemarket.databinding.RecyclerviewChatBinding;

import java.util.ArrayList;
import java.util.List;

public class ChattingRoomAdapter extends RecyclerView.Adapter<ChattingRoomAdapter.ViewHolder> {

    private RecyclerviewChatBinding binding;

    List<ChatDto> dataList = new ArrayList<>();

    public interface OnItemClickListener{
        void onItemClick(View v, int position, Long roomId);
    }

    private ChattingRoomAdapter.OnItemClickListener mListener = null ;

    public void setOnItemClickListener(ChattingRoomAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RecyclerviewChatBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ChattingRoomAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatDto data = dataList.get(position);
        binding.nickName.setText(data.getNickName());
//        binding.town.setText(data.get);
//        binding.time.setText(data.getTime());
        binding.content.setText(data.getContent());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull RecyclerviewChatBinding itemView) {
            super(itemView.getRoot());

            itemView.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    ChatDto data = dataList.get(position);
                    Long roomId = data.getRoomId();
                    if(position != RecyclerView.NO_POSITION){
                        if(mListener != null) {
                            mListener.onItemClick(view, position, roomId);
                        }
                    }
                }
            });
        }
    }

    public void addData(ChatDto list){
        dataList.add(list);
    }

}
