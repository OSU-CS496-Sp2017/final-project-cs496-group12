package com.cs496.clh.lowpolyfinalproject;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
        //implements StarredImagesAdapter.OnSearchResultClickListener {

    private EditText editTextBox;
    private static final String SEARCH_BOX_KEY = "SearchEditTextBox";
    //private ProgressBar mLoadingIndicatorPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextBox = (EditText)findViewById(R.id.text_box);
        //mLoadingIndicatorPB = (ProgressBar)findViewById(R.id.pb_loading_indicator);
        Button searchButton = (Button)findViewById(R.id.search_btn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearch();
            }
        });
        editTextBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    doSearch();
                    return true;
                }
                return false;
            }
        });

        if(savedInstanceState !=null && savedInstanceState.containsKey(SEARCH_BOX_KEY))
        {
            String savedValue = savedInstanceState.getString(SEARCH_BOX_KEY);
            editTextBox.setText(savedValue);
            Log.d("onSaveInstanceState",savedValue);
        }

        Button randomButton = (Button)findViewById(R.id.search_random);
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    editTextBox.setText("");
                    fetchImage("");
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
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);;
        Log.d("onSaveInstanceState","outState");
        String searchQuery = (String) editTextBox.getText().toString();
        outState.putString(SEARCH_BOX_KEY,searchQuery);
    }
    private void doSearch()
    {
        String searchQuery = editTextBox.getText().toString();
        if (!TextUtils.isEmpty(searchQuery)) {
            Log.d("SEARCH", "Input search query = " + searchQuery);
            //mLoadingIndicatorPB.setVisibility(View.VISIBLE);
            //call search method here
            //here i will show the other view
            fetchImage(searchQuery);
        }
    }
}
