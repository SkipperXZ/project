package com.example.droneapp.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.droneapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightViewHolder> {


    @NonNull
    @Override
    public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FlightViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class FlightViewHolder extends  RecyclerView.ViewHolder{

        ImageView imageView ;
        TextView imageTitle;
        public FlightViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
}


