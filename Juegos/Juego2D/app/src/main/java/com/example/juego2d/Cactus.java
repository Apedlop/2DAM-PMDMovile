package com.example.juego2d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Cactus implements Obstacle {
    private static int lastCactusX = 0;  // Última posición X del cactus (del lado izquierdo)
    private static final int CACTUS_WIDTH = 80;  // Ancho del cactus
    private static final int CACTUS_HEIGHT = 100;  // Alto del cactus
    private static final int MIN_SEPARATION = 50;  // Separación mínima de 50 píxeles
    private Rect rect;
    private int speed;
    private Bitmap cactusImage;  // Bitmap para la imagen del cactus
    private int groundLevel;  // Nivel del suelo

    public Cactus(int screenWidth, Context context) {
        // El cactus aparece desde el borde derecho de la pantalla
        int xPosition = screenWidth;  // Comienza desde el borde derecho de la pantalla
        groundLevel = 900;  // Nivel del suelo (ajusta según sea necesario)
        rect = new Rect(xPosition, groundLevel - CACTUS_HEIGHT, xPosition + CACTUS_WIDTH, groundLevel);  // Crea el rectángulo del cactus
        lastCactusX = xPosition;  // Guarda la última posición X
        speed = 10;  // Velocidad de movimiento hacia la izquierda

        // Cargar la imagen del cactus desde los recursos
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cactus);

        // Redimensionar la imagen al tamaño del cactus (CACTUS_WIDTH x CACTUS_HEIGHT)
        cactusImage = Bitmap.createScaledBitmap(originalBitmap, CACTUS_WIDTH, CACTUS_HEIGHT, true);
    }

    @Override
    public void update() {
        rect.offset(-speed, 0);  // Desplaza el cactus hacia la izquierda
    }

    @Override
    public void update(int speedMultiplier) {
        rect.offset(-speed * speedMultiplier, 0);  // Desplaza el cactus hacia la izquierda con velocidad multiplicada
    }

    @Override
    public void draw(Canvas canvas) {
        // Dibujar la imagen del cactus en lugar del rectángulo verde
        if (cactusImage != null) {
            canvas.drawBitmap(cactusImage, null, rect, null);
        }
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