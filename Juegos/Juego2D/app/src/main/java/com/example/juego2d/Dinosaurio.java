package com.example.juego2d;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Dinosaurio {

    private Rect rect;
    private int velocity;
    private boolean isJumping;

    public Dinosaurio() {
        rect = new Rect(100, 800, 200, 900);
        velocity = 0;
        isJumping = false;
    }

    public void jump() {
        if (!isJumping) {
            velocity = -30;
            isJumping = true;
        }
    }

    public void update() {
        rect.offset(0, velocity);
        velocity += 2;
        if (rect.bottom >= 900) {
            rect.offsetTo(100, 800);
            isJumping = false;
        }
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawRect(rect, paint);
    }

    public Rect getRect() {
        return rect;
    }

}
