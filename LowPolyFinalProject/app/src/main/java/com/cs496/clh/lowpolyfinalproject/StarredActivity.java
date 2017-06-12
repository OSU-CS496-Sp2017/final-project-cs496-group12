package com.cs496.clh.lowpolyfinalproject;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cs496.clh.lowpolyfinalproject.data.LFSearchContract;
import com.cs496.clh.lowpolyfinalproject.data.LFSearchDBHelper;
import com.cs496.clh.lowpolyfinalproject.utils.LFutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StarredActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String fstring = "imgname.jpg";
    private SQLiteDatabase mDB;
    /*
    private List<Integer> reourcesId =new ArrayList<Integer>()
    {{
            add(R.drawable.ic_assistant_black_24dp);
            add(R.drawable.ic_photo_camera_black_24dp);
            add(R.drawable.s200);
            add(R.drawable.s400);
            add(R.drawable.s1000);
        }};
       */
    public static class imgPath implements Serializable {
        public static final String EXTRA_SEARCH_RESULT = "LFUtils.SearchResult";
        public String path;
        public Bitmap b;
    }
    //private ArrayList<imgPath> resourcesId;

    private ArrayList<LFutils.SearchResult> getAllSavedSearchResults() {
        Cursor cursor = mDB.query(
                LFSearchContract.FavoriteImages.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                LFSearchContract.FavoriteImages.COLUMN_TIMESTAMP + " DESC"
        );

        ArrayList<LFutils.SearchResult> searchResultsList = new ArrayList<>();
        while (cursor.moveToNext()) {
            LFutils.SearchResult searchResult = new LFutils.SearchResult();
            searchResult.fullName = cursor.getString(
                    cursor.getColumnIndex(LFSearchContract.FavoriteImages.COLUMN_FULL_NAME)
            );
            searchResultsList.add(searchResult);
        }
        cursor.close();
        return searchResultsList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starred);
        mRecyclerView = (RecyclerView) findViewById(R.id.starred_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        //db
        LFSearchDBHelper dbHelper = new LFSearchDBHelper(this);
        mDB = dbHelper.getWritableDatabase();
        System.out.println("-------");
        System.out.println(mDB);
        System.out.println("-------");

        ArrayList<LFutils.SearchResult> dbList = getAllSavedSearchResults();

        ArrayList<imgPath> resourcesId = new ArrayList<>();
        for(int i =0; i < dbList.size(); i++) {
            imgPath ip = new imgPath();
            //ip.b = loadImageFromStorage(fstring);
            ip.b = loadImageFromStorage(dbList.get(i).fullName);
            //ip.path = fstring;
            ip.path = dbList.get(i).fullName;
            resourcesId.add(ip);
        }
        // specify an adapter
        mAdapter = new StarredImagesAdapter(resourcesId, mDB);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void deleteSearchResultFromDB(String p) {
        //if (mSearchResult != null) {
        String sqlSelection = LFSearchContract.FavoriteImages.COLUMN_FULL_NAME + " = ?";
        //String[] sqlSelectionArgs = { mSearchResult.fullName };
        String[] sqlSelectionArgs = { p };
        mDB.delete(LFSearchContract.FavoriteImages.TABLE_NAME, sqlSelection, sqlSelectionArgs);
        //}
    }

    private String saveToInternalStorage(Bitmap bitmapImage, String fName){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory, fName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    private Bitmap loadImageFromStorage(String fName)
    {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        Bitmap b = null;
        try {
            File f=new File(directory, fName);
            b = BitmapFactory.decodeStream(new FileInputStream(f));

            //imgView.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return b;
    }
}
