package com.example.dinosaurio;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;
    private int playerX = 100, playerY = 500;
    private int playerSize = 100;
    private int obstacleX = 800, obstacleY = 500;
    private int obstacleWidth = 100, obstacleHeight = 100;
    private boolean isJumping = false, isCrouching = false;
    private int jumpHeight = 300;
    private int gravity = 20;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                gameThread.setRunning(false);
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.WHITE);

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);

            // Dibuja al jugador (cuadrado)
            canvas.drawRect(playerX, playerY, playerX + playerSize, playerY + playerSize, paint);

            // Dibuja el obstáculo (cuadrado)
            canvas.drawRect(obstacleX, obstacleY, obstacleX + obstacleWidth, obstacleY + obstacleHeight, paint);
        }
    }

    public void update() {
        // Movimiento de obstáculos
        obstacleX -= 20;
        if (obstacleX < 0) {
            obstacleX = getWidth();
        }

        // Salto
        if (isJumping) {
            playerY -= jumpHeight;
            isJumping = false;
        } else if (playerY < 500) {
            playerY += gravity;
        }

        // Agacharse
        if (isCrouching) {
            playerSize = 50;
        } else {
            playerSize = 100;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getX() < getWidth() / 2) {
                isJumping = true;
            } else {
                isCrouching = !isCrouching;
            }
        }
        return true;
    }
}
