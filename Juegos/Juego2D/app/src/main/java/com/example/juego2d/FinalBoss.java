package com.example.juego2d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class FinalBoss implements Obstacle {
    private static final int FINAL_BOSS_WIDTH = 120;  // Ancho del jefe final
    private static final int FINAL_BOSS_HEIGHT = 100; // Alto del jefe final
    private static int activeBossCount = 0;  // Contador de jefes activos

    private Rect rect;
    private int speed;
    private int groundLevel;  // Nivel del suelo
    private boolean isAttacking;  // Indica si el jefe final está atacando
    private boolean isJumping;  // Indica si el jefe final está saltando
    private int jumpVelocity;  // Velocidad vertical del salto
    private int screenWidth;  // Ancho de la pantalla
    private Bitmap finalBossImage;  // Bitmap para la imagen del jefe final

    public FinalBoss(int startX, int screenWidth, Context context) {
        if (activeBossCount >= 2) {
            throw new IllegalStateException("No se pueden crear más de dos jefes finales.");
        }

        this.screenWidth = screenWidth;
        groundLevel = 900;  // Nivel del suelo (ajusta según sea necesario)
        rect = new Rect(startX, groundLevel - FINAL_BOSS_HEIGHT, startX + FINAL_BOSS_WIDTH, groundLevel);
        speed = 10;  // Velocidad de movimiento hacia la izquierda
        isAttacking = false;
        isJumping = false;
        jumpVelocity = 0;

        // Cargar la imagen del jefe final desde los recursos
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.final_boss);

        // Redimensionar la imagen al tamaño del jefe final (FINAL_BOSS_WIDTH x FINAL_BOSS_HEIGHT)
        finalBossImage = Bitmap.createScaledBitmap(originalBitmap, FINAL_BOSS_WIDTH, FINAL_BOSS_HEIGHT, true);

        activeBossCount++;  // Incrementar el contador de jefes activos
    }

    @Override
    public void update() {
        // Este método no se usa, pero debe estar presente para cumplir con la interfaz Obstacle
    }

    @Override
    public void update(int speedMultiplier) {
        if (!isAttacking && !isJumping) {
            // Elige aleatoriamente entre atacar o saltar
            if (Math.random() < 0.01) {  // Probabilidad de 1% de atacar
                isAttacking = true;
            } else if (Math.random() < 0.01) {  // Probabilidad de 1% de saltar
                isJumping = true;
                jumpVelocity = -30;  // Velocidad inicial del salto
            }
        }

        if (isAttacking) {
            // Moverse rápidamente de derecha a izquierda
            rect.offset(-speed * 2 * speedMultiplier, 0);  // Mayor velocidad para el ataque

            // Si sale de la pantalla, reaparece por la derecha
            if (rect.right < 0) {
                resetPosition();
            }
        } else if (isJumping) {
            // Lógica de salto
            rect.offset(0, jumpVelocity);
            jumpVelocity += 1;  // Gravedad

            // Si toca el suelo, detiene el salto
            if (rect.bottom >= groundLevel) {
                rect.bottom = groundLevel;
                rect.top = groundLevel - FINAL_BOSS_HEIGHT;
                isJumping = false;
                jumpVelocity = 0;
                resetPosition();  // Reaparece por la derecha
            }
        } else {
            // Movimiento normal hacia la izquierda
            rect.offset(-speed * speedMultiplier, 0);

            // Si sale de la pantalla, reaparece por la derecha
            if (rect.right < 0) {
                resetPosition();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        // Dibujar la imagen del jefe final en lugar del rectángulo rojo
        if (finalBossImage != null) {
            canvas.drawBitmap(finalBossImage, null, rect, null);
        }
    }

    @Override
    public Rect getRect() {
        return rect;
    }

    // Método para resetear la posición del jefe final
    private void resetPosition() {
        rect.left = screenWidth;
        rect.right = screenWidth + FINAL_BOSS_WIDTH;
        rect.top = groundLevel - FINAL_BOSS_HEIGHT;
        rect.bottom = groundLevel;
        isAttacking = false;
        isJumping = false;
    }

    // Método estático para generar la posición del siguiente jefe final
    public static int getNextFinalBossPosition(int screenWidth) {
        return screenWidth + 500;  // Separación mínima entre jefes finales
    }

    // Método para liberar un jefe final (cuando sale de la pantalla o es eliminado)
    public void release() {
        activeBossCount--;  // Decrementar el contador de jefes activos
    }

    // Método estático para verificar si se puede crear un nuevo jefe final
    public static boolean canCreateNewBoss() {
        return activeBossCount < 2;  // Solo permitir crear un nuevo jefe si hay menos de 2 activos
    }
}