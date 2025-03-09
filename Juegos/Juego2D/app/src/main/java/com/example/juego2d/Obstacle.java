package com.example.juego2d;

import android.graphics.Canvas;
import android.graphics.Rect;

public interface Obstacle {
    void update(); // Actualiza la posición del obstáculo
    void update(int speedMultiplier); // Actualiza con multiplicador de velocidad
    void draw(Canvas canvas); // Dibuja el obstáculo
    Rect getRect(); // Devuelve el rectángulo del obstáculo
}