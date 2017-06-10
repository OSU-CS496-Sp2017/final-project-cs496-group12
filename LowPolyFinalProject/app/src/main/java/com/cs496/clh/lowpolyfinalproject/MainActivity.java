package com.cs496.clh.lowpolyfinalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
                    Log.d("SEARCH", "Input search query = " + searchQuery);
                    //call search method here
                    //here i will show the other view
                    fetchImage(searchQuery);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainactivitymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.starred_menu:
                Intent StarredIntent = new Intent(this, StarredActivity.class);
                startActivity(StarredIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
