package com.example.dinosaurio;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Obstacle {
    private int x, y;
    private int speed;
    private int type; // 0: Cactus, 1: Rama, 2: Coche
    private Paint obstaclePaint;

    public Obstacle(int x, int y, int speed, int type, int scene) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.type = type;
        this.obstaclePaint = new Paint();
        switch (type) {
            case 0:
                obstaclePaint.setColor(Color.GREEN); // Color del cactus
                break;
            case 1:
                obstaclePaint.setColor(Color.BLACK); // Color de la rama
                break;
            case 2:
                obstaclePaint.setColor(Color.RED); // Color del coche
                break;
        }
    }

    public void update() {
        x -= speed;
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(x, y, x + 100, y + 100, obstaclePaint); // Dibuja un cuadrado
    }

    public Rect getBounds() {
        return new Rect(x, y, x + 100, y + 100);
    }

    public boolean collides(Player player) {
        return Rect.intersects(getBounds(), player.getBounds());
    }
}