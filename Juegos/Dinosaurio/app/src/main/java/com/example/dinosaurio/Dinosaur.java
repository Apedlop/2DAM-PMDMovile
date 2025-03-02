package com.example.dinosaurio;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Dinosaur {
    private int x, y;
    private int speed = 20;
    private Paint dinosaurPaint;

    public Dinosaur() {
        this.x = 1000; // Empieza fuera de la pantalla
        this.y = 500;
        this.dinosaurPaint = new Paint();
        this.dinosaurPaint.setColor(Color.DKGRAY); // Color del dinosaurio
    }

    public void update() {
        x -= speed;
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(x, y, x + 150, y + 150, dinosaurPaint); // Dibuja un cuadrado grande
    }

    public Rect getBounds() {
        return new Rect(x, y, x + 150, y + 150);
    }

    public boolean collides(Player player) {
        return Rect.intersects(getBounds(), player.getBounds());
    }
}