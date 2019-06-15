package com.chenwx.www.huarongdao;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.yy.mobile.rollingtextview.*;

import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;


public class GameView extends View {
    public RollingTextView stepsView;
    public static String TAG = "Move";
    public int startX, startY, stopX, stopY;
    public Paint paint = new Paint(), paintStoke = new Paint();
    public Block blocks[] = new Block[10], selected_block = new Block(0, 0, 0, "before");
    public int width, height, w, h;
    public int selected = -1;
    public int offset = 50, sep = 10;
    public int LeftX, RightX;
    public int steps = 0;
    public GameActivity gameActivity;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setClickable(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);
        paintStoke.setColor(Color.BLACK);
        paintStoke.setStyle(Paint.Style.STROKE);
        paintStoke.setStrokeWidth(5);

        gameActivity = (GameActivity) context;
        String[] blocks_value = gameActivity.blocks_value;
        for (int i = 0; i < 10; i++) {
            String value[] = blocks_value[i].split(" ");
            blocks[i] = new Block(value);
        }
        invalidate();
    }

    public int which() {
        for (int i = 0; i < 10; i++) {
            if (startX >= blocks[i].lx && startY >= blocks[i].ly
                    && startX <= blocks[i].rx && startY <= blocks[i].ry) {
                selected_block.getLocation(blocks[i]);
                return i;
            }
        }
        return -1;
    }

    public boolean move() {
        int diffx = stopX - startX, diffy = stopY - startY;
        Block selected_block = blocks[selected];
        if (Math.abs(diffx) > Math.abs(diffy)) {
            selected_block.lx += diffx;
            selected_block.rx += diffx;
            if (!(selected_block.lx > offset + sep && selected_block.rx < w * 4 + offset)) {
                if (diffx > 0)
                    blocks[selected].rx = Math.min(blocks[selected].rx, width - offset);
                else
                    blocks[selected].rx = Math.max(blocks[selected].rx, offset + blocks[selected].width);
                blocks[selected].updateLocXByr();
                Log.i(TAG, "被按回来了" + blocks[selected]);
                return false;
            }
            for (Block block : blocks) {
                if (selected_block == block) {
                    continue;
                }
                if (collision(selected_block, block)) {
                    selected_block.lx -= diffx;
                    selected_block.rx -= diffx;
                    return false;
                }
            }
        } else {
            selected_block.ly += diffy;
            selected_block.ry += diffy;
            if (!(selected_block.ly + sep > 0 && selected_block.ry < height)) {
                if (diffy > 0)
                    blocks[selected].ry = Math.min(blocks[selected].ry, height);
                else blocks[selected].ry = Math.max(blocks[selected].ry, blocks[selected].height);
                blocks[selected].updateLocYByr();
                Log.i(TAG, "被按回来了" + blocks[selected]);
            }
            for (Block block : blocks) {
                if (selected_block == block)
                    continue;
                if (collision(selected_block, block)) {
                    selected_block.ly -= diffy;
                    selected_block.ry -= diffy;
                    return false;
                }
            }
        }
        return true;
    }

    public boolean collision(Block selected_block, Block block) {
        if (selected_block.lx >= block.rx + sep || selected_block.rx + sep <= block.lx
                || selected_block.ly >= block.ry + sep || selected_block.ry + sep <= block.ly) {
            return false;
        }
        return true;
    }

    public void success() {
        for (Block block : blocks) {
            if (block.name.equals("曹操") && block.rx == width - offset - w
                    && block.ry == height) {
                gameActivity.konfettiView.build()
                        .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                        .setDirection(0.0, 359.0)
                        .setSpeed(1f, 5f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(2000L)
                        .addShapes(Shape.RECT, Shape.CIRCLE)
                        .addSizes(new Size(12, 5f))
                        .setPosition(-50f, gameActivity.konfettiView.getWidth() + 50f, -50f, -50f)
                        .streamFor(300, 5000L);
                new AlertDialog.Builder(gameActivity)
                        .setTitle("通关成功")
                        .setMessage("你已成功以" + steps + "步的战绩完成关卡" + gameActivity.levelName + " ！")
                        .setPositiveButton("完成", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                                gameActivity.startActivity(new Intent(gameActivity, ScrollingLevel.class));
                            }
                        }).setNegativeButton("重新开始", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        gameActivity.finish();
                        gameActivity.startActivity(gameActivity.getIntent());
                    }
                }).create().show();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Block block : blocks) {
            paint.setShader(new LinearGradient(block.lx, block.ly, block.rx, block.ry,
                    new int[]{Color.parseColor("#5a85d4"),
                            Color.parseColor("#6ca3ca"), Color.parseColor("#76c1bf"),
                            Color.parseColor("#7be0b3")}, new float[]{0f, 0.33f, 0.66f, 1.0f}, Shader.TileMode.CLAMP));
            canvas.drawRoundRect(block.lx, block.ly, block.rx, block.ry, 50, 50, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                startY = (int) event.getY();
                selected = which();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_MOVE:
                stopX = (int) event.getX();
                stopY = (int) event.getY();
                if (selected >= 0 && move()) {
                    invalidate();
                }
                startX = (int) event.getX();
                startY = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_MOVE) break;
                Log.i(TAG, "ACTION_UP");
                if (selected >= 0) {
                    if (Math.abs(blocks[selected].lx - selected_block.lx) > w / 2
                    ) {
//                        Minus 1 to make locX = 0 possible
                        int locX = (blocks[selected].rx - offset) / w;
                        Log.i(TAG, String.format("Pass %d", locX));
                        if (blocks[selected].lx > selected_block.lx) {
                            blocks[selected].rx = w * (locX + 1) + offset;
                            blocks[selected].rx = Math.min(blocks[selected].rx, width - offset);
                        } else if (blocks[selected].lx < selected_block.lx) {
                            blocks[selected].rx = w * (locX) + offset;
                            blocks[selected].rx = Math.max(blocks[selected].rx, offset + blocks[selected].width);
                        }
                        blocks[selected].updateLocXByr();
                    } else if (
                            Math.abs(blocks[selected].ly - selected_block.ly) > h / 2) {
                        int locY = (blocks[selected].ry) / h;
                        Log.i(TAG, String.format("Pass %d %d %d", locY, blocks[selected].ly, selected_block.ly));
                        if (blocks[selected].ly > selected_block.ly) {
                            blocks[selected].ry = h * (locY + 1);
                            blocks[selected].ry = Math.min(blocks[selected].ry, height);
                        } else if (blocks[selected].ly < selected_block.ly) {
                            blocks[selected].ry = h * (locY);
                            blocks[selected].ry = Math.max(blocks[selected].ry, blocks[selected].height);
                        }
                        blocks[selected].updateLocYByr();
                    } else blocks[selected].getLocation(selected_block);
                    steps += Math.abs(blocks[selected].rx - selected_block.rx) / w
                            + Math.abs((blocks[selected].ry - selected_block.ry) / h);
                    Log.i(TAG, "Steps: " + steps + " ----------------------------");
                    gameActivity.stepsView.setText("" + steps);
                    invalidate();
                    success();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setWH(int width, int height) {
        this.width = width;
        this.height = height;
        this.w = (width - 2 * offset) / 4;
        this.h = height / 5;
        for (int i = 0; i < 10; i++) {
            blocks[i].lx = blocks[i].lx * w + offset + sep;
            blocks[i].ly = blocks[i].ly * h + sep;
            blocks[i].rx = blocks[i].rx * w + offset;
            blocks[i].ry = blocks[i].ry * h;
            blocks[i].updateSize();
        }
    }
}