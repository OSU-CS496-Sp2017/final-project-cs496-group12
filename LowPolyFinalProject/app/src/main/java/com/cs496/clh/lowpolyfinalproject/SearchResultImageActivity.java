package com.cs496.clh.lowpolyfinalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.uniquestudio.lowpoly.LowPoly;

import com.cs496.clh.lowpolyfinalproject.utils.LFutils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static com.cs496.clh.lowpolyfinalproject.utils.LFutils.buildLFURL;
import static com.cs496.clh.lowpolyfinalproject.utils.NetworkUtils.doHTTPGet;

/**
 * Created by Alshawi on 6/8/2017.
 */

public class SearchResultImageActivity extends AppCompatActivity {
    private ImageView imgView;
    private TextView textView;
    private Button applyPolyBtn;
    private Button starImgBtn;
    private boolean polyDone = false;
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
            String query = intent.getStringExtra("searchQuery");
            Log.d("DEBUG","Inside bitmap fetch location");
            Log.d("DEBUG","search query IS = " + query);
            //example placeholder image
            //Drawable placeHolderImg = getResources().getDrawable( R.drawable.ic_photo_camera_black_24dp );
            //ColorFilter filter = new LightingColorFilter( Color.BLUE, Color.BLUE );
            //imgView.setColorFilter(filter);
            //imgView.setImageDrawable(placeHolderImg);
<<<<<<< HEAD
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            Bitmap placeHolder = BitmapFactory.decodeResource(getResources(), R.drawable.s1000, options);
            imgView.setImageBitmap(placeHolder);
=======
            //BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inSampleSize = 4;
            //Bitmap placeHolder = BitmapFactory.decodeResource(getResources(), R.drawable.s1000, options);
            //imgView.setImageBitmap(placeHolder);

>>>>>>> 3e9c58f5c17156dc2728e211d114cf3782a40302
            //String bURL = "http://loremflickr.com/1000/1000/boat";
            //default width and height
            int dW = 200;
            int dH = 200;
            //String bURL = buildLFURL(dW, dH, "water");
            String bURL = buildLFURL(dW, dH, query);
            Log.d("BUILD", "url is " + bURL);
            Glide.
                    with(SearchResultImageActivity.this).
                    load(bURL).
                    into(imgView);
            //placeholder not working
            /*if(imgView.getDrawable() == null) {
                Log.d("Search", "failed to download image into view");
                Log.d("Search", "putting in placeholder");
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                Bitmap placeHolder = BitmapFactory.decodeResource(getResources(), R.drawable.searchfailure, options);
                imgView.setImageBitmap(placeHolder);
            }*/
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
            Bitmap beforePoly = null;

            if(imgView.getDrawable() != null) {
                beforePoly = ((BitmapDrawable) imgView.getDrawable()).getBitmap();
                if(beforePoly != null && !polyDone) {
                    int gradientThresh = 30;
                    Bitmap afterPoly = LowPoly.generate(beforePoly, gradientThresh);
                    polyDone = true;
                    imgView.setImageBitmap(afterPoly);
                    Context context = getApplicationContext();
                    CharSequence text = "Complete!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    applyPolyBtn.setVisibility(View.GONE);
                    starImgBtn.setVisibility(View.VISIBLE);
                } else {
                    // doesn't seem to be detecting failure
                    Log.d("FAILURE", "No image in before poly");
                }
            } else {
                //doesn't seem to be detecting failure
                Log.d("FAILURE", "No image inside view");
            }

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
