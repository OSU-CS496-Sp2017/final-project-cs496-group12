package com.cs496.clh.lowpolyfinalproject;

import android.content.Context;
import android.content.ContextWrapper;
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
import com.bumptech.glide.request.target.SimpleTarget;
import com.uniquestudio.lowpoly.LowPoly;

import com.cs496.clh.lowpolyfinalproject.utils.LFutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
            Drawable placeHolderImg = getResources().getDrawable( R.drawable.ic_photo_camera_black_24dp );
            //ColorFilter filter = new LightingColorFilter( Color.BLUE, Color.BLUE );
            //imgView.setColorFilter(filter);
            //imgView.setImageDrawable(placeHolderImg);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            Bitmap placeHolder = BitmapFactory.decodeResource(getResources(), R.drawable.s1000, options);
            imgView.setImageBitmap(placeHolder);

            //BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inSampleSize = 4;
            //Bitmap placeHolder = BitmapFactory.decodeResource(getResources(), R.drawable.s1000, options);
            //imgView.setImageBitmap(placeHolder);

            //String bURL = "http://loremflickr.com/1000/1000/boat";
            //default width and height
            int dW = 400;
            int dH = 400;
            //String bURL = buildLFURL(dW, dH, "water");
            String bURL = buildLFURL(dW, dH, query);


            Log.d("BUILD", "url is " + bURL);

            Glide.
                    with(SearchResultImageActivity.this).
                    load(bURL).into(imgView);
            //sleep testing

            long sleeptime = 3000;
            try {
                Thread.sleep(sleeptime);
            } catch(Exception e){
                e.printStackTrace();
            }
            if(imgView.getDrawable() == null) {
                Log.d("AFTERSLEEP", "img not in view after sleep");
            } else {
                Log.d("AFTERSLEEP", "img IS IN view after sleep");
            }

            //SystemClock.sleep(timeInMills);

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
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                //rough way of handling load failure
                //polyDone = true;
                //applyPolyBtn.setVisibility(View.GONE);
                Bitmap placeHolder = BitmapFactory.decodeResource(getResources(), R.drawable.searchfailure, options);
                imgView.setImageBitmap(placeHolder);
                Context context = getApplicationContext();
                CharSequence text = "Image search failure! Try again!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

        }
    }
    class handleStarImageClickButton implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            //save the image logic goes here.......
            Bitmap beforePoly = null;

            //Save file to internal filestore
            if(imgView.getDrawable() != null) {
                beforePoly = ((BitmapDrawable) imgView.getDrawable()).getBitmap();
                String path = saveToInternalStorage(beforePoly);
                Log.d("WRITE", "JUST WROTE TO THIS PATH =" + path);
            } else {
                Log.d("WRITE", "no bitmap to write");
            }


            //Load image into imgView
            /*
            String loadPath = "/data/user/0/com.cs496.clh.lowpolyfinalproject/app_imageDir";
            loadImageFromStorage(loadPath);
            */

            Context context = getApplicationContext();
            CharSequence text = "Image is Starred!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"testname.jpg");

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

    private void loadImageFromStorage(String path)
    {

        try {
            File f=new File(path, "testname.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            imgView.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}
