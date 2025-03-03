package com.example.juego2d;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Vehicle implements Obstacle {
    private static int lastVehicleX = 0;  // Última posición X del vehículo (del lado izquierdo)
    private static final int VEHICLE_WIDTH = 100;  // Ancho del vehículo
    private static final int VEHICLE_HEIGHT = 50;  // Alto del vehículo
    private static final int MIN_SEPARATION = 50;  // Separación mínima de 50 píxeles
    private Rect rect;
    private int speed;

    public Vehicle(int screenWidth) {
        // El vehículo aparece desde el borde derecho de la pantalla
        int xPosition = screenWidth;  // Comienza desde el borde derecho de la pantalla
        rect = new Rect(xPosition, 850, xPosition + VEHICLE_WIDTH, 900);  // Crea el rectángulo del vehículo
        lastVehicleX = xPosition;  // Guarda la última posición X
        speed = 20;  // Velocidad de movimiento hacia la izquierda
    }

    @Override
    public void update() {
        rect.offset(-speed, 0);  // Desplaza el vehículo hacia la izquierda
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);  // Color del vehículo
        canvas.drawRect(rect, paint);  // Dibuja el vehículo
    }

    @Override
    public Rect getRect() {
        return rect;  // Devuelve el rectángulo del vehículo
    }

    // Método estático para generar nuevos vehículos con la separación mínima entre ellos
    public static int getNextVehiclePosition(int screenWidth) {
        // Se asegura que el siguiente vehículo se coloque con separación mínima
        return lastVehicleX + MIN_SEPARATION + VEHICLE_WIDTH;
    }
}