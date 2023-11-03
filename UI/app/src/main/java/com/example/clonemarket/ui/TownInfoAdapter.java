package com.example.clonemarket.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clonemarket.data.model.TownInfoDto;
import com.example.clonemarket.databinding.RecyclerviewTownInfoBinding;

import java.util.ArrayList;
import java.util.List;

public class TownInfoAdapter extends RecyclerView.Adapter<TownInfoAdapter.ViewHolder> {

    private RecyclerviewTownInfoBinding binding;

    List<TownInfoDto> dataList = new ArrayList<>();

    public interface OnItemClickListener{
        void onItemClick(View v, int position, Long num, String writer);
    }

    private OnItemClickListener mListener = null ;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RecyclerviewTownInfoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TownInfoAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TownInfoDto data = dataList.get(position);
        binding.title.setText(data.getTitle());
        binding.content.setText(data.getContent());
        binding.town.setText(data.getTown());
        binding.time.setText(data.getTime());
        binding.viewCnt.setText(data.getViewCnt().toString());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull RecyclerviewTownInfoBinding itemView) {
            super(itemView.getRoot());

            itemView.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    TownInfoDto data = dataList.get(position);
                    Long num = data.getNum();
                    String writer = data.getWriteUser();
                    if(position != RecyclerView.NO_POSITION){
                        if(mListener != null) {
                            mListener.onItemClick(view, position, num, writer);
                        }
                    }
                }
            });
        }
    }

    public void addData(TownInfoDto list){
        dataList.add(list);
    }
}
