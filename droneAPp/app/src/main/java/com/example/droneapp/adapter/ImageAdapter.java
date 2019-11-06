package com.example.droneapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.droneapp.R;
import com.example.droneapp.activity.PhotoViewActivity;
import com.example.droneapp.ulity.Constant;

import java.io.ByteArrayOutputStream;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private List<String> imageUrls;
    private Context context;
    private final String BASE_DOWNLOAD_URL = Constant.BASE_API_URL+"downloadFile/";
    public  ImageAdapter(List<String> imageUrls){
        this.imageUrls = imageUrls;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_layout,parent,false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);
        context = parent.getContext();
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {

        String url = BASE_DOWNLOAD_URL+ imageUrls.get(position);
        SharedPreferences sp = context.getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE);
        String token = sp.getString("token",null);

        GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer "+token)
                .build());





        Glide.with(context)
                .asBitmap()
                .load(glideUrl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        final Bitmap imageBitmap = resource;
                        holder.imageView.setImageBitmap(resource);
                        holder.imageTitle.setText("image :"+position);
                        holder.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent t = new Intent(v.getContext(),PhotoViewActivity.class);

                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] byteArray = stream.toByteArray();

                                t.putExtra("imageByteArray",byteArray);
                                v.getContext().startActivity(t);
                            }
                        });
                    }
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });




    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
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
