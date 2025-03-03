package com.example.juego2d;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class TreeBranch implements Obstacle {
    private static int lastTreeBranchX = 0;  // Última posición X de la rama (del lado izquierdo)
    private static final int TREE_BRANCH_WIDTH = 50;  // Ancho de la rama
    private static final int TREE_BRANCH_HEIGHT = 50;  // Alto de la rama
    private static final int MIN_SEPARATION = 50;  // Separación mínima de 50 píxeles
    private Rect rect;
    private int speed;
    private int jumpVelocity;  // Velocidad vertical del salto
    private boolean isJumping;  // Indica si el conejo está saltando
    private int groundLevel;  // Nivel del suelo

    public TreeBranch(int screenWidth) {
        // La rama aparece desde el borde derecho de la pantalla
        int xPosition = screenWidth;  // Comienza desde el borde derecho de la pantalla
        groundLevel = 900;  // Nivel del suelo (ajusta según sea necesario)
        rect = new Rect(xPosition, groundLevel - TREE_BRANCH_HEIGHT, xPosition + TREE_BRANCH_WIDTH, groundLevel);  // Crea el rectángulo de la rama
        lastTreeBranchX = xPosition;  // Guarda la última posición X
        speed = 10;  // Velocidad de movimiento hacia la izquierda
        jumpVelocity = -10;  // Velocidad inicial del salto (hacia arriba)
        isJumping = false;  // Inicialmente, el conejo no está saltando
    }

    @Override
    public void update() {
        // Mover el conejo hacia la izquierda
        rect.offset(-speed, 0);

        // Lógica de salto
        if (isJumping) {
            rect.offset(0, jumpVelocity);  // Mover el conejo verticalmente
            jumpVelocity += 1;  // Aplicar gravedad (aumentar la velocidad hacia abajo)

            // Si el conejo toca el suelo, detener el salto
            if (rect.bottom >= groundLevel) {
                rect.bottom = groundLevel;
                rect.top = groundLevel - TREE_BRANCH_HEIGHT;
                isJumping = false;
            }
        } else {
            // Iniciar un nuevo salto de manera aleatoria
            if (Math.random() < 0.005) {  // Probabilidad de saltar (0.5%)
                isJumping = true;
                jumpVelocity = -20;  // Reiniciar la velocidad del salto
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);  // Color de la rama (o conejo)
        canvas.drawRect(rect, paint);  // Dibuja la rama (o conejo)
    }

    @Override
    public Rect getRect() {
        return rect;  // Devuelve el rectángulo de la rama (o conejo)
    }

    // Método estático para generar nuevas ramas con la separación mínima entre ellas
    public static int getNextTreeBranchPosition(int screenWidth) {
        // Se asegura que la siguiente rama se coloque con separación mínima
        return lastTreeBranchX + MIN_SEPARATION + TREE_BRANCH_WIDTH;
    }
}