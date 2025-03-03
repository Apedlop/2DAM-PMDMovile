package com.example.juego2d;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class PowerUp {

    private Rect rect;
    private int velocity;
    public boolean isActive;
    private int type; // 0: SuperJump, 1: SuperSpeed, 2: CrazyScenario

    // Constructor para el PowerUp, genera un power-up en una posición aleatoria
    public PowerUp(int x, int y) {
        rect = new Rect(x, y, x + 50, y + 50); // Tamaño de los power-ups
        velocity = 10; // Velocidad con la que se mueve hacia el jugador
        isActive = true;

        // Elige el tipo de PowerUp aleatoriamente
        type = (int) (Math.random() * 3); // Genera un valor entre 0 y 2
    }

    // Actualiza el movimiento del PowerUp (se mueve hacia la izquierda)
    public void update() {
        rect.offset(-velocity, 0); // Mueve el PowerUp hacia la izquierda
        if (rect.right < 0) { // Si el power-up sale de la pantalla, desactivarlo
            isActive = false;
        }
    }

    // Aplica el efecto del PowerUp al dinosaurio
    public void applyEffect(Dinosaurio dino) {
        if (isActive) { // Solo aplicar el efecto si el power-up está activo
            switch (type) {
                case 0: // Super Jump
                    dino.superJump(); // Activar super salto
                    break;
                case 1: // Super Speed
                    dino.superSpeed(); // Activar super velocidad
                    break;
                case 2: // Crazy Scenario
                    dino.crazyScenario(); // Activar escenario loco
                    break;
            }
            deactivate(); // Desactivar el power-up después de aplicarlo
        }
    }

    // Dibuja el PowerUp en el Canvas
    public void draw(Canvas canvas, Paint paint) {
        if (isActive) {
            // Dibuja diferentes colores según el tipo de PowerUp
            switch (type) {
                case 0:
                    paint.setColor(Color.RED); // Color para el super salto
                    break;
                case 1:
                    paint.setColor(Color.BLUE); // Color para la super velocidad
                    break;
                case 2:
                    paint.setColor(Color.GREEN); // Color para el escenario loco
                    break;
            }
            canvas.drawRect(rect, paint); // Dibuja el rectángulo que representa el power-up
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
