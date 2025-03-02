package com.example.juego2d;

import android.graphics.Canvas;
import android.graphics.Rect;

public interface Obstacle {
    void update();
    void draw(Canvas canvas);
    Rect getRect();
}
