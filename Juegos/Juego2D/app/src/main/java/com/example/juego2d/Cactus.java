package com.example.juego2d;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Cactus {
    private Rect rect;
    private int speed;

    public Cactus(int startX) {
        rect = new Rect(startX, 850, startX + 50, 900);
        speed = 10;
    }

    public void update() {
        rect.offset(-speed, 0);
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawRect(rect, paint);
    }

    public Rect getRect() {
        return rect;
    }
}
