package com.chenwx.www.huarongdao;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.View;

import java.util.LinkedList;
import java.util.Objects;

public class ScrollingLevel extends AppCompatActivity {
    public static final String TAG = "Level";
    public RecyclerView mRecyclerView;
    public LevelAdapter mAdapter;
    public final static LinkedList<String[]> mLevelList = new LinkedList<>();
    public final static LinkedList<String> mLevelName = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_level);
        final Intent intent = new Intent(this, GameActivity.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "随机选择关卡", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                intent.putExtra(ScrollingLevel.TAG, mLevelList.get((int) (Math.random() * 7)));
                startActivity(intent);
            }
        });
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TypedArray array = getResources().obtainTypedArray(R.array.level);
        mLevelName.clear();
        mLevelList.clear();
        for (int i = 0; i < array.length(); i++) {
            mLevelList.add(getResources().getStringArray(array.getResourceId(i, -1)));
            mLevelName.add(getResources().getResourceEntryName(array.getResourceId(i, -1)));
            Log.i(TAG, getResources().getResourceEntryName(array.getResourceId(i, -1)));
        }
        array.recycle();

        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerview);
// Create an adapter and supply the data to be displayed.
        mAdapter = new LevelAdapter(this, mLevelName);
// Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
// Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public void jump(int position) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(ScrollingLevel.TAG, position);
        startActivity(intent);
    }

}
