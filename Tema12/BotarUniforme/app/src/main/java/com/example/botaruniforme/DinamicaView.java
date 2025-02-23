package com.example.botaruniforme;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DinamicaView extends View implements Runnable {

    private int x, y, ymax;
    private float velocidad = 1.5f;
    private int dt = 10;
    private int tiempo = 0;
    private boolean continuar = true;
    private float s;

    private Paint paintFondo, paintParticula, paint;

    public DinamicaView(Context context, float escala) {
        super(context);

        this.s = escala;

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

            // Movimiento rectilíneo uniforme: y = y + v * t
            y += (int) (velocidad * dt);

            // Si llega abajo, invierte la velocidad
            if (y > ymax) {
                y = ymax; // Evita que se salga
                velocidad = -velocidad;
            }

            // Si llega arriba, invierte la velocidad
            if (y < 0) {
                y = 0; // Evita valores negativos
                velocidad = -velocidad;
            }

            postInvalidate();

            try {
                Thread.sleep(dt);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

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
        canvas.drawText("y = " + y, 10 * s, 25 * s, paint);
        canvas.drawText("Tiempo = " + tiempo, 10 * s, 50 * s, paint);
    }

    public void detenerAnimacion() {
        continuar = false;
    }

    public void iniciarAnimacion() {
        continuar = true;
        new Thread(this).start();
    }
}
