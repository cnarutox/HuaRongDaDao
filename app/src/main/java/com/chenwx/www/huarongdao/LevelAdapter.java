package com.chenwx.www.huarongdao;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

public class LevelAdapter extends RecyclerView.Adapter {

    public static final String TAG = "Select";
    private final LinkedList<String> mLevelName;
    private LayoutInflater mInflater;
    public ScrollingLevel scrollingLevel;

    public LevelAdapter(Context context, LinkedList<String> mLevelName) {
        mInflater = LayoutInflater.from(context);
        this.mLevelName = mLevelName;
        scrollingLevel = (ScrollingLevel) context;
    }

    class LevelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView LevelView;
        public final LevelAdapter mAdapter;

        public LevelViewHolder(View itemView, LevelAdapter mAdapter) {
            super(itemView);
            LevelView = itemView.findViewById(R.id.level);
            this.mAdapter = mAdapter;
            LevelView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            scrollingLevel.jump(mPosition);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.level_card, parent, false);
        return new LevelViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String mCurrent = mLevelName.get(position);
        ((LevelViewHolder) holder).LevelView.setText(mCurrent);

    }

    @Override
    public int getItemCount() {
        return mLevelName.size();
    }
}
