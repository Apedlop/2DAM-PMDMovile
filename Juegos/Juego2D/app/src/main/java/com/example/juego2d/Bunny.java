package com.example.juego2d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Bunny implements Obstacle {
    private static int lastBunnyX = 0;  // Última posición X del conejo (del lado izquierdo)
    private static final int BUNNY_WIDTH = 80;  // Ancho del conejo
    private static final int BUNNY_HEIGHT = 80;  // Alto del conejo
    private static final int MIN_SEPARATION = 50;  // Separación mínima de 50 píxeles
    private Rect rect;
    private int speed;
    private int jumpVelocity;  // Velocidad vertical del salto
    private boolean isJumping;  // Indica si el conejo está saltando
    private int groundLevel;  // Nivel del suelo
    private Bitmap spritesheet;  // Spritesheet del conejo
    private Rect frameRect;  // Rectángulo que define el frame actual del spritesheet
    private int currentFrame;  // Frame actual de la animación
    private long lastFrameTime;  // Tiempo del último cambio de frame
    private static final int FRAME_COUNT = 1;  // Número de frames en el spritesheet
    private static final long FRAME_DURATION = 50;  // Duración de cada frame en milisegundos

    public Bunny(int screenWidth, Context context) {
        // El conejo aparece un poco antes de la pantalla (screenWidth - BUNNY_WIDTH)
        int xPosition = screenWidth - BUNNY_WIDTH;  // Comienza desde el borde derecho de la pantalla
        groundLevel = 900;  // Nivel del suelo (ajusta según sea necesario)
        rect = new Rect(xPosition, groundLevel - BUNNY_HEIGHT, xPosition + BUNNY_WIDTH, groundLevel);
        lastBunnyX = xPosition;  // Guarda la última posición X
        speed = 10;  // Velocidad de movimiento hacia la izquierda
        jumpVelocity = 0;  // Inicialmente el salto está detenido
        isJumping = false;  // Inicialmente, el conejo no está saltando

        // Cargar el spritesheet desde los recursos
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bunny_sprite);

        // Redimensionar el spritesheet al tamaño del cuadrado (BUNNY_WIDTH x BUNNY_HEIGHT)
        spritesheet = Bitmap.createScaledBitmap(originalBitmap, BUNNY_WIDTH, BUNNY_HEIGHT, true);

        // Inicializar el rectángulo del frame actual
        frameRect = new Rect(0, 0, BUNNY_WIDTH, BUNNY_HEIGHT);
        currentFrame = 0;  // Empezar con el primer frame
        lastFrameTime = System.currentTimeMillis();  // Registrar el tiempo inicial
    }

    @Override
    public void update() {

    }

    @Override
    public void update(int speedMultiplier) {
        // Mueve el conejo hacia la izquierda
        rect.offset(-speed * speedMultiplier, 0);

        // Lógica de salto
        if (isJumping) {
            // Aplica la velocidad vertical (salto y caída)
            rect.offset(0, jumpVelocity);

            // Aplica gravedad (aumenta la velocidad hacia abajo)
            jumpVelocity += 1;

            // Si el conejo toca el suelo, detiene el salto
            if (rect.bottom >= groundLevel) {
                rect.bottom = groundLevel;  // Asegura que no pase del suelo
                rect.top = groundLevel - BUNNY_HEIGHT;
                isJumping = false;  // Detiene el salto
                jumpVelocity = 0;  // Reinicia la velocidad del salto
            }
        } else {
            // Probabilidad de que inicie un salto (1% de probabilidad)
            if (Math.random() < 0.005) {
                isJumping = true;
                jumpVelocity = -20;  // Velocidad inicial del salto (hacia arriba)
            }
        }

        // Actualizar la animación
        updateAnimation();
    }

    private void updateAnimation() {
        // Cambiar el frame si ha pasado el tiempo suficiente
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime > FRAME_DURATION) {
            currentFrame = (currentFrame + 1) % FRAME_COUNT;  // Avanzar al siguiente frame
            frameRect.left = currentFrame * BUNNY_WIDTH;  // Calcular la posición X del frame en el spritesheet
            frameRect.right = frameRect.left + BUNNY_WIDTH;
            lastFrameTime = currentTime;  // Actualizar el tiempo del último cambio de frame
        }
    }

    @Override
    public void draw(Canvas canvas) {
        // Dibujar el frame actual del spritesheet
        if (spritesheet != null) {
            canvas.drawBitmap(spritesheet, frameRect, rect, null);
        }
    }

    @Override
    public Rect getRect() {
        return rect;
    }

    // Método estático para generar nuevos conejos con la separación mínima entre ellos
    public static int getNextBunnyPosition(int screenWidth) {
        // Se asegura que el siguiente conejo se coloque con separación mínima
        return lastBunnyX + MIN_SEPARATION + BUNNY_WIDTH;
    }
}