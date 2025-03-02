package com.example.dinosaurio;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class PowerUp {
    private int x, y;
    private int speed;
    private int type; // 0: Super Salto, 1: Super Velocidad, 2: Escenario Loco
    private Paint powerUpPaint;

    public PowerUp(int x, int y, int speed, int type) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.type = type;
        this.powerUpPaint = new Paint();
        switch (type) {
            case 0:
                powerUpPaint.setColor(Color.MAGENTA); // Color del Super Salto
                break;
            case 1:
                powerUpPaint.setColor(Color.CYAN); // Color de la Super Velocidad
                break;
            case 2:
                powerUpPaint.setColor(Color.YELLOW); // Color del Escenario Loco
                break;
        }
    }

    public void update() {
        x -= speed;
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(x, y, x + 50, y + 50, powerUpPaint); // Dibuja un cuadrado pequeño
    }

    public Rect getBounds() {
        return new Rect(x, y, x + 50, y + 50);
    }

    public boolean collides(Player player) {
        return Rect.intersects(getBounds(), player.getBounds());
    }

    public void applyEffect(Player player) {
        // Aquí puedes implementar los efectos de los power-ups
    }
}