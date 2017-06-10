package com.cs496.clh.lowpolyfinalproject;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luay on 6/10/2017.
 */

public class StarredImagesAdapter extends RecyclerView.Adapter<StarredImagesAdapter.ViewHolder> {
    private List<Integer> reourcesId;
    private static Toast toast;
    public StarredImagesAdapter(List<Integer> r)
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
        holder.bind(reourcesId.get(position));
        holder.unstarImg.setOnClickListener(new handleUnstarImgClick(position));

    }

    @Override
    public int getItemCount() {
        return reourcesId.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgView;
        public Button unstarImg;
        private static Context context;
        public ViewHolder(View itemView) {
            super(itemView);
            //itemView.setOnClickListener(this);
            context = itemView.getContext();
            imgView = (ImageView) itemView.findViewById(R.id.image_view);
            unstarImg = (Button) itemView.findViewById(R.id.unstarred_btn);
            //unstarImg.setOnClickListener(new handleUnstarImgClick());
        }
        public void bind(Integer i)
        {
            Drawable drawable =  ContextCompat.getDrawable(context, i);
            if(drawable !=null)
                imgView.setImageDrawable(drawable);
        }


    }
    class handleUnstarImgClick implements View.OnClickListener {

        private Integer position;

        handleUnstarImgClick(Integer i){
            position = i;
        }

        @Override
        public void onClick(View view) {
            int itemPosition = position;
            Log.d("handleUnstarImgClick", "onClick " + itemPosition + " ");
            Context context = view.getContext();

            //remove from database
            Log.d("size",Integer.toString(reourcesId.size()));
            reourcesId.remove(itemPosition);
            //reourcesId.
            Log.d("size",Integer.toString(reourcesId.size()));
            //remove from the view
            notifyItemRemoved(itemPosition);
            notifyItemRangeChanged(itemPosition, reourcesId.size());


            CharSequence text = "Image is removed!";
            int duration = Toast.LENGTH_SHORT;
            try{ toast.getView().isShown();
                toast.setText(text);
            } catch (Exception e) {
                toast = Toast.makeText(context, text, duration);
            }
            toast.show();
        }
    }
}
