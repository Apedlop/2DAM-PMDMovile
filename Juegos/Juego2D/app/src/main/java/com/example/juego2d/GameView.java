package com.example.juego2d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread thread;
    private Player dino;
    private ArrayList<Obstacle> obstacles; // Lista de obstáculos
    private ArrayList<PowerUp> powerUps; // Lista de power-ups
    private Paint paint;
    private int score;
    private boolean isGameOver;
    private int currentScenario;
    private static final int DESIERTO = 0;
    private static final int BOSQUE = 1;
    private static final int CIUDAD = 2;
    private static final int NIVEL_FINAL = 3; // Nuevo escenario final
    private static final int MIN_SEPARATION = 300; // Separación mínima entre obstáculos
    private Random random;
    private static final int POWERUP_INTERVAL = 500; // Intervalo de puntos para generar power-ups
    private int lastPowerUpScore = 0; // Última puntuación en la que se generó un power-up
    private long shakeStartTime = 0; // Guarda el tiempo de inicio del temblor
    private boolean isShaking = false; // Indica si el temblor está activo
    private boolean isFinalLevel = false; // Indica si estamos en el nivel final
    private boolean hasWon = false; // Indica si el jugador ha ganado
    private Rect playAgainButton; // Rectángulo del botón "Volver a jugar"
    private boolean isGameOverButtonVisible = false; // Indica si el botón está visible

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);
        dino = new Player(context);
        obstacles = new ArrayList<>();
        powerUps = new ArrayList<>();
        paint = new Paint();
        score = 0;
        isGameOver = false;
        currentScenario = DESIERTO;
        random = new Random();

        // Inicializar el botón "Volver a jugar"
        playAgainButton = new Rect();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!thread.isRunning()) {
            thread.setRunning(true);
            thread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // No es necesario hacer nada aquí, pero el método debe estar presente
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isGameOver && isGameOverButtonVisible) {
                // Verificar si el toque está dentro del botón "Volver a jugar"
                if (playAgainButton.contains((int) event.getX(), (int) event.getY())) {
                    resetGame(); // Reiniciar el juego
                    return true;
                }
            } else if (!isGameOver && !hasWon) {
                dino.jump(); // Saltar si el juego está en progreso
            }
        }
        return true;
    }

    private void resetGame() {
        // Reiniciar todas las variables del juego
        dino = new Player(getContext());
        obstacles.clear();
        powerUps.clear();
        score = 0;
        isGameOver = false;
        isGameOverButtonVisible = false;
        currentScenario = DESIERTO;
        isFinalLevel = false;
        hasWon = false;
    }

    public void update() {
        if (!isGameOver && !hasWon) {
            dino.update();

            // Actualizar y verificar colisiones con los obstáculos
            int speedMultiplier = dino.hasSuperSpeed() ? 2 : 1; // Multiplicador para super velocidad

            for (Obstacle obstacle : obstacles) {
                obstacle.update(speedMultiplier); // Pasar el multiplicador de velocidad
                if (Rect.intersects(dino.getRect(), obstacle.getRect())) {
                    isGameOver = true;

                    // Reproducir sonido al colisionar con un obstáculo
                    playSound(R.raw.game_over); // Asegúrate de que el archivo esté en res/raw/collision_sound.mp3
                }
            }

            // Actualizar y verificar colisiones con los power-ups
            for (PowerUp powerUp : powerUps) {
                powerUp.update(); // Actualizar la posición del power-up
                if (powerUp.isActive() && Rect.intersects(dino.getRect(), powerUp.getRect())) {
                    powerUp.applyEffect(dino); // Aplicar el efecto del power-up al dinosaurio
                    powerUp.deactivate(); // Desactivar el power-up después de ser recogido

                    // Reproducir sonido al recoger el power-up
                    playSound(R.raw.power_up_sound); // Asegúrate de que el archivo esté en res/raw/powerup_sound.mp3

                    // Si el power-up es CrazyScenario, activar el temblor
                    if (dino.isCrazyScenarioActive()) {
                        isShaking = true; // Iniciar el temblor
                        shakeStartTime = System.currentTimeMillis(); // Registrar el inicio del temblor
                    }
                }
            }

            // Eliminar power-ups inactivos
            powerUps.removeIf(powerUp -> !powerUp.isActive());

            // Eliminar obstáculos que salen de la pantalla
            obstacles.removeIf(obstacle -> obstacle.getRect().right < 0);

            // Generar nuevos obstáculos específicos para cada escenario
            if (random.nextInt(100) < 2 && canGenerateNewObstacle()) {
                addNewObstacle();
            }

            // Generar power-ups cada 500 puntos
            if (score % 500 == 0 && score - lastPowerUpScore >= POWERUP_INTERVAL) {
                addNewPowerUp();
            }

            score++;

            // Cambiar de escenario en momentos clave y regenerar obstáculos
            if (score >= 1000 && currentScenario == DESIERTO) {
                changeScenario(BOSQUE);
            } else if (score >= 2000 && currentScenario == BOSQUE) {
                changeScenario(CIUDAD);
            } else if (score >= 3000 && currentScenario == CIUDAD) {
                changeScenario(NIVEL_FINAL); // Cambiar al nivel final
                isFinalLevel = true; // Activar el nivel final
            }

            // Verificar si el jugador ha ganado (score >= 4000 en el nivel final)
            if (isFinalLevel && score >= 4000) {
                hasWon = true; // El jugador ha ganado
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            // Dibujar el fondo personalizado
            drawBackground(canvas);

            if (isShaking) {
                canvas.save();  // Guardar el estado antes de aplicar el temblor
                applyShakeEffect(canvas);
            }

            dino.draw(canvas);
            for (Obstacle obstacle : obstacles) {
                obstacle.draw(canvas);
            }
            for (PowerUp powerUp : powerUps) {
                powerUp.draw(canvas, paint);
            }
            paint.setColor(Color.BLACK);
            paint.setTextSize(50);
            canvas.drawText("Score: " + score, 50, 100, paint);

            if (isGameOver) {
                // Mostrar "Game Over"
                paint.setTextSize(100);
                canvas.drawText("Game Over", getWidth() / 2 - 200, getHeight() / 2, paint);

                // Dibujar el botón "Volver a jugar"
                drawPlayAgainButton(canvas);
                isGameOverButtonVisible = true; // Hacer visible el botón
            }

            if (hasWon) {
                paint.setTextSize(100);
                canvas.drawText("You Win!", getWidth() / 2 - 200, getHeight() / 2, paint);
                // Dibujar el botón "Volver a jugar"
                drawPlayAgainButton(canvas);
                isGameOverButtonVisible = true; // Hacer visible el botón
            }

            if (isShaking) {
                canvas.restore(); // Restaurar el estado después del temblor
            }
        } else {
            Log.e("GameView", "Canvas es null");
        }
    }

    private void drawPlayAgainButton(Canvas canvas) {
        // Definir el tamaño y la posición del botón
        int buttonWidth = 400;
        int buttonHeight = 150;
        int buttonLeft = getWidth() / 2 - buttonWidth / 2;
        int buttonTop = getHeight() / 2 + 100;
        int buttonRight = buttonLeft + buttonWidth;
        int buttonBottom = buttonTop + buttonHeight;

        // Guardar las coordenadas del botón
        playAgainButton.set(buttonLeft, buttonTop, buttonRight, buttonBottom);

        // Dibujar el fondo del botón
        paint.setColor(Color.rgb(128, 0, 0)); // Color verde
        canvas.drawRect(playAgainButton, paint);

        // Dibujar el texto del botón
        paint.setColor(Color.WHITE);
        paint.setTextSize(60);
        String buttonText = "Volver a jugar";
        float textWidth = paint.measureText(buttonText);
        float textX = buttonLeft + (buttonWidth - textWidth) / 2;
        float textY = buttonTop + (buttonHeight + 60) / 2; // Centrar verticalmente
        canvas.drawText(buttonText, textX, textY, paint);
    }

    private void drawBackground(Canvas canvas) {
        // Dibujar el fondo según el escenario actual
        switch (currentScenario) {
            case DESIERTO:
                drawDesertBackground(canvas);
                break;
            case BOSQUE:
                drawForestBackground(canvas);
                break;
            case CIUDAD:
                drawCityBackground(canvas);
                break;
            case NIVEL_FINAL:
                drawFinalLevelBackground(canvas);
                break;
        }
    }

    private void drawDesertBackground(Canvas canvas) {
        // Fondo sólido de color arena
        canvas.drawColor(Color.rgb(255, 165, 0)); // Color naranja

        // Dibujar dunas de arena (más simples)
        paint.setColor(Color.rgb(240, 220, 130)); // Color amarillo arena brillante
        Path dunePath = new Path();
        dunePath.moveTo(0, getHeight() - 100);
        dunePath.quadTo(getWidth() / 4, getHeight() - 150, getWidth() / 2, getHeight() - 100);
        dunePath.quadTo(3 * getWidth() / 4, getHeight() - 80, getWidth(), getHeight() - 100);
        dunePath.lineTo(getWidth(), getHeight());
        dunePath.lineTo(0, getHeight());
        dunePath.close();
        canvas.drawPath(dunePath, paint);

        // Dibujar un sol simplificado
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(getWidth() - 100, 100, 50, paint);
    }

    private void drawForestBackground(Canvas canvas) {
        // Fondo sólido de color azul claro para el cielo
        canvas.drawColor(Color.rgb(135, 206, 235)); // Azul claro para el cielo

        // Dibujar el sol
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(getWidth() - 150, 150, 80, paint); // Sol en la esquina superior derecha

        // Fondo de pasto (verde oscuro)
        Paint grassPaint = new Paint();
        grassPaint.setColor(Color.rgb(34, 139, 34)); // Verde oscuro para el pasto
        canvas.drawRect(0, getHeight() / 2, getWidth(), getHeight(), grassPaint);

        // Dibujar árboles con variaciones en tamaño, posición y tonos para simular profundidad
        paint.setColor(Color.rgb(34, 139, 34)); // Color base verde oscuro para los árboles cercanos

        // Árboles cercanos (más grandes y oscuros)
        int treeWidth = 120; // Ancho base
        int treeHeight = 300; // Altura base
        int treeX, treeY;

        // Árbol 1 (cercano)
        treeX = getWidth() / 6;
        treeY = getHeight() - treeHeight - 50;
        drawTree(canvas, treeX, treeY, treeWidth, treeHeight, Color.rgb(34, 139, 34)); // Verde oscuro

        // Árbol 2 (cercano)
        treeX = getWidth() / 3;
        drawTree(canvas, treeX, treeY, treeWidth + 20, treeHeight + 30, Color.rgb(34, 139, 34)); // Verde oscuro

        // Árbol 3 (cercano)
        treeX = getWidth() / 2;
        drawTree(canvas, treeX, treeY + 20, treeWidth - 10, treeHeight - 20, Color.rgb(34, 139, 34)); // Verde oscuro

        // Árboles medianos (un poco más lejos, tonos más claros)
        paint.setColor(Color.rgb(50, 205, 50)); // Verde más claro para árboles medianos

        // Árbol 4 (mediano)
        treeX = (int) (getWidth() * 0.66);
        drawTree(canvas, treeX, treeY, treeWidth - 20, treeHeight - 50, Color.rgb(50, 205, 50)); // Verde claro

        // Árbol 5 (mediano)
        treeX = (int) (getWidth() * 0.83);
        drawTree(canvas, treeX, treeY + 30, treeWidth - 30, treeHeight - 60, Color.rgb(50, 205, 50)); // Verde claro

        // Árboles lejanos (más pequeños y tonos aún más claros)
        paint.setColor(Color.rgb(144, 238, 144)); // Verde muy claro para árboles lejanos

        // Árbol 6 (lejano)
        treeX = getWidth() / 4;
        treeY = getHeight() - treeHeight - 20;
        drawTree(canvas, treeX, treeY, treeWidth - 40, treeHeight - 100, Color.rgb(144, 238, 144)); // Verde muy claro

        // Árbol 7 (lejano)
        treeX = (int) (getWidth() * 0.6);
        drawTree(canvas, treeX, treeY, treeWidth - 50, treeHeight - 120, Color.rgb(144, 238, 144)); // Verde muy claro
    }

    private void drawTree(Canvas canvas, int x, int y, int width, int height, int color) {
        // Tronco del árbol (rectángulo)
        paint.setColor(Color.rgb(139, 69, 19)); // Color marrón
        canvas.drawRect(x + width / 4, y + height / 2, x + 3 * width / 4, y + height, paint);

        // Copa del árbol (triángulo)
        paint.setColor(color); // Usar el color pasado como parámetro
        Path treePath = new Path();
        treePath.moveTo(x + width / 2, y); // Punto superior
        treePath.lineTo(x, y + height / 2); // Esquina inferior izquierda
        treePath.lineTo(x + width, y + height / 2); // Esquina inferior derecha
        treePath.close(); // Cerrar el triángulo
        canvas.drawPath(treePath, paint);

        // Detalle adicional: sombra en la copa del árbol (opcional)
        paint.setColor(Color.rgb(0, 100, 0)); // Verde más oscuro para la sombra
        Path shadowPath = new Path();
        shadowPath.moveTo(x + width / 2, y + 20); // Punto superior con desplazamiento
        shadowPath.lineTo(x + 20, y + height / 2); // Esquina inferior izquierda con desplazamiento
        shadowPath.lineTo(x + width - 20, y + height / 2); // Esquina inferior derecha con desplazamiento
        shadowPath.close(); // Cerrar el triángulo
        canvas.drawPath(shadowPath, paint);
    }

    private void drawCityBackground(Canvas canvas) {
        // Fondo sólido de color gris claro
        canvas.drawColor(Color.rgb(211, 211, 211)); // Gris claro para el cielo

        // Dibujar edificios con diferentes alturas y detalles
        paint.setColor(Color.rgb(105, 105, 105)); // Color gris oscuro para los edificios
        int buildingWidth = 120; // Ancho base de los edificios
        int buildingHeight;
        int buildingX, buildingY;

        // Edificio 1 (más alto)
        buildingX = getWidth() / 6;
        buildingHeight = 350;
        buildingY = getHeight() - buildingHeight - 50;
        drawBuilding(canvas, buildingX, buildingY, buildingWidth, buildingHeight);

        // Edificio 2 (mediano)
        buildingX = getWidth() / 3;
        buildingHeight = 300;
        buildingY = getHeight() - buildingHeight - 50;
        drawBuilding(canvas, buildingX, buildingY, buildingWidth, buildingHeight);

        // Edificio 3 (más bajo)
        buildingX = getWidth() / 2;
        buildingHeight = 250;
        buildingY = getHeight() - buildingHeight - 50;
        drawBuilding(canvas, buildingX, buildingY, buildingWidth, buildingHeight);

        // Edificio 4 (más alto)
        buildingX = (int) (getWidth() * 0.66);
        buildingHeight = 400;
        buildingY = getHeight() - buildingHeight - 50;
        drawBuilding(canvas, buildingX, buildingY, buildingWidth, buildingHeight);

        // Edificio 5 (mediano)
        buildingX = (int) (getWidth() * 0.83);
        buildingHeight = 320;
        buildingY = getHeight() - buildingHeight - 50;
        drawBuilding(canvas, buildingX, buildingY, buildingWidth, buildingHeight);

        // Dibujar detalles adicionales (farolas o postes)
        paint.setColor(Color.rgb(169, 169, 169)); // Gris medio para las farolas
        for (int i = 0; i < 3; i++) {
            int lampX = getWidth() / 4 + i * (getWidth() / 4);
            int lampY = getHeight() - 50;
            drawStreetLamp(canvas, lampX, lampY, 10, 50); // Dibujar farolas
        }
    }

    private void drawBuilding(Canvas canvas, int x, int y, int width, int height) {
        // Dibujar el edificio (rectángulo)
        canvas.drawRect(x, y, x + width, y + height, paint);

        // Dibujar ventanas (pequeños rectángulos con variaciones)
        paint.setColor(Color.YELLOW);
        int windowWidth = 20;
        int windowHeight = 30;
        int windowSpacing = 10;

        for (int i = 0; i < 5; i++) { // Más filas de ventanas
            for (int j = 0; j < 4; j++) { // Más columnas de ventanas
                int windowX = x + windowSpacing + j * (windowWidth + windowSpacing);
                int windowY = y + windowSpacing + i * (windowHeight + windowSpacing);
                // Alternar ventanas encendidas y apagadas
                if ((i + j) % 2 == 0) {
                    paint.setColor(Color.YELLOW); // Ventana encendida
                } else {
                    paint.setColor(Color.rgb(200, 200, 200)); // Ventana apagada (gris claro)
                }
                canvas.drawRect(windowX, windowY, windowX + windowWidth, windowY + windowHeight, paint);
            }
        }
    }

    private void drawStreetLamp(Canvas canvas, int x, int y, int width, int height) {
        // Dibujar el poste de la farola
        paint.setColor(Color.rgb(169, 169, 169)); // Gris medio
        canvas.drawRect(x - width / 2, y - height, x + width / 2, y, paint);

        // Dibujar la luz de la farola (círculo)
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(x, y - height, 15, paint);
    }

    private void drawFinalLevelBackground(Canvas canvas) {
        // Fondo de espacio (azul claro)
        canvas.drawColor(Color.rgb(70, 130, 180));

        // Dibujar estrellas
        paint.setColor(Color.WHITE);
        for (int i = 0; i < 100; i++) { // Más estrellas
            int starX = random.nextInt(getWidth());
            int starY = random.nextInt(getHeight());
            int starSize = random.nextInt(3) + 1; // Tamaño aleatorio de las estrellas (1-3 píxeles)
            canvas.drawCircle(starX, starY, starSize, paint);
        }

        // Dibujar una luna más grande
        paint.setColor(Color.rgb(200, 200, 200)); // Color gris claro para la luna
        int moonX = getWidth() - 120; // Ajustar posición X para que no quede muy cerca del borde
        int moonY = 120; // Ajustar posición Y para que no quede muy cerca del borde
        int moonRadius = 50; // Hacer la luna más grande (radio de 50 píxeles)
        canvas.drawCircle(moonX, moonY, moonRadius, paint);

        // Detalles de la luna (cráteres)
        paint.setColor(Color.rgb(180, 180, 180)); // Color gris más oscuro para los cráteres
        canvas.drawCircle(moonX - 20, moonY - 20, 10, paint); // Cráter 1 (más grande)
        canvas.drawCircle(moonX + 25, moonY + 15, 12, paint); // Cráter 2 (más grande)
        canvas.drawCircle(moonX + 10, moonY + 30, 8, paint);  // Cráter 3 (nuevo)
    }

    private void playSound(int soundResource) {
        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), soundResource);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release(); // Liberar recursos después de reproducir el sonido
            }
        });
        mediaPlayer.start(); // Reproducir el sonido
    }

    private void startShakeEffect() {
        shakeStartTime = System.currentTimeMillis(); // Registrar el tiempo de inicio
        isShaking = true;
    }

    private void applyShakeEffect(Canvas canvas) {
        long elapsedTime = System.currentTimeMillis() - shakeStartTime;
        if (elapsedTime < 2000) { // Si han pasado menos de 2 segundos
            int remainingTime = (int) (2000 - elapsedTime);

            // Hacer que la intensidad sea más fuerte al inicio y luego decrezca gradualmente
            int shakeStrength = Math.max(1, remainingTime / 100); // Hacer que la intensidad sea mayor al principio
            int shakeX = random.nextInt(shakeStrength * 4) - (shakeStrength * 2); // Mayor rango de movimiento en X
            int shakeY = random.nextInt(shakeStrength * 4) - (shakeStrength * 2); // Mayor rango de movimiento en Y

            // Aumentar el rango de movimiento para un temblor más exagerado
            canvas.translate(shakeX, shakeY);
        } else {
            isShaking = false; // Detener el temblor después de 2 segundos
        }
    }

    private boolean canGenerateNewObstacle() {
        if (obstacles.isEmpty()) {
            return true;
        }

        Obstacle lastObstacle = obstacles.get(obstacles.size() - 1);
        int distanceTraveled = getWidth() - lastObstacle.getRect().right;
        return distanceTraveled >= (MIN_SEPARATION + getObstacleWidth());
    }

    private void changeScenario(int newScenario) {
        currentScenario = newScenario;
        obstacles.clear();
    }

    private void addNewObstacle() {
        int screenWidth = getWidth();
        int nextObstacleX = screenWidth;

        if (!obstacles.isEmpty()) {
            Obstacle lastObstacle = obstacles.get(obstacles.size() - 1);
            if (lastObstacle instanceof Cactus) {
                nextObstacleX = Cactus.getNextCactusPosition(screenWidth);
            } else if (lastObstacle instanceof Bunny) {
                nextObstacleX = Bunny.getNextBunnyPosition(screenWidth);
            } else if (lastObstacle instanceof Vehicle) {
                nextObstacleX = Vehicle.getNextVehiclePosition(screenWidth);
            }
        }

        if (nextObstacleX > screenWidth) {
            nextObstacleX = screenWidth;
        }

        obstacles.add(createObstacle(nextObstacleX));
    }

    private Obstacle createObstacle(int positionX) {
        int groundLevel = getHeight() - 100;
        switch (currentScenario) {
            case BOSQUE:
                return new Bunny(positionX, getContext());
            case CIUDAD:
                return new Vehicle(positionX, getContext());
            case NIVEL_FINAL:
                return new FinalBoss(positionX, getWidth(), getContext()); // Jefe final en el nivel
            default:
                return new Cactus(positionX, getContext());
        }
    }

    private int getObstacleWidth() {
        switch (currentScenario) {
            case BOSQUE:
                return 50;
            case CIUDAD:
                return 100;
            case NIVEL_FINAL:
                return 150; // Obstáculo más grande para el nivel final
            default:
                return 50;
        }
    }

    private void addNewPowerUp() {
        if (powerUps.isEmpty() && score - lastPowerUpScore >= POWERUP_INTERVAL) {
            int screenWidth = getWidth();
            int randomPowerUpType = random.nextInt(3);

            PowerUp newPowerUp = null;
            switch (randomPowerUpType) {
                case 0:
                    newPowerUp = new SuperJump(screenWidth, getHeight() - 300, getContext());
                    break;
                case 1:
                    newPowerUp = new SuperSpeed(screenWidth, getHeight() - 300, getContext());
                    break;
                case 2:
                    newPowerUp = new CrazyScenario(screenWidth, getHeight() - 300, getContext());
                    break;
            }

            if (newPowerUp != null) {
                powerUps.add(newPowerUp);
                lastPowerUpScore = score; // Actualizar la última puntuación de generación
            }
        }
    }
}