package com.cs496.clh.lowpolyfinalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StarredActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Integer> reourcesId =new ArrayList<Integer>()
    {{
            add(R.drawable.ic_assistant_black_24dp);
            add(R.drawable.ic_photo_camera_black_24dp);
            add(R.drawable.s200);
            add(R.drawable.s400);
            add(R.drawable.s1000);
        }};
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

        // specify an adapter
        mAdapter = new StarredImagesAdapter(reourcesId);
        mRecyclerView.setAdapter(mAdapter);
    }
}
