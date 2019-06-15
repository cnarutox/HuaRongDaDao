package com.chenwx.www.huarongdao;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.yy.mobile.rollingtextview.CharOrder;
import com.yy.mobile.rollingtextview.RollingTextView;
import com.yy.mobile.rollingtextview.strategy.Strategy;

import nl.dionsegijn.konfetti.KonfettiView;

public class GameActivity extends AppCompatActivity {
    public GameView gameView;
    public RollingTextView stepsView;
    public KonfettiView konfettiView;
    public AlertDialog dialog;
    public static String TAG = "GameActivity";
    public boolean hasSet = false;
    String[] blocks_value;
    String levelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        blocks_value = ScrollingLevel.mLevelList.get(getIntent().getIntExtra(ScrollingLevel.TAG, 0));

        setContentView(R.layout.activity_game);

        levelName = ScrollingLevel.mLevelName.get(getIntent().getIntExtra(ScrollingLevel.TAG, 0));
        ((TextView)findViewById(R.id.levelName)).setText(levelName);
        gameView = findViewById(R.id.canvas);
        konfettiView = findViewById(R.id.viewKonfetti);
        stepsView = findViewById(R.id.alphaBetView);

        stepsView.setAnimationDuration(200L);
        stepsView.setCharStrategy(Strategy.NormalAnimation());
        stepsView.addCharOrder(CharOrder.Alphabet);
        stepsView.setAnimationInterpolator(new AccelerateDecelerateInterpolator());
        stepsView.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //finsih
            }
        });
        stepsView.setText("0");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Log.i(TAG, "" + gameView.getWidth());
            Log.i(TAG, "" + gameView.getHeight());
            if (!hasSet) {
                hasSet = !hasSet;
                gameView.setWH(gameView.getWidth(), gameView.getHeight());
            }
        }
    }

    public void handle(View view) {
        GameView g = (GameView) view;
    }
}
