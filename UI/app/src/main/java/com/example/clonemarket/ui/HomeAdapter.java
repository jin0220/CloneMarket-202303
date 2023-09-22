package com.example.clonemarket.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clonemarket.data.model.PostDto;
import com.example.clonemarket.databinding.RecyclerviewMainBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private RecyclerviewMainBinding binding;

    List<PostDto> dataList = new ArrayList<>();

    public interface OnItemClickListener{
        void onItemClick(View v, int position, String num) ;
    }

    private OnItemClickListener mListener = null ;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RecyclerviewMainBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HomeAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostDto data = dataList.get(position);
//        holder.getTextView().setText(data.getTitle());
        binding.postNum.setText(data.getNum());
        binding.title.setText(data.getTitle());
        binding.town.setText(data.getTown());
        binding.time.setText(data.getDate());
        binding.price.setText(data.getPrice());
//        binding.img.setImageURI(data.getImg());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull RecyclerviewMainBinding itemView) {
            super(itemView.getRoot());

            itemView.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    PostDto data = dataList.get(position);
                    String postNum = data.getNum();
                    if(position != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(view, position, postNum);
                        }
                    }
                }
            });
        }
        public TextView getTextView() {
            return binding.title;
        }
    }

    public void addData(PostDto list){
        dataList.add(list);
    }
}
