package com.example.droneapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.droneapp.activity.GalleryActivity;
import com.example.droneapp.activity.PhotoViewActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private int[] images;
    public  ImageAdapter(int[] images){
        this.images = images;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_layout,parent,false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder( ImageViewHolder holder, int position) {
        final int image_id = images[position];
        holder.imageView.setImageResource(image_id);
        holder.imageTitle.setText("image :"+position);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(v.getContext(),PhotoViewActivity.class);
                t.putExtra("imageID",image_id);
                v.getContext().startActivity(t);
            }
        });

    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class ImageViewHolder extends  RecyclerView.ViewHolder{

        ImageView imageView ;
        TextView imageTitle;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            imageTitle = itemView.findViewById(R.id.imageTitle);
        }
    }
}
