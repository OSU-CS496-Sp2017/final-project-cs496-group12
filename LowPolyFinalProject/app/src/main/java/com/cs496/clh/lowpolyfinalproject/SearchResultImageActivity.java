package com.cs496.clh.lowpolyfinalproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Alshawi on 6/8/2017.
 */

public class SearchResultImageActivity extends AppCompatActivity {
    private ImageView imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_show_image);

        imgView = (ImageView) findViewById(R.id.image_view);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("fetchedImage")) {
            //maybe do the poly logic here>
            // string will change to the fetched image object or maybe poly image
            String ex =(String) intent.getSerializableExtra("fetchedImage");
            Drawable placeHolderImg = getResources().getDrawable( R.drawable.ic_photo_camera_black_24dp );
            ColorFilter filter = new LightingColorFilter( Color.BLUE, Color.BLUE );
            imgView.setColorFilter(filter);
            imgView.setImageDrawable(placeHolderImg);
        }
    }
}
