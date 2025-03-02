package com.example.juego2d;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Cactus implements Obstacle {
    private static int lastCactusX = 0;  // Última posición X del cactus (del lado izquierdo)
    private static final int CACTUS_WIDTH = 50;  // Ancho del cactus
    private static final int CACTUS_HEIGHT = 50;  // Alto del cactus
    private static final int MIN_SEPARATION = 50;  // Separación mínima de 50 píxeles
    private Rect rect;
    private int speed;

    public Cactus(int screenWidth) {
        // El cactus aparece desde el borde derecho de la pantalla
        int xPosition = screenWidth;  // Comienza desde el borde derecho de la pantalla
        rect = new Rect(xPosition, 850, xPosition + CACTUS_WIDTH, 900);  // Crea el rectángulo del cactus
        lastCactusX = xPosition;  // Guarda la última posición X
        speed = 10;  // Velocidad de movimiento hacia la izquierda
    }

    @Override
    public void update() {
        rect.offset(-speed, 0);  // Desplaza el cactus hacia la izquierda
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);  // Color del cactus
        canvas.drawRect(rect, paint);  // Dibuja el cactus
    }

    @Override
    public Rect getRect() {
        return rect;  // Devuelve el rectángulo del cactus
    }

    // Método estático para generar nuevos cactus con la separación mínima entre ellos
    public static int getNextCactusPosition(int screenWidth) {
        // Se asegura que el siguiente cactus se coloque con separación mínima
        return lastCactusX + MIN_SEPARATION + CACTUS_WIDTH;
    }
}
