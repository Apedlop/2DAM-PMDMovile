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
        paint = new Paint();
        score = 0;
        isGameOver = false;
        currentScenario = DESIERTO; // Comienza con el escenario Desierto
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

            // Eliminar obstáculos que salen de la pantalla
            obstacles.removeIf(obstacle -> obstacle.getRect().right < 0);

            // Generar nuevos obstáculos específicos para cada escenario
            if (random.nextInt(100) < 2 && canGenerateNewObstacle()) {
                addNewObstacle();
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
            canvas.drawColor(getBackgroundColor()); // Cambiar color de fondo según el escenario
            dino.draw(canvas);
            for (Obstacle obstacle : obstacles) {
                obstacle.draw(canvas);
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

    // Método para obtener el color de fondo según el escenario
    private int getBackgroundColor() {
        switch (currentScenario) {
            case BOSQUE:
                return Color.GREEN; // Color de fondo para el bosque
            case CIUDAD:
                return Color.GRAY; // Color de fondo para la ciudad
            default:
                return Color.YELLOW; // Color de fondo para el desierto
        }
    }

    // Método para verificar si se puede generar un nuevo obstáculo
    private boolean canGenerateNewObstacle() {
        if (obstacles.isEmpty()) {
            return true; // Si no hay obstáculos, generar uno nuevo
        }

        // Obtener el último obstáculo generado
        Obstacle lastObstacle = obstacles.get(obstacles.size() - 1);

        // Verificar si el último obstáculo ha recorrido al menos la separación mínima
        int distanceTraveled = getWidth() - lastObstacle.getRect().right;
        return distanceTraveled >= (MIN_SEPARATION + getObstacleWidth());
    }

    // Método para cambiar de escenario y regenerar obstáculos
    private void changeScenario(int newScenario) {
        currentScenario = newScenario;
        obstacles.clear(); // Limpiar los obstáculos anteriores
    }

    // Método para agregar un nuevo obstáculo según el escenario
    private void addNewObstacle() {
        int screenWidth = getWidth();
        int nextObstacleX = screenWidth; // Por defecto, el obstáculo aparece en el borde derecho

        if (!obstacles.isEmpty()) {
            // Obtener la posición del último obstáculo
            Obstacle lastObstacle = obstacles.get(obstacles.size() - 1);

            // Calcular la posición del próximo obstáculo con la separación mínima
            if (lastObstacle instanceof Cactus) {
                nextObstacleX = Cactus.getNextCactusPosition(screenWidth);
            } else if (lastObstacle instanceof TreeBranch) {
                nextObstacleX = TreeBranch.getNextTreeBranchPosition(screenWidth);
            } else if (lastObstacle instanceof Vehicle) {
                nextObstacleX = Vehicle.getNextVehiclePosition(screenWidth);
            }
        }

        // Asegurarse de que el obstáculo no se genere fuera de la pantalla
        if (nextObstacleX > screenWidth) {
            nextObstacleX = screenWidth;
        }

        obstacles.add(createObstacle(nextObstacleX)); // Generar un nuevo obstáculo
    }

    // Método para crear obstáculos específicos según el escenario actual
    private Obstacle createObstacle(int positionX) {
        int groundLevel = getHeight() - 100; // Nivel del suelo (ajusta según sea necesario)
        switch (currentScenario) {
            case BOSQUE:
                return new TreeBranch(positionX); // Genera una rama de árbol
            case CIUDAD:
                return new Vehicle(positionX); // Genera un coche
            default:
                return new Cactus(positionX); // Genera un cactus
        }
    }

    // Método para obtener el ancho de un obstáculo
    private int getObstacleWidth() {
        switch (currentScenario) {
            case BOSQUE:
                return 50; // Ancho de una rama de árbol
            case CIUDAD:
                return 100; // Ancho de un coche
            default:
                return 50; // Ancho de un cactus
        }
    }
}