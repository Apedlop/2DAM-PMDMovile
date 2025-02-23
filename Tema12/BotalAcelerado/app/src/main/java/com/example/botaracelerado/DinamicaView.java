package com.example.botaracelerado;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DinamicaView extends View implements Runnable {

    private int x, y, ymax;
    private float velocidad = 1.5f;
    private float aceleracion = 0.01f;
    private int dt = 10;
    private boolean continuar = true;
    private int tiempo = 0;

    private Paint paintFondo, paintParticula, paint;
    private float s = getResources().getDisplayMetrics().density;

    public DinamicaView(Context context) {
        super(context);

        // Colores para el dibujo y el tamaño del texto
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

            // Aplicar aceleración a la velocidad
            velocidad += aceleracion * dt;

            // Movimiento rectilíneo uniformemente acelerado
            y += (int) (velocidad * dt);

            // Si llega abajo, invertir la velocidad
            if (y > ymax) {
                velocidad = -Math.abs(velocidad);
            }

            // Si llega arriba, invertir la velocidad
            if (y < 0) {
                velocidad = Math.abs(velocidad);
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
        x = w / 2;
        y = 0;
        ymax = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(paintFondo);

        paint.setTextSize(20 * s);

        canvas.drawCircle(x, y, 30 * s, paintParticula);
        canvas.drawText("y=" + y, 10 * s, 25 * s, paint);
        canvas.drawText("tiempo=" + tiempo, 10 * s, 50 * s, paint);
        canvas.drawText("velocidad=" + velocidad, 10 * s, 75 * s, paint);
    }

    // Métodos para controlar la animación desde MainActivity
    public void iniciarAnimacion() {
        continuar = true;
        Thread hilo = new Thread(this);
        hilo.start();
    }

    public void detenerAnimacion() {
        continuar = false;
    }
}