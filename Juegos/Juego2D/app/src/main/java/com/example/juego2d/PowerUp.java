package com.example.juego2d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import java.util.Random;

public class PowerUp {

    private Rect rect;
    private int velocity;
    private boolean isActive;
    private int type; // 0: SuperJump, 1: SuperSpeed, 2: CrazyScenario
    private static final Random random = new Random(); // Generador de números aleatorios
    private Bitmap powerUpImage; // Bitmap para la imagen del power-up

    // Constructor para el PowerUp, genera un power-up en una posición aleatoria
    public PowerUp(int x, int y, Context context) {
        rect = new Rect(x, y, x + 50, y + 50); // Tamaño del power-up
        velocity = 10; // Velocidad de movimiento
        isActive = true;
        type = random.nextInt(3); // Genera un valor entre 0 y 2

        // Cargar la imagen del power-up desde los recursos
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.power_up);

        // Redimensionar la imagen al tamaño del power-up (50x50 píxeles)
        powerUpImage = Bitmap.createScaledBitmap(originalBitmap, 80, 80, true);
    }

    // Actualiza el movimiento del PowerUp
    public void update() {
        rect.offset(-velocity, 0); // Mueve hacia la izquierda
        if (rect.right < 0) { // Si sale de la pantalla, desactivar
            isActive = false;
        }
    }

    // Aplica el efecto del PowerUp al dinosaurio
    public void applyEffect(Player dino) {
        switch (type) {
            case 0:
                dino.activateSuperJump(); // Activar super salto
                break;
            case 1:
                dino.activateSuperSpeed(); // Activar super velocidad
                break;
            case 2:
                dino.activateCrazyScenario(); // Activar escenario loco
                break;
        }
        deactivate(); // Desactivar después de aplicarlo
    }

    // Dibuja el PowerUp en el Canvas
    public void draw(Canvas canvas, Paint globalPaint) {
        if (isActive && powerUpImage != null) {
            // Dibujar la imagen del power-up
            canvas.drawBitmap(powerUpImage, null, rect, null);
        }
    }

    public Rect getRect() {
        return rect;
    }

    public int getType() {
        return type;
    }

    // Método para verificar si el PowerUp está activo
    public boolean isActive() {
        return isActive;
    }

    // Método para desactivar el PowerUp
    public void deactivate() {
        isActive = false;
    }
}