package com.example.juego2d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread thread;
    private Dinosaurio dino;
    private ArrayList<Obstacle> obstacles; // Lista de obstáculos
    private ArrayList<PowerUp> powerUps; // Lista de power-ups
    private Paint paint;
    private int score;
    private boolean isGameOver;
    private int currentScenario;
    private static final int DESIERTO = 0;
    private static final int BOSQUE = 1;
    private static final int CIUDAD = 2;
    private static final int MIN_SEPARATION = 300; // Separación mínima entre obstáculos
    private static final int OBSTACLE_SPEED = 10; // Velocidad de los obstáculos
    private Random random;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);
        dino = new Dinosaurio();
        obstacles = new ArrayList<>();
        powerUps = new ArrayList<>();
        paint = new Paint();
        score = 0;
        isGameOver = false;
        currentScenario = DESIERTO;
        random = new Random();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && !isGameOver) {
            dino.jump();
        }
        return true;
    }

    // En el método update de GameView, asegúrate de generar los power-ups cada 500 puntos
    public void update() {
        if (!isGameOver) {
            dino.update();

            // Actualizar y verificar colisiones con los obstáculos
            for (Obstacle obstacle : obstacles) {
                obstacle.update();
                if (Rect.intersects(dino.getRect(), obstacle.getRect())) {
                    isGameOver = true;
                }
            }

            // Actualizar y verificar colisiones con los power-ups
            for (PowerUp powerUp : powerUps) {
                powerUp.update(); // Actualizar la posición del power-up
                if (powerUp.isActive() && Rect.intersects(dino.getRect(), powerUp.getRect())) {
                    powerUp.applyEffect(dino); // Aplicar el efecto del power-up al dinosaurio
                    powerUp.deactivate(); // Desactivar el power-up después de ser recogido
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
            if (score % 500 == 0) {
                addNewPowerUp();
            }

            score++;

            // Cambiar de escenario en momentos clave y regenerar obstáculos
            if (score >= 1000 && currentScenario == DESIERTO) {
                changeScenario(BOSQUE);
            } else if (score >= 2000 && currentScenario == BOSQUE) {
                changeScenario(CIUDAD);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(getBackgroundColor());
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
                paint.setTextSize(100);
                canvas.drawText("Game Over", getWidth() / 2 - 200, getHeight() / 2, paint);
            }
        }
    }

    private int getBackgroundColor() {
        switch (currentScenario) {
            case BOSQUE:
                return Color.GREEN;
            case CIUDAD:
                return Color.GRAY;
            default:
                return Color.YELLOW;
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
            } else if (lastObstacle instanceof TreeBranch) {
                nextObstacleX = TreeBranch.getNextTreeBranchPosition(screenWidth);
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
                return new TreeBranch(positionX);
            case CIUDAD:
                return new Vehicle(positionX);
            default:
                return new Cactus(positionX);
        }
    }

    private int getObstacleWidth() {
        switch (currentScenario) {
            case BOSQUE:
                return 50;
            case CIUDAD:
                return 100;
            default:
                return 50;
        }
    }

    private void addNewPowerUp() {
        if (powerUps.isEmpty()) { // Solo generar un power-up si no hay otro activo
            int screenWidth = getWidth();
            int nextPowerUpX = screenWidth;
            int randomPowerUpType = random.nextInt(3);

            PowerUp newPowerUp = null;
            switch (randomPowerUpType) {
                case 0:
                    newPowerUp = new SuperJump(nextPowerUpX, getHeight() - 200);
                    break;
                case 1:
                    newPowerUp = new SuperSpeed(nextPowerUpX, getHeight() - 200);
                    break;
                case 2:
                    newPowerUp = new CrazyScenario(nextPowerUpX, getHeight() - 200);
                    break;
            }

            if (newPowerUp != null) {
                powerUps.add(newPowerUp); // Agregar el power-up a la lista
            }
        }
    }
}
