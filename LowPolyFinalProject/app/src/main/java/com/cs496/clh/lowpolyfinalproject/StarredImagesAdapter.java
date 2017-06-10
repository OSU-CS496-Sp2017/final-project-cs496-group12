package com.cs496.clh.lowpolyfinalproject;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Luay on 6/10/2017.
 */

public class StarredImagesAdapter extends RecyclerView.Adapter<StarredImagesAdapter.ViewHolder> {
    private Integer[] reourcesId;
    public StarredImagesAdapter(Integer[] r)
    {
        reourcesId = r;
    }
    @Override
    public StarredImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.viewholder, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StarredImagesAdapter.ViewHolder holder, int position) {
        holder.bind(reourcesId[position]);
    }

    @Override
    public int getItemCount() {
        return reourcesId.length;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgView;
        private Button unstarImg;
        private static Context context;
        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            imgView = (ImageView) itemView.findViewById(R.id.image_view);
        }
        public void bind(Integer i)
        {
            Drawable drawable =  ContextCompat.getDrawable(context, i);
            if(drawable !=null)
                imgView.setImageDrawable(drawable);
        }
    }
}
