package com.example.juego2d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;

public class Player {
    private Rect rect;
    private int velocityY;
    private boolean isJumping;
    private boolean hasSuperJump;
    private boolean hasSuperSpeed;
    private boolean hasCrazyScenario;
    private int superJumpDuration;
    private int superSpeedDuration;
    private int crazyScenarioDuration;
    private static final int GRAVITY = 2;
    private static final int JUMP_STRENGTH = -35;
    private static final int SUPER_JUMP_STRENGTH = -45;
    private boolean crazyScenarioActive;
    private Bitmap dinosaurioImage; // Bitmap para la imagen del dinosaurio
    private Context context; // Contexto para cargar la imagen

    public Player(Context context) {
        this.context = context;
        rect = new Rect(100, 750, 200, 900); // Rectángulo inicial del dinosaurio (150px de alto)
        velocityY = 0;
        isJumping = false;
        hasSuperJump = false;
        hasSuperSpeed = false;
        hasCrazyScenario = false;
        superJumpDuration = 0;
        superSpeedDuration = 0;
        crazyScenarioDuration = 0;
        crazyScenarioActive = false;

        // Cargar la imagen del dinosaurio desde los recursos
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);

        // Redimensionar la imagen al tamaño del rectángulo (100x150 píxeles)
        dinosaurioImage = Bitmap.createScaledBitmap(originalBitmap, rect.width(), rect.height(), true);
    }

    public void jump() {
        if (!isJumping) {
            velocityY = hasSuperJump ? SUPER_JUMP_STRENGTH : JUMP_STRENGTH;  // Salto normal o super salto
            isJumping = true;

            // Reproducir sonido al saltar
            playSound(R.raw.jump);
        }
    }

    private void playSound(int soundResource) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, soundResource);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release(); // Liberar recursos después de reproducir el sonido
            }
        });
        mediaPlayer.start(); // Reproducir el sonido
    }

    public void activateSuperJump() {
        hasSuperJump = true;
        superJumpDuration = 100; // Duración extendida
    }

    public void activateSuperSpeed() {
        hasSuperSpeed = true;
        superSpeedDuration = 100;
    }

    public void activateCrazyScenario() {
        hasCrazyScenario = true;
        crazyScenarioActive = true;
        crazyScenarioDuration = 100;
    }

    public void deactivateCrazyScenario() {
        hasCrazyScenario = false;
        crazyScenarioActive = false;
    }

    public boolean isCrazyScenarioActive() {
        return crazyScenarioActive;
    }

    public boolean hasSuperSpeed() {
        return hasSuperSpeed;
    }

    public void update() {
        // Aplicar gravedad
        velocityY += GRAVITY;
        rect.offset(0, velocityY);

        // Si toca el suelo, detener el salto
        if (rect.bottom >= 900) {
            rect.offsetTo(100, 750); // Ajusta la posición vertical para que el personaje esté en el suelo
            isJumping = false;
            velocityY = 0;
        }

        // Reducir duración de los efectos de power-ups
        if (superJumpDuration > 0) {
            superJumpDuration--;
        } else {
            hasSuperJump = false;
        }

        if (superSpeedDuration > 0) {
            superSpeedDuration--;
        } else {
            hasSuperSpeed = false;
        }

        if (crazyScenarioDuration > 0) {
            crazyScenarioDuration--;
        } else {
            deactivateCrazyScenario();
        }
    }

    public void draw(Canvas canvas) {
        if (dinosaurioImage != null) {
            // Dibujar la imagen del dinosaurio
            canvas.drawBitmap(dinosaurioImage, null, rect, null);
        }
    }

    public Rect getRect() {
        return rect;
    }
}