package com.example.dinosaurio;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Player {
    private int x, y;
    private boolean isJumping, isDucking;
    private int jumpHeight = 200;
    private int duckHeight = 100;
    private Paint playerPaint;

    public Player() {
        this.x = 100;
        this.y = 500;
        this.isJumping = false;
        this.isDucking = false;
        this.playerPaint = new Paint();
        this.playerPaint.setColor(Color.BLUE); // Color del jugador
    }

    public void update() {
        if (isJumping) {
            y -= 10; // Mueve al jugador hacia arriba
            if (y < 500 - jumpHeight) {
                isJumping = false;
            }
        } else if (y < 500) {
            y += 10; // Mueve al jugador hacia abajo
        }
        if (isDucking) {
            y = 500 + duckHeight;
        } else if (y > 500) {
            y = 500;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(x, y, x + 100, y + 100, playerPaint); // Dibuja un cuadrado
    }

    public void jump() {
        if (!isJumping && y == 500) {
            isJumping = true;
        }
    }

    public void duck() {
        isDucking = true;
    }

    public void stand() {
        isDucking = false;
    }

    public Rect getBounds() {
        return new Rect(x, y, x + 100, y + 100);
    }
}