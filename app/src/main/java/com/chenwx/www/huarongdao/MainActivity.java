package com.chenwx.www.huarongdao;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.dd.morphingbutton.MorphingButton;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MorphingButton start = (MorphingButton) findViewById(R.id.start);
        MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(1000)
                .cornerRadius(500) // 56 dp
                .width(400) // 56 dp
                .height(400) // 56 dp
                .color(Color.parseColor("#FFDA37")).colorPressed(Color.parseColor("#FFEA47"))
                .text("开始游戏").strokeColor(Color.parseColor("#FFD312")).strokeWidth(40);
        start.morph(circle);
    }

    public void start(View view) {
        Intent intent = new Intent(this, ScrollingLevel.class);
        startActivity(intent);
    }
}