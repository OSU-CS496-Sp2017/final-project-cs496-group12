package com.cs496.clh.lowpolyfinalproject;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
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
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.cs496.clh.lowpolyfinalproject.data.LFSearchContract;
import com.cs496.clh.lowpolyfinalproject.data.LFSearchDBHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luay on 6/10/2017.
 */

public class StarredImagesAdapter extends RecyclerView.Adapter<StarredImagesAdapter.SearchResultViewHolder> {
    private ArrayList<StarredActivity.imgPath> resourcesId;
    private static Toast toast;
    //private OnSearchResultClickListener mSearchResultClickListener;
    private SQLiteDatabase mDB;
    private void deleteSearchResultFromDB(String p) {
        //if (mSearchResult != null) {
            String sqlSelection = LFSearchContract.FavoriteImages.COLUMN_FULL_NAME + " = ?";
            //String[] sqlSelectionArgs = { mSearchResult.fullName };
            String[] sqlSelectionArgs = { p };
            mDB.delete(LFSearchContract.FavoriteImages.TABLE_NAME, sqlSelection, sqlSelectionArgs);
        //}
    }

    public StarredImagesAdapter(ArrayList<StarredActivity.imgPath> r, SQLiteDatabase db)
    {
        //mSearchResultClickListener = clickListener;
        resourcesId = r;
        mDB = db;
    }
    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.viewholder, parent, false);
        //ViewHolder viewHolder = new ViewHolder(view);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        holder.bind(resourcesId.get(position));
    }

    @Override
    public int getItemCount() {
        if (resourcesId != null) {
            return resourcesId.size();
        } else {
            return 0;
        }
    }

    /*
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

    public interface OnSearchResultClickListener {
        void onSearchResultClick(StarredActivity.imgPath searchResult);
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
            Log.d("size",Integer.toString(resourcesId.size()));
            resourcesId.remove(itemPosition);
            //reourcesId.
            Log.d("size",Integer.toString(resourcesId.size()));
            //remove from the view
            notifyItemRemoved(itemPosition);
            notifyItemRangeChanged(itemPosition, resourcesId.size());


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
    */
    class SearchResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private int position;
        /*
        SearchResultViewHolder(Integer i){
            position = i;
        }*/

        //private TextView mSearchResultTV;
        private ImageView mImg;
        public Button unstarImg;

        public SearchResultViewHolder(View itemView) {
            super(itemView);
            unstarImg = (Button) itemView.findViewById(R.id.unstarred_btn);
            //mSearchResultTV = (TextView)itemView.findViewById(R.id.tv_search_result);
            mImg = (ImageView)itemView.findViewById(R.id.image_view);
            //itemView.setOnClickListener(this);
            unstarImg.setOnClickListener(this);
        }

        public void bind(StarredActivity.imgPath searchResult) {
            //mSearchResultTV.setText(searchResult.fullName);
            mImg.setImageBitmap(searchResult.b);
        }

        @Override
        public void onClick(View v) {
            position = getAdapterPosition();
            StarredActivity.imgPath searchResult = resourcesId.get(position);


            Log.d("PATH", "The path is " + searchResult.path);
            Log.d("PATH", "DELETING FROM DB");
            deleteSearchResultFromDB(searchResult.path);
            resourcesId.remove(position);
            //remove from the view
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, resourcesId.size());
            //mSearchResultClickListener.onSearchResultClick(searchResult);
        }
    }
}


