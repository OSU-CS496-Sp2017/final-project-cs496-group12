package com.cs496.clh.lowpolyfinalproject;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AlertDialog;
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

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luay on 6/10/2017.
 */

public class StarredImagesAdapter extends RecyclerView.Adapter<StarredImagesAdapter.SearchResultViewHolder> {
    private ArrayList<StarredActivity.imgPath> resourcesId;
    private SQLiteDatabase mDB;
    private Context mCon;

    private void deleteInternalStorage(String fName){
        ContextWrapper cw = new ContextWrapper(mCon);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        String deleteString = directory.getAbsolutePath() + fName;
        File dFile = new File(directory, fName);
        dFile.delete();
        Log.d("DELETE", "Deleted " + directory.getAbsolutePath() + "/" + fName);
    }

    private void deleteSearchResultFromDB(String p) {
        String sqlSelection = LFSearchContract.FavoriteImages.COLUMN_FULL_NAME + " = ?";
        String[] sqlSelectionArgs = { p };
        mDB.delete(LFSearchContract.FavoriteImages.TABLE_NAME, sqlSelection, sqlSelectionArgs);
    }

    public StarredImagesAdapter(ArrayList<StarredActivity.imgPath> r, SQLiteDatabase db, Context c)
    {
        resourcesId = r;
        mDB = db;
        mCon = c;

    }
    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.viewholder, parent, false);
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


    class SearchResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private int position;

        private ImageView mImg;
        public Button unstarImg;

        public SearchResultViewHolder(View itemView) {
            super(itemView);
            unstarImg = (Button) itemView.findViewById(R.id.unstarred_btn);
            mImg = (ImageView)itemView.findViewById(R.id.image_view);
            unstarImg.setOnClickListener(this);
        }

        public void bind(StarredActivity.imgPath searchResult) {
            mImg.setImageBitmap(searchResult.b);
        }
        @Override
        public void onClick(View v) {
            openConfirmDialoug(v);
        }
        public void openConfirmDialoug(final View view){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder((Activity) mCon);
            alertDialogBuilder.setMessage("Are you sure you want to unstar this image?");
            alertDialogBuilder.setPositiveButton("yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            position = getAdapterPosition();
                            StarredActivity.imgPath searchResult = resourcesId.get(position);
                            Log.d("PATH", "The path is " + searchResult.path);
                            Log.d("PATH", "DELETING FROM DB");
                            deleteSearchResultFromDB(searchResult.path);
                            Log.d("PATH", "DELETING FROM LOCAL STORAGE");
                            deleteInternalStorage(searchResult.path);
                            resourcesId.remove(position);
                            //remove from the view
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, resourcesId.size());
                        }
                    });

            alertDialogBuilder.setNegativeButton("No",null);

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}


