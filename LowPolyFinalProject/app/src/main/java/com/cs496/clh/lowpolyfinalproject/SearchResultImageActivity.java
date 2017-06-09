package com.cs496.clh.lowpolyfinalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Alshawi on 6/8/2017.
 */

public class SearchResultImageActivity extends AppCompatActivity {
    private ImageView imgView;
    private TextView textView;
    private Button applyPolyBtn;
    private Button starImgBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_show_image);

        imgView = (ImageView) findViewById(R.id.image_view);
        textView = (TextView) findViewById(R.id.text_view);
        applyPolyBtn = (Button) findViewById(R.id.apply_poly_btn);
        starImgBtn = (Button) findViewById(R.id.star_poly_btn);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("fetchedImage")) {
            // string will change to the fetched image object or maybe just image
            String ex =(String) intent.getSerializableExtra("fetchedImage");

            //example placeholder image
            Drawable placeHolderImg = getResources().getDrawable( R.drawable.ic_photo_camera_black_24dp );
            ColorFilter filter = new LightingColorFilter( Color.BLUE, Color.BLUE );
            imgView.setColorFilter(filter);
            imgView.setImageDrawable(placeHolderImg);
        }
        if(intent !=null && intent.hasExtra("searchQuery"))
        {
            String searchedQuery =(String) intent.getSerializableExtra("searchQuery");
            textView.setText("You Searched ".concat(searchedQuery).concat("!"));
        }

        applyPolyBtn.setOnClickListener(new handleApplyPolyClickButton());

        starImgBtn.setOnClickListener(new handleStarImageClickButton());
    }
    class handleApplyPolyClickButton implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            //do the poly logic here>
            Context context = getApplicationContext();
            CharSequence text = "Complete!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            starImgBtn.setVisibility(View.VISIBLE);
        }
    }
    class handleStarImageClickButton implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            //save the image logic goes here.......
            Context context = getApplicationContext();
            CharSequence text = "Image is Starred!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}
