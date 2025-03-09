package com.example.juego2d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Vehicle implements Obstacle {
    private static int lastVehicleX = 0;  // Última posición X del vehículo (del lado izquierdo)
    private static final int VEHICLE_WIDTH = 150;  // Ancho del vehículo
    private static final int VEHICLE_HEIGHT = 90;  // Alto del vehículo
    private static final int MIN_SEPARATION = 50;  // Separación mínima de 50 píxeles
    private Rect rect;
    private int speed;
    private Bitmap vehicleImage;  // Bitmap para la imagen del vehículo
    private int groundLevel;  // Nivel del suelo

    public Vehicle(int screenWidth, Context context) {
        // El vehículo aparece desde el borde derecho de la pantalla
        int xPosition = screenWidth;  // Comienza desde el borde derecho de la pantalla
        groundLevel = 900;  // Nivel del suelo (ajusta según sea necesario)
        rect = new Rect(xPosition, groundLevel - VEHICLE_HEIGHT, xPosition + VEHICLE_WIDTH, groundLevel);  // Crea el rectángulo del vehículo
        lastVehicleX = xPosition;  // Guarda la última posición X
        speed = 20;  // Velocidad de movimiento hacia la izquierda

        // Cargar la imagen del vehículo desde los recursos
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.vehicle);

        // Redimensionar la imagen al tamaño del vehículo (VEHICLE_WIDTH x VEHICLE_HEIGHT)
        vehicleImage = Bitmap.createScaledBitmap(originalBitmap, VEHICLE_WIDTH, VEHICLE_HEIGHT, true);
    }

    @Override
    public void update() {
        rect.offset(-speed, 0);  // Desplaza el vehículo hacia la izquierda
    }

    @Override
    public void update(int speedMultiplier) {
        rect.offset(-speed * speedMultiplier, 0);  // Desplaza el vehículo hacia la izquierda con velocidad multiplicada
    }

    @Override
    public void draw(Canvas canvas) {
        // Dibujar la imagen del vehículo en lugar del rectángulo azul
        if (vehicleImage != null) {
            canvas.drawBitmap(vehicleImage, null, rect, null);
        }
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