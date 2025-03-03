package com.example.juego2d;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Dinosaurio {

    private Rect rect;
    private int velocity;
    private boolean isJumping;
    private boolean hasSuperJump; // Indica si tiene el power-up de super salto
    private boolean hasSuperSpeed; // Indica si tiene el power-up de super velocidad
    private boolean hasCrazyScenario; // Indica si tiene el power-up de escenario loco
    private int superJumpDuration; // Duración del super salto
    private int superSpeedDuration; // Duración de la super velocidad
    private int crazyScenarioDuration; // Duración del escenario loco

    public Dinosaurio() {
        rect = new Rect(100, 800, 200, 900); // Posición inicial del dinosaurio
        velocity = 0;
        isJumping = false;
        hasSuperJump = false;
        hasSuperSpeed = false;
        hasCrazyScenario = false;
        superJumpDuration = 0;
        superSpeedDuration = 0;
        crazyScenarioDuration = 0;
    }

    // Método para saltar
    public void jump() {
        if (!isJumping) {
            velocity = -30;
            isJumping = true;
        }
    }

    // Método para super salto
    public void superJump() {
        if (!isJumping && hasSuperJump) {
            velocity = -45;  // Salto más alto
            isJumping = true;
            superJumpDuration = 50; // Duración del super salto
        }
    }

    // Método para aplicar super velocidad
    public void superSpeed() {
        if (!hasSuperSpeed) {
            hasSuperSpeed = true;
            superSpeedDuration = 100; // Duración de la super velocidad
        }
    }

    // Método para activar el escenario loco
    public void crazyScenario() {
        if (!hasCrazyScenario) {
            hasCrazyScenario = true;
            crazyScenarioDuration = 100; // Duración del escenario loco
        }
    }

    // Actualizar la posición del dinosaurio y los power-ups
    public void update() {
        // Actualizar el salto
        rect.offset(0, velocity);
        velocity += 2;
        if (rect.bottom >= 900) {
            rect.offsetTo(100, 800);
            isJumping = false;
        }

        // Verificar la duración de los power-ups
        if (superJumpDuration > 0) {
            superJumpDuration--;
        } else {
            hasSuperJump = false; // Desactivar super salto cuando la duración termine
        }

        if (superSpeedDuration > 0) {
            superSpeedDuration--;
        } else {
            hasSuperSpeed = false; // Desactivar super velocidad cuando la duración termine
        }

        if (crazyScenarioDuration > 0) {
            crazyScenarioDuration--;
        } else {
            hasCrazyScenario = false; // Desactivar escenario loco cuando la duración termine
        }
    }

    // Dibujar el dinosaurio en el Canvas
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK); // Color base del dinosaurio
        canvas.drawRect(rect, paint);

        // Dibujar efectos de power-ups si es necesario
        if (hasSuperJump) {
            paint.setColor(Color.RED); // Color para indicar que tiene el super salto
            canvas.drawRect(rect, paint);
        }
        if (hasSuperSpeed) {
            paint.setColor(Color.GREEN); // Color para indicar que tiene super velocidad
            canvas.drawRect(rect, paint);
        }
        if (hasCrazyScenario) {
            paint.setColor(Color.BLUE); // Color para indicar el escenario loco
            canvas.drawRect(rect, paint);
        }
    }

    // Obtener el rectángulo para las colisiones
    public Rect getRect() {
        return rect;
    }

    // Obtener la altura del salto (más alto si tiene super salto)
    public int getJumpHeight() {
        return hasSuperJump ? 150 : 100; // Super salto es más alto
    }

    // Métodos para obtener el estado de los power-ups
    public boolean hasSuperJump() {
        return hasSuperJump;
    }

    public boolean hasSuperSpeed() {
        return hasSuperSpeed;
    }

    public boolean hasCrazyScenario() {
        return hasCrazyScenario;
    }
}
