package com.example.juego2d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class DinoGameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread thread;
    private Dinosaurio dino;
    private ArrayList<Cactus> cacti;
    private Paint paint;
    private int score;
    private boolean isGameOver;

    public DinoGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);
        dino = new Dinosaurio();
        cacti = new ArrayList<>();
        paint = new Paint();
        score = 0;
        isGameOver = false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && !isGameOver) {
            dino.jump();
        }
        return true;
    }

    public void update() {
        if (!isGameOver) {
            dino.update();
            for (Cactus cactus : cacti) {
                cactus.update();
                if (Rect.intersects(dino.getRect(), cactus.getRect())) {
                    isGameOver = true;
                }
            }
            if (new Random().nextInt(100) < 2) {
                cacti.add(new Cactus(getWidth()));
            }
            score++;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.WHITE);
            dino.draw(canvas);
            for (Cactus cactus : cacti) {
                cactus.draw(canvas);
            }
            paint.setColor(Color.BLACK);
            paint.setTextSize(50);
            canvas.drawText("Score: " + score, 50, 100, paint);
            if (isGameOver) {
                canvas.drawText("Game Over", getWidth() / 2 - 100, getHeight() / 2, paint);
            }
        }
    }
}
