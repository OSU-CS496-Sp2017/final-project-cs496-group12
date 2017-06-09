package com.cs496.clh.lowpolyfinalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editTextBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextBox = (EditText)findViewById(R.id.text_box);

        Button searchButton = (Button)findViewById(R.id.search_btn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = editTextBox.getText().toString();
                if (!TextUtils.isEmpty(searchQuery)) {
                    //call search method here
                    //here i will show the other view
                    fetchImage("http://example.com/?image=searchQuery");
                }
            }
        });
    }
    private void fetchImage(String url)
    {
        Intent intent = new Intent(this, SearchResultImageActivity.class);
        intent.putExtra("fetchedImage", "image object should go here");
        String searchQuery = (String) editTextBox.getText().toString();
        intent.putExtra("searchQuery", searchQuery);
        startActivity(intent);
    }
}