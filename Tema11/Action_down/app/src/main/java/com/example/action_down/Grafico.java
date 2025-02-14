package com.example.action_down;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class Grafico extends View {
    private float x = 50;
    private float y = 50;
    private float radio;
    private Paint paint;

    public Grafico(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Obtener densidad de pantalla para ajustar el radio
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float densidad = metrics.density;
        radio = 20 * densidad;

        // Configurar el pincel para el círculo
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Dibujar el círculo rojo
        paint.setColor(Color.RED);
        canvas.drawCircle(x, y, radio, paint);

        // Dibujar las coordenadas en negro
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        canvas.drawText("X: " + x, 200, 100, paint);
        canvas.drawText("Y: " + y, 200, 175, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Detectar evento de tocar la pantalla
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = event.getX();
            y = event.getY();
            invalidate(); // Volver a dibujar la pantalla
        }
        return true;
    }
}
