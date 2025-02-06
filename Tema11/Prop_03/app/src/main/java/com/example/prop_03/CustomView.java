package com.example.prop_03;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CustomView extends View {
    private Paint paint;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true); // Suaviza los bordes
        paint.setStrokeWidth(5); // Grosor del contorno
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Configurar colores
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE); // Solo borde

        // Dibujar rectángulo
        float x1 = 100, y1 = 100, x2 = 600, y2 = 400;
        canvas.drawRect(x1, y1, x2, y2, paint);

        // Dibujar óvalo dentro del rectángulo
        paint.setColor(Color.BLUE);
        RectF oval = new RectF(x1, y1, x2, y2);
        canvas.drawOval(oval, paint);

        // Dibujar círculo en el centro del óvalo
        paint.setColor(Color.BLACK);
        float centerX = (x1 + x2) / 2;
        float centerY = (y1 + y2) / 2;
        float radius = (x2 - x1) / 4;
        canvas.drawCircle(centerX, centerY, radius, paint);

        // Dibujar tres cuadrados con distintos estilos
        paint.setColor(Color.RED);
        float squareSize = 100;
        float startX = 100;
        float startY = 500;

        // STROKE (solo contorno)
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(startX, startY, startX + squareSize, startY + squareSize, paint);

        // FILL (relleno)
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(startX + 150, startY, startX + 150 + squareSize, startY + squareSize, paint);

        // FILL_AND_STROKE (relleno y borde)
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawRect(startX + 300, startY, startX + 300 + squareSize, startY + squareSize, paint);
    }
}