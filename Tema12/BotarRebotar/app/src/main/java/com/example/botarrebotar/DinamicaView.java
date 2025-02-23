package com.example.botarrebotar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DinamicaView extends View implements Runnable {

    private int x, y, xmax, ymax;
    private float velocidadX = 5.0f;
    private float velocidadY = 5.0f;
    private float aceleracionX = 0.02f;
    private float aceleracionY = 0.02f;
    private int dt = 10;
    private boolean continuar = true;
    private int tiempo = 0;
    private int radio = 30; // Radio de la bola

    private Paint paintFondo, paintParticula, paint;
    private float s;
    private Thread hilo;

    public DinamicaView(Context context) {
        super(context);

        // Obtener escala de densidad de pantalla
        s = getResources().getDisplayMetrics().density;

        // Colores para el dibujo
        paintFondo = new Paint();
        paintParticula = new Paint();
        paint = new Paint();

        paintFondo.setColor(Color.WHITE);
        paintParticula.setColor(Color.RED);
        paint.setColor(Color.BLACK);
    }

    @Override
    public void run() {
        while (continuar) {
            tiempo += dt;

            // Aplicar aceleración a la velocidad en ambos ejes
            velocidadX += aceleracionX * dt;
            velocidadY += aceleracionY * dt;

            // Movimiento en ambos ejes
            x += (int) (velocidadX * dt);
            y += (int) (velocidadY * dt);

            // Rebote en los bordes laterales
            if (x + radio > xmax) {
                x = xmax - radio;
                velocidadX = -Math.abs(velocidadX); // Invierte dirección hacia la izquierda
            } else if (x - radio < 0) {
                x = radio;
                velocidadX = Math.abs(velocidadX); // Invierte dirección hacia la derecha
            }

            // Rebote en los bordes superior e inferior
            if (y + radio > ymax) {
                y = ymax - radio;
                velocidadY = -Math.abs(velocidadY); // Invierte dirección hacia arriba
            } else if (y - radio < 0) {
                y = radio;
                velocidadY = Math.abs(velocidadY); // Invierte dirección hacia abajo
            }

            postInvalidate();

            try {
                Thread.sleep(dt);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Obtiene geometría del canvas
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        xmax = w;
        ymax = h;

        // Posición inicial en el centro de la pantalla
        x = xmax / 2;
        y = ymax / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(paintFondo);

        paint.setTextSize(20 * s);

        // Dibujar la bola
        canvas.drawCircle(x, y, radio, paintParticula);

        // Mostrar información en pantalla
        canvas.drawText("x=" + x, 10 * s, 25 * s, paint);
        canvas.drawText("y=" + y, 10 * s, 50 * s, paint);
        canvas.drawText("tiempo=" + tiempo, 10 * s, 75 * s, paint);
        canvas.drawText("velocidadX=" + velocidadX, 10 * s, 100 * s, paint);
        canvas.drawText("velocidadY=" + velocidadY, 10 * s, 125 * s, paint);
    }

    // Métodos para controlar la animación desde MainActivity
    public void iniciarAnimacion() {
        continuar = true;
        if (hilo == null || !hilo.isAlive()) {
            hilo = new Thread(this);
            hilo.start();
        }
    }

    public void detenerAnimacion() {
        continuar = false;
    }
}