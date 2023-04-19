package com.example.clonemarket.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clonemarket.data.model.LocationDto;
import com.example.clonemarket.databinding.RecyclerviewLocationBinding;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private RecyclerviewLocationBinding binding;

    List<LocationDto> dataList = new ArrayList<>();

    // 뷰홀더 클래스 - 뷰를 재활용하기 때문에 각 뷰의 내용을 담아둘 뷰 홀더가 필요
    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull RecyclerviewLocationBinding itemView) {
            super(itemView.getRoot());
        }

        public TextView getTextView() {
            return binding.textView;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RecyclerviewLocationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LocationDto data = dataList.get(position);

        holder.getTextView().setText(data.getDistrict() + " "
                + data.getCity() + " "
                + data.getTown() + " "
//                + data.getTownship() + " "
//                + data.getVillage() + " "
        );
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addData(LocationDto list) {
        dataList.add(list);

        Log.d("confirm", list.getTown() + "    " + dataList.size());

//        notifyDataSetChanged();
    }
}
